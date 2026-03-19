package com.focusglove.api.dashboard.service;

import com.focusglove.api.dashboard.dto.response.DashboardSummaryResponse;
import com.focusglove.api.dashboard.dto.response.WeeklyChartResponse;
import com.focusglove.api.focus.repository.FocusLogRepository;
import com.focusglove.api.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FocusLogRepository focusLogRepository;
    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public DashboardSummaryResponse getSummary(Long userId) {
        // 1. 누적 시간 조회 (전체 기간의 focusTime 합계)
        Integer total = focusLogRepository.sumAllFocusTimeByUserId(userId);

        // 2. 오늘 완료한 뽀모도로 개수 (오늘 00:00:00 이후 생성된 기록 수)
        long todayCount = focusLogRepository.countByTaskUserIdAndStartTimeAfter(
                userId, LocalDate.now().atStartOfDay());

        // 3. 스트릭 계산
        int streak = calculateStreak(userId);

        return DashboardSummaryResponse.builder()
                .totalTime(total != null ? total : 0)
                .todayPomos((int) todayCount)
                .streakCount(streak)
                .build();
    }

    // 스트릭 계산 전용 메서드
    private int calculateStreak(Long userId) {
        int streak = 0;
        LocalDate checkDate = LocalDate.now(); // 오늘부터 체크 시작

        while (true) {
            LocalDateTime start = checkDate.atStartOfDay();
            LocalDateTime end = checkDate.atTime(LocalTime.MAX);

            // 해당 날짜에 기록이 있는지 확인
            boolean hasRecord = focusLogRepository.existsByTaskUserIdAndStartTimeBetween(userId, start, end);

            if (hasRecord) {
                streak++;
                checkDate = checkDate.minusDays(1); // 하루 전으로 이동
            } else {
                // 오늘 기록이 없어도 스트릭이 끊긴 건 아닐 수 있음 (어제까지는 했을 수 있으니까)
                // 하지만 어제 기록도 없다면 거기서 스트릭은 종료!
                if (checkDate.equals(LocalDate.now())) {
                    checkDate = checkDate.minusDays(1);
                    continue;
                }
                break;
            }
        }
        return streak;
    }

    @Transactional(readOnly = true)
    public List<WeeklyChartResponse> getWeeklyChart(Long userId) {
        List<WeeklyChartResponse> chartData = new ArrayList<>();
        LocalDate today = LocalDate.now();

        // 최근 7일간 반복 (6일 전부터 오늘까지)
        for (int i = 6; i >= 0; i--) {
            LocalDate targetDate = today.minusDays(i);
            LocalDateTime start = targetDate.atStartOfDay();
            LocalDateTime end = targetDate.atTime(LocalTime.MAX);

            // 1. 해당 날짜의 전체 Task 개수
            long totalTasks = taskRepository.countByUserIdAndCreatedAtBetween(userId, start, end);

            // 2. 해당 날짜에 완료된 Task 개수
            long completedTasks = taskRepository.countByUserIdAndIsCompletedTrueAndCreatedAtBetween(userId, start, end);

            // 3. 완료율 계산 (0으로 나누기 방지)
            double rate = (totalTasks == 0) ? 0 : ((double) completedTasks / totalTasks) * 100;

            // 소수점 첫째 자리까지만 반올림
            rate = Math.round(rate * 10) / 10.0;

            chartData.add(new WeeklyChartResponse(targetDate.toString().substring(5), rate)); // "MM-dd" 형태
        }

        return chartData;
    }

}