package com.focusglove.api.log.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class DailyLogResponse {
    private Long id;
    private String content;
    private String emoji;
    private LocalDate logDate;
}