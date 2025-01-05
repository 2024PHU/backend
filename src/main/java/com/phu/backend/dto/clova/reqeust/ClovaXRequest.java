package com.phu.backend.dto.clova.reqeust;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClovaXRequest {
    private String content;
    private String role;
    @Builder
    public ClovaXRequest(String content, String role) {
        this.content = content;
        this.role = role;
    }
}
