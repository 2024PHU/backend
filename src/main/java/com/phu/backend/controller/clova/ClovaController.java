package com.phu.backend.controller.clova;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.phu.backend.dto.clova.ClovaSpeechResponse;
import com.phu.backend.service.clova.ClovaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClovaController {
    private final ClovaService clovaService;
    @GetMapping("/pt/clova/{member-id}/{file-id}")
    @Operation(summary = "음성메모 텍스트 추출", description = "음성메모의 텍스트를 분석해 추출한다.")
    public ResponseEntity<List<ClovaSpeechResponse>> uploadVoiceFile(@PathVariable(name = "file-id") Long fileId,
                                                                     @PathVariable(name = "member-id") Long memberId) throws JsonProcessingException {
        return ResponseEntity.ok().body(clovaService.clovaSpeech(fileId, memberId));
    }
}
