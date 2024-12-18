package com.phu.backend.domain.dailychart;

import com.phu.backend.domain.member.Member;
import com.phu.backend.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DailyChart extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_chart_id")
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private Branch branch;
    private Integer weight;
    private String memo;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member trainer;
    private String memberEmail;
    private LocalDate chartDate;
    @OneToMany(mappedBy = "dailyChart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Routine> routines = new ArrayList<>();

    public void updateChart(LocalDate chartDate, Integer weight, String memo) {
        this.chartDate = chartDate;
        this.weight = weight;
        this.memo = memo;
    }
    @Builder
    public DailyChart(Branch branch, Integer weight, String memo, Member trainer, String memberEmail, LocalDate chartDate) {
        this.branch = branch;
        this.weight = weight;
        this.memo = memo;
        this.trainer = trainer;
        this.memberEmail = memberEmail;
        this.chartDate = chartDate;
    }
}
