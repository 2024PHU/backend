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
    private Long memberId;

    @Builder
    public VoiceFileResponse(Long id, String originalFileName, String uploadFileName, String uploadFileUrl, String message, Long memberId) {
        this.id = id;
        this.originalFileName = originalFileName;
        this.uploadFileName = uploadFileName;
        this.uploadFileUrl = uploadFileUrl;
        this.message = message;
        this.memberId = memberId;
    }
}
