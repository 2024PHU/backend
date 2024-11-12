package com.phu.backend.dto.voicefile.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoiceFileResponse {
    private Long id;
    private String originalFileName;
    private String uploadFileName;
    private String uploadFileUrl;
    private String message;

    @Builder
    public VoiceFileResponse(Long id, String originalFileName, String uploadFileName, String uploadFileUrl, String message) {
        this.id = id;
        this.originalFileName = originalFileName;
        this.uploadFileName = uploadFileName;
        this.uploadFileUrl = uploadFileUrl;
        this.message = message;
    }
}
