package com.phu.backend.dto.voicefile.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoiceFileResponse {
    private Long fileId;
    private String originalFileName;
    private String voiceFileId;
    private String uploadFileName;
    private String uploadFileUrl;
    private String message;
    private Long memberId;
    private Boolean isTransformation;

    @Builder
    public VoiceFileResponse(Long fileId, String originalFileName, String voiceFileId, String uploadFileName, String uploadFileUrl, String message, Long memberId, Boolean isTransformation) {
        this.fileId = fileId;
        this.originalFileName = originalFileName;
        this.voiceFileId = voiceFileId;
        this.uploadFileName = uploadFileName;
        this.uploadFileUrl = uploadFileUrl;
        this.message = message;
        this.memberId = memberId;
        this.isTransformation = isTransformation;
    }
}
