package com.focusglove.api.dashboard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

//차트의 막대 하나하나에 들어갈 데이터를 정의합니다.
@Getter
@AllArgsConstructor
public class WeeklyChartResponse {
    private String date;        // "03-20" 형식의 날짜 문자열
    private double completionRate; // 완료율 (0 ~ 100.0)
}