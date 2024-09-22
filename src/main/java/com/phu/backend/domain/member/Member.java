package com.phu.backend.domain.member;

import com.phu.backend.global.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Email
    private String email;
    private String password;
    private String name;
    private Integer age;
    private String tel;
    @Enumerated(value = EnumType.STRING)
    private Part part;

    @Builder
    public Member(String email, String password, String name, Integer age, String tel, Part part) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.tel = tel;
        this.part = part;
    }
}
