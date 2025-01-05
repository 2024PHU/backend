package com.phu.backend.dto.clova.reqeust;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClovaMessage {
    private String content;
    @Builder
    public ClovaMessage(String content, String role) {
        this.content = content;
    }
}
