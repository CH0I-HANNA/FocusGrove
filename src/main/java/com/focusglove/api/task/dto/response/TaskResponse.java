package com.focusglove.api.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String title;
    private boolean isCompleted; // 목록 조회할 때 '체크 여부'가 필요하니까 추가!
}