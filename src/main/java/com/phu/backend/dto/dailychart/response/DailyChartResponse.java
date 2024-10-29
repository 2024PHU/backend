package com.phu.backend.dto.dailychart.response;

import com.phu.backend.domain.dailychart.Branch;
import com.phu.backend.domain.dailychart.ExerciseArea;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class DailyChartResponse {
    private Long id;
    private Branch branch;
    private LocalDate chartDate;
    private Integer weight;
    private String memo;
    private List<ExerciseArea> routines = new ArrayList<>();

    @Builder
    public DailyChartResponse(Long id, Branch branch, LocalDate chartDate, Integer weight, String memo, List<ExerciseArea> routines) {
        this.id = id;
        this.branch = branch;
        this.chartDate = chartDate;
        this.weight = weight;
        this.memo = memo;
        this.routines = routines;
    }
}
