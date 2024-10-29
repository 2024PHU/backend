package com.phu.backend.controller.dailychart;

import com.phu.backend.dto.dailychart.request.PtDailyChartRequest;
import com.phu.backend.dto.dailychart.response.DailyChartResponse;
import com.phu.backend.service.dailychart.DailyChartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "데일리차트 서비스 API", description = "데일리차트와 관련된 서비스를 제공하는 API")
public class DailyChartController {
    private final DailyChartService dailyChartService;
    @PostMapping("/pt/chart")
    @Operation(summary = "트레이너 -> 회원 데일리 차트 생성", description = "PT후 트레이너가 회원의 데일리 차트를 생성한다.")
    public ResponseEntity<Long> addChartPt(@RequestBody @Valid PtDailyChartRequest request) {
        return ResponseEntity.ok().body(dailyChartService.addChartPt(request));
    }

    @GetMapping("/chart/{chart-id}")
    @Operation(summary = "데일리 차트 조회", description = "사용자가 데일리 차트를 상세조회한다.")
    public ResponseEntity<DailyChartResponse> getDailyChart(@PathVariable(value = "chart-id") Long id) {
        return ResponseEntity.ok().body(dailyChartService.getDailyCart(id));
    }
}
