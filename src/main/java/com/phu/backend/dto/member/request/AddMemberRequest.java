package com.phu.backend.dto.member.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "트레이너가 회원을 추가할때 필요한 데이터")
public class AddMemberRequest {
    @NotBlank(message = "이메일을 입력하세요")
    @Email(message = "이메일 형식이 아닙니다")
    @Schema(description = "사용자 이메일", nullable = false, example = "mr6208@naver.com")
    private String email;
}
