package com.phu.backend.dto.clova.reqeust;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ClovaXRequestList {
    private List<ClovaXRequest> messages;
    @Builder
    public ClovaXRequestList(List<ClovaXRequest> messages) {
        this.messages = messages;
    }
}
