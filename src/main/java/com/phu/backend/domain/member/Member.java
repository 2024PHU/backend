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
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private Integer age;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    private String tel;
    @Enumerated(value = EnumType.STRING)
    private Part part;
    private String role;

    @Builder
    public Member(String email, String password, String name, Integer age, Gender gender, String tel, Part part, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.tel = tel;
        this.part = part;
        this.role = role;
    }
}
