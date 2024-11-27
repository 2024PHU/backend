package com.phu.backend.dto.clova;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ClovaSpeechRequest {
    private String dataKey;
    private String language;
    private String completion;
    @Builder
    public ClovaSpeechRequest(String dataKey, String language, String completion) {
        this.dataKey = dataKey;
        this.language = language;
        this.completion = completion;
    }
}
