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
        // 1. 해당 Task 조회
        Task task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException("해당 할 일을 찾을 수 없습니다. ID: " + request.getTaskId()));

        // 2. FocusLog 엔티티 생성 및 저장
        FocusLog focusLog = FocusLog.builder()
                .task(task)
                .focusTime(request.getFocusTime())
                .startTime(LocalDateTime.now().minusMinutes(request.getFocusTime()))
                .endTime(LocalDateTime.now())
                .build();

        focusLogRepository.save(focusLog);

        //기록을 저장할 때 해당 Task를 찾아서 카운트를 올려주는 코드를 한 줄 추가합니다.
        //JPA의 Dirty Checking(변경 감지) 덕분에 별도의 save 호출 없이도 트랜잭션이 끝날 때 DB에 반영됩니다.
        // 3. ★ 핵심: Task의 뽀모도로 카운트 증가
        task.incrementPomodoro();
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