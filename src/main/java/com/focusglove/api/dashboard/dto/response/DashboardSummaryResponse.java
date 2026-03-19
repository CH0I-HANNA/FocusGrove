package com.focusglove.api.dashboard.dto.response;

import lombok.Builder;
import lombok.Getter;

// 프론트엔드에서 받기 편한 형태로 데이터를 구성합니다.
@Getter
@Builder
public class DashboardSummaryResponse {
    private int streakCount;    // 연속 집중 일수
    private int totalTime;      // 누적 집중 시간 (분)
    private int todayPomos;     // 오늘 완료한 뽀모도로 개수
}