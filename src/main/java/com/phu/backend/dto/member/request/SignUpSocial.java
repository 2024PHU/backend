package com.phu.backend.dto.member.request;

import com.phu.backend.domain.member.Gender;
import com.phu.backend.domain.member.Part;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "회원이 소셜로그인 회원가입시 필요한 추가적인 데이터")
public class SignUpSocial {
    @NotBlank(message = "비밀번호를 입력하세요")
    @Schema(description = "사용자 비밀번호", nullable = false, example = "asdf1020")
    private String password;
    @NotNull(message = "나이를 입력하세요")
    @Schema(description = "사용자 나이", nullable = false, example = "27")
    private Integer age;
    @Schema(description = "사용자 성별", nullable = false, example = "MALE")
    @NotNull
    private Gender gender;
    @NotBlank(message = "전화번호를 입력하세요")
    @Schema(description = "사용자 전화번호", nullable = false, example = "01046666208")
    private String tel;
    @Schema(description = "사용자 분류", nullable = false, example = "TRAINER")
    @NotNull
    private Part part;
    @NotBlank(message = "소셜아이디를 입력해주세요")
    @Schema(description = "사용자 소셜아이디", nullable = false, example = "소셜아이디")
    private String socialId;
}
