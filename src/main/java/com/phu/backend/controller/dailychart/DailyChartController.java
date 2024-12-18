package com.phu.backend.controller.dailychart;

import com.phu.backend.dto.dailychart.request.DailyChartRequest;
import com.phu.backend.dto.dailychart.request.UpdateDailyChartRequest;
import com.phu.backend.dto.dailychart.response.DailyChartListResponse;
import com.phu.backend.dto.dailychart.response.DailyChartResponse;
import com.phu.backend.service.dailychart.DailyChartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "데일리차트 서비스 API", description = "데일리차트와 관련된 서비스를 제공하는 API")
public class DailyChartController {
    private final DailyChartService dailyChartService;
    @PostMapping("/pt/chart/{member-id}")
    @Operation(summary = "트레이너 -> 회원 데일리 차트 생성", description = "PT후 트레이너가 회원의 데일리 차트를 생성한다.")
    public ResponseEntity<Long> addChartPt(@RequestBody @Valid DailyChartRequest request,
                                           @PathVariable(name = "member-id") Long memberId) {
        return ResponseEntity.ok().body(dailyChartService.addChartPt(request, memberId));
    }

    @PostMapping("/member/chart")
    @Operation(summary = "회원(개인운동) 데일리 차트 생성", description = "회원이 개인운동 후 데일리 차트를 생성한다.")
    public ResponseEntity<Long> addChartMember(@RequestBody @Valid DailyChartRequest request) {
        return ResponseEntity.ok().body(dailyChartService.addChartMember(request));
    }

    @GetMapping("/chart/{chart-id}")
    @Operation(summary = "데일리 차트 조회", description = "사용자가 데일리 차트를 상세조회한다.")
    public ResponseEntity<DailyChartResponse> getDailyChart(@PathVariable(name = "chart-id") Long chartId) {
        return ResponseEntity.ok().body(dailyChartService.getDailyCart(chartId));
    }

    @GetMapping("/chart/all/{member-id}")
    @Operation(summary = "회원별 데일리 차트 전체조회", description = "회원별 데일리 차트를 전체조회한다.")
    public ResponseEntity<List<DailyChartListResponse>> getAllDailyChart(@PathVariable(name = "member-id") Long memberId) {
        return ResponseEntity.ok().body(dailyChartService.getAllDailyCart(memberId));
    }

    @PutMapping("/chart/{chart-id}")
    @Operation(summary = "데일리차트 수정", description = "작성한 데일리 차트를 수정한다.")
    public void updateDailyChart(@RequestBody @Valid UpdateDailyChartRequest request,
                                 @PathVariable(name = "chart-id") Long chartId) {
        dailyChartService.updateDailyChart(request,chartId);
    }

    @DeleteMapping("/chart/{chart-id}")
    @Operation(summary = "데일리차트 삭제", description = "작성한 데일리 차트를 삭제한다.")
    public void deleteDailyChart(@PathVariable(name = "chart-id") Long chartId) {
        dailyChartService.deleteDailyChart(chartId);
    }
}
