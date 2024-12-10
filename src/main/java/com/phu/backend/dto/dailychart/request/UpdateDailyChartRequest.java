package com.phu.backend.dto.dailychart.request;

import com.phu.backend.domain.dailychart.ExerciseArea;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class UpdateDailyChartRequest {
    @NotNull(message = "운동 기록 날짜를 입력해주세요")
    @Schema(description = "운동 날짜", nullable = false, example = "2024-10-29")
    private LocalDate chartDate;
    private Integer weight;
    private String memo;
    private List<ExerciseArea> routines = new ArrayList<>();
}
