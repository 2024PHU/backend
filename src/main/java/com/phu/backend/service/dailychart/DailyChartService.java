package com.phu.backend.service.dailychart;

import com.phu.backend.domain.dailychart.Branch;
import com.phu.backend.domain.dailychart.DailyChart;
import com.phu.backend.domain.dailychart.ExerciseArea;
import com.phu.backend.domain.dailychart.Routine;
import com.phu.backend.domain.member.Member;
import com.phu.backend.domain.member.MemberList;
import com.phu.backend.domain.member.Part;
import com.phu.backend.dto.dailychart.request.DailyChartRequest;
import com.phu.backend.dto.dailychart.request.UpdateDailyChartRequest;
import com.phu.backend.dto.dailychart.response.DailyChartListResponse;
import com.phu.backend.dto.dailychart.response.DailyChartResponse;
import com.phu.backend.exception.dailychart.NotConnectedToTrainerException;
import com.phu.backend.exception.dailychart.NotFoundChartException;
import com.phu.backend.exception.dailychart.NotFoundChartMemberException;
import com.phu.backend.exception.member.MemberDuplicationException;
import com.phu.backend.exception.member.MemberRoleException;
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
    public Long addChartPt(DailyChartRequest request, Long memberId) {
        Member trainer = memberService.getMember();

        if (trainer.getPart().equals(Part.MEMBER)) {
            throw new TrainerRoleException();
        }

        Member member = memberRepository.findById(memberId)
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
                .branch(Branch.PT)
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

    @Transactional
    public Long addChartMember(DailyChartRequest request) {
        Member member = memberService.getMember();

        // 피티 검증
        MemberList memberList = memberListRepository.findByMemberEmail(member.getEmail())
                .orElseThrow(NotConnectedToTrainerException::new);

        DailyChart chart = DailyChart.builder()
                .branch(Branch.PERSONAL)
                .chartDate(request.getChartDate())
                .weight(request.getWeight())
                .memberEmail(member.getEmail())
                .trainer(memberList.getTrainer())
                .memo(request.getMemo())
                .build();

        dailyChartRepository.save(chart);

        List<Routine> routines = request.getRoutines().stream().map(exerciseArea ->
                Routine.builder()
                        .routine(exerciseArea)
                        .dailyChart(chart)
                        .build())
                .collect(Collectors.toList());

        routineRepository.saveAll(routines);

        return chart.getId();
    }

    public List<DailyChartListResponse> getAllDailyCart(Long id) {
        Member user = memberService.getMember();

        if (user.getPart().equals(Part.TRAINER)) {
            Member member = memberRepository.findById(id)
                    .orElseThrow(NotFoundMemberException::new);

            List<DailyChart> dailyCharts = dailyChartRepository
                    .findAllByTrainerAndMemberEmail(user, member.getEmail());

            return dailyCharts.stream()
                    .map(dailyChart -> {
                        DailyChartListResponse response = new DailyChartListResponse(dailyChart);

                        List<ExerciseArea> exerciseAreas = routineRepository.findAllByDailyChart(dailyChart)
                                .stream().map(Routine::getRoutine).collect(Collectors.toList());

                        response.confirmRoutines(exerciseAreas);
                        return response;

                    }).collect(Collectors.toList());
        } else {
            List<DailyChart> dailyCharts = dailyChartRepository.findAllByMemberEmail(user.getEmail());

            return dailyCharts.stream()
                    .map(dailyChart -> {
                        DailyChartListResponse response = new DailyChartListResponse(dailyChart);

                        List<ExerciseArea> exerciseAreas = routineRepository.findAllByDailyChart(dailyChart)
                                .stream().map(Routine::getRoutine).collect(Collectors.toList());

                        response.confirmRoutines(exerciseAreas);
                        return response;
                    } ).collect(Collectors.toList());
        }
    }
    @Transactional
    public void updateDailyChart(UpdateDailyChartRequest request, Long chartId) {
        DailyChart dailyChart = dailyChartRepository.findById(chartId)
                .orElseThrow(NotFoundChartException::new);

        Member member = memberService.getMember();
        Part part = member.getPart();

        // 회원의 개인운동 차트를 트레이너가 수정하려는 경우
        if (dailyChart.getBranch().equals(Branch.PERSONAL) && part.equals(Part.TRAINER)) {
            throw new MemberRoleException();
        }
        // 트레이너가 쓴 회원 데일리차트를 회원이 수정하려는 경우
        if (dailyChart.getBranch().equals(Branch.PT) && part.equals(Part.MEMBER)) {
            throw new TrainerRoleException();
        }

        dailyChart.updateChart(request.getChartDate(), request.getWeight(), request.getMemo());

        List<Routine> routines = routineRepository.findAllByDailyChart(dailyChart);
        routineRepository.deleteAll(routines);

        for (ExerciseArea exerciseArea : request.getRoutines()) {
            Routine routine = Routine.builder()
                    .routine(exerciseArea)
                    .dailyChart(dailyChart)
                    .build();
            routineRepository.save(routine);
        }
    }
}
