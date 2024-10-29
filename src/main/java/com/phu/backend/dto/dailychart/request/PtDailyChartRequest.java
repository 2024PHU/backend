package com.phu.backend.dto.dailychart.request;

import com.phu.backend.domain.dailychart.Branch;
import com.phu.backend.domain.dailychart.ExerciseArea;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PtDailyChartRequest {
    @NotNull(message = "차트를 추가할 회원의 아이디를 넣어주세요")
    private Long id;
    @NotNull(message = "차트 종류를 선택하세요")
    @Schema(description = "차트 종류", nullable = false, example = "PT")
    private Branch branch;
    @NotNull(message = "운동 기록 날짜를 입력해주세요")
    @Schema(description = "운동 날짜", nullable = false, example = "2024-10-29")
    private LocalDate chartDate;
    private Integer weight;
    private String memo;
    private List<ExerciseArea> routines = new ArrayList<>();
}
