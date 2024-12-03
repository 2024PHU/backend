package com.phu.backend.dto.clova;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ClovaSpeechResponseList {
    private UUID voiceListId;
    private List<ClovaSpeechResponse> list;

    @Builder
    public ClovaSpeechResponseList(UUID voiceListId, List<ClovaSpeechResponse> list) {
        this.voiceListId = voiceListId;
        this.list = list;
    }
}
