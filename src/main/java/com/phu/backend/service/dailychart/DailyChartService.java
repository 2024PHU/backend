package com.phu.backend.service.dailychart;

import com.phu.backend.domain.dailychart.Branch;
import com.phu.backend.domain.dailychart.DailyChart;
import com.phu.backend.domain.dailychart.ExerciseArea;
import com.phu.backend.domain.dailychart.Routine;
import com.phu.backend.domain.member.Member;
import com.phu.backend.domain.member.Part;
import com.phu.backend.dto.dailychart.request.PtDailyChartRequest;
import com.phu.backend.dto.dailychart.response.DailyChartResponse;
import com.phu.backend.exception.dailychart.BranchIsNotValidException;
import com.phu.backend.exception.dailychart.NotFoundChartException;
import com.phu.backend.exception.dailychart.NotFoundChartMemberException;
import com.phu.backend.exception.member.MemberDuplicationException;
import com.phu.backend.exception.member.NotFoundMemberException;
import com.phu.backend.exception.member.TrainerRoleException;
import com.phu.backend.repository.dailychart.DailyChartRepository;
import com.phu.backend.repository.dailychart.RoutineRepository;
import com.phu.backend.repository.member.MemberListRepository;
import com.phu.backend.repository.member.MemberRepository;
import com.phu.backend.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DailyChartService {
    private final DailyChartRepository dailyChartRepository;
    private final RoutineRepository routineRepository;
    private final MemberRepository memberRepository;
    private final MemberListRepository memberListRepository;
    private final MemberService memberService;

    @Transactional
    public Long addChartPt(PtDailyChartRequest request) {
        if (request.getBranch().equals(Branch.PERSONAL)) {
            throw new BranchIsNotValidException();
        }
        Member trainer = memberService.getMember();

        if (trainer.getPart().equals(Part.MEMBER)) {
            throw new TrainerRoleException();
        }

        Member member = memberRepository.findById(request.getId())
                .orElseThrow(NotFoundMemberException::new);

        if (member.getId().equals(trainer.getId())) {
            throw new MemberDuplicationException();
        }

        memberListRepository.findByTrainerAndMemberEmail(trainer, member.getEmail())
                .orElseThrow(NotFoundChartMemberException::new);

        DailyChart chart = DailyChart.builder()
                .chartDate(request.getChartDate())
                .trainer(trainer)
                .memberEmail(member.getEmail())
                .branch(request.getBranch())
                .weight(request.getWeight())
                .memo(request.getMemo())
                .build();

        dailyChartRepository.save(chart);

        List<Routine> routines = request.getRoutines().stream()
                .map(exerciseArea -> Routine.builder()
                        .routine(exerciseArea)
                        .dailyChart(chart)
                        .build())
                .collect(Collectors.toList());

        routineRepository.saveAll(routines);

        return chart.getId();
    }

    public DailyChartResponse getDailyCart(Long id) {
        DailyChart dailyChart = dailyChartRepository.findById(id)
                .orElseThrow(NotFoundChartException::new);

        List<Routine> routines = routineRepository.findAllByDailyChart(dailyChart);

        List<ExerciseArea> exerciseAreas = routines.stream()
                .map(Routine::getRoutine).collect(Collectors.toList());

        return DailyChartResponse.builder()
                .id(dailyChart.getId())
                .branch(dailyChart.getBranch())
                .weight(dailyChart.getWeight())
                .chartDate(dailyChart.getChartDate())
                .routines(exerciseAreas)
                .memo(dailyChart.getMemo())
                .build();
    }
}
