package com.phu.backend.dto.clova.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ClovaSpeechResponseList {
    private UUID voiceListId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createAt;
    private String memberName;
    private List<ClovaSpeechResponse> list;

    @Builder
    public ClovaSpeechResponseList(UUID voiceListId, LocalDateTime createAt, String memberName, List<ClovaSpeechResponse> list) {
        this.voiceListId = voiceListId;
        this.createAt = createAt;
        this.memberName = memberName;
        this.list = list;
    }
}
