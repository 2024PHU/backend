package com.phu.backend.dto.member.response;


import com.phu.backend.domain.member.MemberList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberInfoResponse {
    private Long id;
    private String name;
    private String email;

    public MemberInfoResponse(MemberList memberList) {
        this.id = memberList.getId();
        this.email = memberList.getMemberEmail();
        this.name = memberList.getMemberName();
    }
    @Builder
    public MemberInfoResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
