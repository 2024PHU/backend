package com.phu.backend.controller.voicefile;

import com.phu.backend.dto.voicefile.response.VoiceFileListResponse;
import com.phu.backend.dto.voicefile.response.VoiceFileResponse;
import com.phu.backend.service.voicefile.VoiceFileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VoiceFileController {
    private final VoiceFileService voiceFileService;
    @PostMapping("/pt/voice-file")
    @Operation(summary = "음성파일 업로드", description = "음성파일을 업로드한다")
    public ResponseEntity<VoiceFileResponse> uploadVoiceFile(@RequestPart(value = "file", required = false) MultipartFile multipartFile) {
        return ResponseEntity.ok().body(voiceFileService.uploadVoiceFile(multipartFile));
    }

    @GetMapping("/pt/voice-file")
    @Operation(summary = "저장된 음성파일 리스트 조회", description = "저장된 음성파일 리스트를 전체조회한다.")
    public ResponseEntity<List<VoiceFileListResponse>> getVoiceFileList() {
        return ResponseEntity.ok().body(voiceFileService.getList());
    }
}
