package com.phu.backend.dto.voicefile.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.phu.backend.domain.voicefile.VoiceFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class VoiceFileListResponse {
    private Long fileId;
    private String uploadFileUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createAt;
    private Long memberId;
    private Boolean isTransformation;

    public VoiceFileListResponse(VoiceFile voiceFile) {
        this.fileId = voiceFile.getId();
        this.uploadFileUrl = voiceFile.getUploadFileUrl();
        this.createAt = voiceFile.getCreatedAt();
        this.memberId = voiceFile.getMemberId();
        this.isTransformation = voiceFile.getIsTransformation();
    }

    @Builder
    public VoiceFileListResponse(Long fileId, String uploadFileUrl, LocalDateTime createAt, Long memberId, Boolean isTransformation) {
        this.fileId = fileId;
        this.uploadFileUrl = uploadFileUrl;
        this.createAt = createAt;
        this.memberId = memberId;
        this.isTransformation = isTransformation;
    }
}
