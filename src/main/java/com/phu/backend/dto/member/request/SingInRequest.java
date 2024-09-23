package com.phu.backend.dto.member.request;

import com.phu.backend.domain.member.Gender;
import com.phu.backend.domain.member.Part;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "회원이 회원가입할때 필요한 데이터")
public class SingInRequest {
    @NotBlank(message = "이메일을 입력하세요")
    @Email(message = "이메일 형식이 아닙니다")
    @Schema(description = "사용자 이메일", nullable = false, example = "mr6208@naver.com")
    private String email;
    @NotBlank(message = "비밀번호를 입력하세요")
    @Schema(description = "사용자 비밀번호", nullable = false, example = "asdf1020")
    private String password;
    @NotBlank(message = "이름을 입력하세요")
    @Schema(description = "사용자 이름", nullable = false, example = "정우혁")
    private String name;
    @NotNull(message = "나이를 입력하세요")
    @Schema(description = "사용자 나이", nullable = false, example = "27")
    private Integer age;
    @Schema(description = "사용자 성별", nullable = false, example = "MALE")
    private Gender gender;
    @NotBlank(message = "전화번호를 입력하세요")
    @Schema(description = "사용자 전화번호", nullable = false, example = "01046666208")
    private String tel;
    @Schema(description = "사용자 분류", nullable = false, example = "TRAINER")
    private Part part;
}
