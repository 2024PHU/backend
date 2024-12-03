package com.phu.backend.dto.member.response;


import com.phu.backend.domain.member.MemberList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberInfoResponse {
    private Long id;
    private String name;
    private String email;
    private String tel;

    public MemberInfoResponse(MemberList memberList, String tel) {
        this.id = memberList.getId();
        this.email = memberList.getMemberEmail();
        this.name = memberList.getMemberName();
        this.tel = tel;
    }
    @Builder
    public MemberInfoResponse(Long id, String name, String email, String tel) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.tel = tel;
    }
}
