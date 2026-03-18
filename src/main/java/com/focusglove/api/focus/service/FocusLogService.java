package com.focusglove.api.focus.service;

import com.focusglove.api.focus.dto.request.FocusLogRequest;
import com.focusglove.api.focus.entity.FocusLog;
import com.focusglove.api.focus.repository.FocusLogRepository;
import com.focusglove.api.task.entity.Task;
import com.focusglove.api.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    //오늘의 시작(00:00:00)과 끝(23:59:59) 시간을 계산해서 Repository에 넘겨줍니다.
    @Transactional(readOnly = true)
    public Integer getTotalFocusTimeToday(Long userId) {
        // 1. 오늘의 시작과 끝 시간 계산
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay(); // 2026-03-19 00:00:00
        LocalDateTime endOfToday = LocalDateTime.now(); // 현재 시간까지

        // 2. DB에서 합계 가져오기 (기록이 없으면 null이 올 수 있으므로 처리 필요)
        Integer totalTime = focusLogRepository.sumFocusTimeByUserIdAndDate(userId, startOfToday, endOfToday);

        return (totalTime != null) ? totalTime : 0;
    }
}