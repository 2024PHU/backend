package com.phu.backend.controller.clova;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.phu.backend.dto.clova.reqeust.ClovaMessage;
import com.phu.backend.dto.clova.response.ClovaSpeechResponseList;
import com.phu.backend.service.clova.ClovaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("pt/clova/test")
    @Operation(summary = "챗봇 테스팅", description = "테스트")
    public ResponseEntity<?> test(@RequestBody ClovaMessage request) throws JsonProcessingException {
        return ResponseEntity.ok(clovaService.test(request));
    }
}
