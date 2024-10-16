package com.phu.backend.dto.member.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberUpdateRequest {
    @NotBlank(message = "이름을 입력하세요")
    @Schema(description = "사용자 이름", nullable = false, example = "변경된이름")
    private String name;
    @NotNull(message = "나이를 입력하세요")
    @Schema(description = "사용자 나이", nullable = false, example = "40")
    private Integer age;
    @NotBlank(message = "전화번호를 입력하세요")
    @Schema(description = "사용자 전화번호", nullable = false, example = "0102838274")
    private String tel;
}
