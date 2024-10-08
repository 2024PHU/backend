package com.phu.backend.dto.member.response;

import com.phu.backend.domain.member.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponse {
    private String email;
    private String name;
    private Integer age;
    private Gender gender;
    private String tel;

    @Builder
    public MemberResponse(String email, String name, Integer age, Gender gender, String tel) {
        this.email = email;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.tel = tel;
    }
}
