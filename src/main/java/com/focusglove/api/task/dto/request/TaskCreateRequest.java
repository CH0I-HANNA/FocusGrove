package com.focusglove.api.task.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TaskCreateRequest {
    private String title;
    // 나중에 여기에 dueDate, priority 등을 추가하기 좋다.
}
