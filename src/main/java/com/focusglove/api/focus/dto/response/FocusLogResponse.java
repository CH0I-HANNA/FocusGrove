package com.focusglove.api.focus.dto.response;

import com.focusglove.api.focus.entity.FocusLog;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

//목록 조회 시 데이터를 담아줄 그릇이 필요합니다.
@Getter
@Builder
public class FocusLogResponse {
    private Long id;
    private String taskTitle;
    private Integer focusTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static FocusLogResponse from(FocusLog log) {
        return FocusLogResponse.builder()
                .id(log.getId())
                .taskTitle(log.getTask().getTitle())
                .focusTime(log.getFocusTime())
                .startTime(log.getStartTime())
                .endTime(log.getEndTime())
                .build();
    }
}
