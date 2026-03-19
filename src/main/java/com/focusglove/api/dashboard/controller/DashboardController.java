package com.focusglove.api.dashboard.controller;

import com.focusglove.api.dashboard.dto.response.DashboardSummaryResponse;
import com.focusglove.api.dashboard.dto.response.WeeklyChartResponse;
import com.focusglove.api.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // [추가] 이 클래스가 REST API용 컨트롤러임을 선언
@RequestMapping("/api/dashboard") // [추가] 공통 URL 경로 설정
@RequiredArgsConstructor // [추가] final이 붙은 필드(서비스)를 자동으로 연결
public class DashboardController { // [추가] 클래스 선언부 시작

    private final DashboardService dashboardService; // [추가] 서비스를 사용하기 위한 선언

    //대시보드 요약 정보 조회 API (스트릭, 누적 시간, 오늘 뽀모도로)
    @GetMapping("/summary/{userId}")
    public ResponseEntity<DashboardSummaryResponse> getSummary(@PathVariable("userId") Long userId) {
        // 이제 여기서 dashboardService를 에러 없이 사용할 수 있습니다.
        return ResponseEntity.ok(dashboardService.getSummary(userId));
    }

    //주간 차트 데이터 조회 API (최근 7일간의 날짜별 완료율)
    @GetMapping("/weekly/{userId}")
    public ResponseEntity<List<WeeklyChartResponse>> getWeeklyChart(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(dashboardService.getWeeklyChart(userId));
    }
}