package com.phu.backend.repository.dailychart;

import com.phu.backend.domain.dailychart.DailyChart;
import com.phu.backend.domain.dailychart.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
    List<Routine> findAllByDailyChart(DailyChart dailyChart);
}
