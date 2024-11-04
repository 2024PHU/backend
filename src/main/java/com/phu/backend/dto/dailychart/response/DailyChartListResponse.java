package com.phu.backend.dto.dailychart.response;

import com.phu.backend.domain.dailychart.Branch;
import com.phu.backend.domain.dailychart.DailyChart;
import com.phu.backend.domain.dailychart.ExerciseArea;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class DailyChartListResponse {
    private Long id;
    private Branch branch;
    private LocalDate chartDate;
    private List<ExerciseArea> routines = new ArrayList<>();

    public DailyChartListResponse(DailyChart dailyCharts) {
        this.id = dailyCharts.getId();
        this.branch = dailyCharts.getBranch();
        this.chartDate = dailyCharts.getChartDate();
    }

    public void confirmRoutines(List<ExerciseArea> exerciseAreas) {
        this.routines = exerciseAreas;
    }

    @Builder
    public DailyChartListResponse(Long id, Branch branch, LocalDate chartDate, List<ExerciseArea> routines) {
        this.id = id;
        this.branch = branch;
        this.chartDate = chartDate;
        this.routines = routines;
    }
}
