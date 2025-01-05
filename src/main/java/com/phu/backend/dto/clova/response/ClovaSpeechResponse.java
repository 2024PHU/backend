package com.phu.backend.dto.clova.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ClovaSpeechResponse {
    private String speaker;
    private String text;
    @Builder
    public ClovaSpeechResponse(String speaker, String text) {
        this.speaker = speaker;
        this.text = text;
    }
}
