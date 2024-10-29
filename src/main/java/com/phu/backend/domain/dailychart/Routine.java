package com.phu.backend.domain.dailychart;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_id")
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private ExerciseArea routine;
    @ManyToOne(fetch = FetchType.LAZY)
    private DailyChart dailyChart;
    @Builder
    public Routine(ExerciseArea routine, DailyChart dailyChart) {
        this.routine = routine;
        this.dailyChart = dailyChart;
    }
}
