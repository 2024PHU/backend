package com.phu.backend.dto.member.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateMemberMemoRequest {
    @NotBlank(message = "목표를 설정해주세요")
    @Schema(description = "회원 목표", nullable = false, example = "바디푸로필 찍기")
    private String memberTarget;
    @NotBlank(message = "회원의 특이사항을 작성하세요")
    @Schema(description = "회원 특이사항", nullable = false, example = "비만")
    private String significant;
    @NotNull(message = "PT시작 날짜를 입력해주세요")
    @Schema(description = "PT 시작 날짜", nullable = false, example = "2024-10-22")
    private LocalDate ptStartDate;
    @NotNull(message = "PT종료 날짜를 입력해주세요")
    @Schema(description = "PT 종료 날짜", nullable = false, example = "2024-12-24")
    private LocalDate ptEndDate;
}
