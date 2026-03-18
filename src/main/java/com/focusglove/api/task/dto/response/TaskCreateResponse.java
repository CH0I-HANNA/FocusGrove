package com.focusglove.api.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskCreateResponse {
    private Long id;
    private String title;
}
