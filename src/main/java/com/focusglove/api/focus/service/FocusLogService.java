package com.focusglove.api.focus.service;

import com.focusglove.api.focus.dto.request.FocusLogRequest;
import com.focusglove.api.focus.entity.FocusLog;
import com.focusglove.api.focus.repository.FocusLogRepository;
import com.focusglove.api.task.entity.Task;
import com.focusglove.api.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FocusLogService {

    private final FocusLogRepository focusLogRepository;
    private final TaskRepository taskRepository; // Task 존재 여부 확인용

    @Transactional
    public void recordFocus(FocusLogRequest request) {
        // 1. 해당 Task가 존재하는지 확인
        Task task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException("해당 할 일을 찾을 수 없습니다. ID: " + request.getTaskId()));

        // 2. FocusLog 엔티티 생성 및 저장
        FocusLog focusLog = FocusLog.builder()
                .task(task)
                .focusTime(request.getFocusTime())
                .startTime(LocalDateTime.now().minusMinutes(request.getFocusTime())) // 현재 시간에서 집중 시간을 뺀 시점
                .endTime(LocalDateTime.now()) // 지금 끝났다고 가정
                .build();

        focusLogRepository.save(focusLog);
    }
}