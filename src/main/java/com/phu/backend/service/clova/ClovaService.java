package com.phu.backend.service.clova;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phu.backend.domain.clovavoicetext.ClovaVoiceText;
import com.phu.backend.domain.member.Member;
import com.phu.backend.domain.voicefile.VoiceFile;
import com.phu.backend.dto.clova.ClovaSpeechRequest;
import com.phu.backend.dto.clova.ClovaSpeechResponse;
import com.phu.backend.exception.voicefile.NotFoundFileException;
import com.phu.backend.repository.clovavoicetext.ClovaVoiceTextRepository;
import com.phu.backend.repository.voicefile.VoiceFileRepository;
import com.phu.backend.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ClovaService {
    private final VoiceFileRepository voiceFileRepository;
    private final RestTemplate restTemplate;
    private final ClovaVoiceTextRepository clovaVoiceTextRepository;
    private final MemberService memberService;
    @Value("${ncp.clova.invoke-url}")
    private String invokeUrl;
    @Value("${ncp.clova.clova-secret-key}")
    private String clovaSecret;

    public List<ClovaSpeechResponse> clovaSpeech(Long fileId, Long memberId) throws JsonProcessingException {
        Member member = memberService.getMember();
        Long trainerId = member.getId();

        VoiceFile voiceFile = voiceFileRepository.findById(fileId).orElseThrow(NotFoundFileException::new);
        String domainPath = "/";
        String fullPath = voiceFile.getUploadFileUrl();

        String url = invokeUrl + "/recognizer/object-storage";

        String dataKey = extractDataKey(fullPath, domainPath);

        ClovaSpeechRequest request = ClovaSpeechRequest.builder()
                .dataKey(dataKey)
                .language("ko-KR")
                .completion("sync")
                .build();

        // 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-CLOVASPEECH-API-KEY", clovaSecret);

        HttpEntity<ClovaSpeechRequest> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String voiceResponse = response.getBody();
        return parseClovaSpeechResponse(voiceResponse, trainerId, memberId);
    }


    private String extractDataKey(String fullPath, String domainPath) {
        try {
            // URL에서 경로 추출
            URI uri = new URI(fullPath);
            String path = uri.getPath();

            if (path.startsWith(domainPath)) {
                return path.substring(domainPath.length());
            } else {
                throw new IllegalArgumentException("Invalid uploadFileUrl or domainPath");
            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL format", e);
        }
    }
    private List<ClovaSpeechResponse> parseClovaSpeechResponse(String voiceResponse, Long trainerId, Long memberId) throws JsonProcessingException {
        List<ClovaSpeechResponse> responses = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(voiceResponse);
        JsonNode segmentsNode = rootNode.path("segments");

        for (JsonNode segmentNode : segmentsNode) {
            String speaker = segmentNode.path("speaker").path("name").asText();
            String text = segmentNode.path("text").asText();

            ClovaSpeechResponse clovaSpeechResponse = ClovaSpeechResponse.builder()
                    .speaker(speaker)
                    .text(text)
                    .build();

            UUID uuid = UUID.randomUUID();

            ClovaVoiceText clovaVoiceText = ClovaVoiceText.builder()
                    .id(uuid)
                    .trainerId(trainerId)
                    .memberId(memberId)
                    .speaker(speaker)
                    .text(text)
                    .build();
            clovaVoiceTextRepository.save(clovaVoiceText);
            responses.add(clovaSpeechResponse);
        }
        return responses;
    }
}

