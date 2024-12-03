package com.phu.backend.dto.member.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberMemoResponse {
    private Long memberId;
    private String memberName;
    private Integer memberAge;
    private LocalDate ptStartDate;
    private LocalDate ptEndDate;
    private String memberTarget;
    private String significant;
    private String tel;

    @Builder
    public MemberMemoResponse(Long memberId, String memberName, Integer memberAge, LocalDate ptStartDate, LocalDate ptEndDate, String memberTarget, String significant, String tel) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberAge = memberAge;
        this.ptStartDate = ptStartDate;
        this.ptEndDate = ptEndDate;
        this.memberTarget = memberTarget;
        this.significant = significant;
        this.tel = tel;
    }
}
