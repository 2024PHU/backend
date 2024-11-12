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
    private Long id;
    private String uploadFileUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createAt;

    public VoiceFileListResponse(VoiceFile voiceFile) {
        this.id = voiceFile.getId();
        this.uploadFileUrl = voiceFile.getUploadFileUrl();
        this.createAt = voiceFile.getCreatedAt();
    }
    @Builder
    public VoiceFileListResponse(Long id, String uploadFileUrl, LocalDateTime createAt) {
        this.id = id;
        this.uploadFileUrl = uploadFileUrl;
        this.createAt = createAt;
    }
}
