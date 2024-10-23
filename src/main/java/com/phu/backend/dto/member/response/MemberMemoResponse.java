package com.phu.backend.dto.member.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberMemoResponse {
    private String memberName;
    private Integer memberAge;
    private LocalDate ptStartDate;
    private LocalDate ptEndDate;
    private String memberTarget;
    private String significant;

    @Builder
    public MemberMemoResponse(String memberName, Integer memberAge, LocalDate ptStartDate, LocalDate ptEndDate, String memberTarget, String significant) {
        this.memberName = memberName;
        this.memberAge = memberAge;
        this.ptStartDate = ptStartDate;
        this.ptEndDate = ptEndDate;
        this.memberTarget = memberTarget;
        this.significant = significant;
    }
}
