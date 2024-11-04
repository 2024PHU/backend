package com.phu.backend.repository.dailychart;

import com.phu.backend.domain.dailychart.DailyChart;
import com.phu.backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailyChartRepository extends JpaRepository<DailyChart, Long> {
    List<DailyChart> findAllByTrainerAndMemberEmail(Member trainer, String email);
    List<DailyChart> findAllByMemberEmail(String email);
}
