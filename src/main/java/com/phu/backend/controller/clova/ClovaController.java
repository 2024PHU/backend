package com.phu.backend.controller.clova;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.phu.backend.dto.clova.response.ClovaSpeechResponseList;
import com.phu.backend.dto.summarization.response.SummarizationResponse;
import com.phu.backend.service.clova.ClovaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ClovaController {
    private final ClovaService clovaService;

    @GetMapping("/pt/clova/{member-id}/{file-id}")
    @Operation(summary = "음성메모 텍스트 추출", description = "음성메모의 텍스트를 분석해 추출한다.")
    public ResponseEntity<ClovaSpeechResponseList> uploadVoiceFile(@PathVariable(name = "file-id") Long fileId,
                                                                   @PathVariable(name = "member-id") Long memberId) throws JsonProcessingException {
        return ResponseEntity.ok().body(clovaService.clovaSpeech(fileId, memberId));
    }

    @GetMapping("pt/clova/summation/{voice-text-id}")
    @Operation(summary = "장문요약", description = "분석한 텍스트를 요약해준다.")
    public ResponseEntity<SummarizationResponse> summationTextFile(@PathVariable(name = "voice-text-id")UUID textId) throws JsonProcessingException {
        return ResponseEntity.ok(clovaService.summationTextFile(textId));
    }

    @GetMapping("pt/clova/summation/get/{summarization-id}")
    @Operation(summary = "요약된 텍스트 정보 조회", description = "요약된 텍스트 정보를 조회한다.")
    public ResponseEntity<SummarizationResponse> getSummarization(@PathVariable(name = "summarization-id") Long summarizationId) {
        return ResponseEntity.ok(clovaService.getSummarization(summarizationId));
    }
}
