package com.focusglove.api.focus.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FocusLogRequest {
    private Long taskId;      // 어떤 할 일을 했는지
    private Integer focusTime; // 몇 분 동안 집중했는지 (예: 25)

    //시작 시간과 종료 시간을 직접 받기
/*    private LocalDateTime startTime;
    private LocalDateTime endTime;*/
}
