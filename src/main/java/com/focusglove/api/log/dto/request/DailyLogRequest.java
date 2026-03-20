package com.focusglove.api.log.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DailyLogRequest {
    private String content;
    private String emoji;
}