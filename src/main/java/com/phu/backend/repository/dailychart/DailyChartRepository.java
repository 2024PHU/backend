package com.phu.backend.repository.dailychart;

import com.phu.backend.domain.dailychart.DailyChart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyChartRepository extends JpaRepository<DailyChart, Long> {
}
