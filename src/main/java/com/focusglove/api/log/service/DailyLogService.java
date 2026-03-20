package com.focusglove.api.log.service;

import com.focusglove.api.log.dto.request.DailyLogRequest;
import com.focusglove.api.log.dto.response.DailyLogResponse;
import com.focusglove.api.log.entity.DailyLog;
import com.focusglove.api.log.repository.DailyLogRepository;
import com.focusglove.api.user.entity.User;
import com.focusglove.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DailyLogService {

    private final DailyLogRepository dailyLogRepository;
    private final UserRepository userRepository;

    @Transactional
    public DailyLogResponse saveOrUpdate(Long userId, DailyLogRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        LocalDate today = LocalDate.now();

        // [핵심] 오늘 날짜의 로그가 이미 있는지 조회
        DailyLog dailyLog = dailyLogRepository.findByUserIdAndLogDate(userId, today)
                .map(existingLog -> {
                    // 있다면 수정(Update)
                    existingLog.updateContent(request.getContent(), request.getEmoji());
                    return existingLog;
                })
                .orElseGet(() -> {
                    // 없다면 생성(Create)
                    return DailyLog.builder()
                            .user(user)
                            .content(request.getContent())
                            .emoji(request.getEmoji())
                            .logDate(today)
                            .build();
                });

        DailyLog saved = dailyLogRepository.save(dailyLog);
        return DailyLogResponse.builder()
                .id(saved.getId())
                .content(saved.getContent())
                .emoji(saved.getEmoji())
                .logDate(saved.getLogDate())
                .build();
    }

    @Transactional(readOnly = true)
    public DailyLogResponse getTodayLog(Long userId) {
        LocalDate today = LocalDate.now();

        // 유저 ID와 오늘 날짜로 로그가 있는지 확인
        return dailyLogRepository.findByUserIdAndLogDate(userId, today)
                .map(this::convertToResponse)
                .orElse(null); // 아직 작성 전이라면 null을 반환 (프론트에서 작성 유도 가능)
    }

    // 중복 코드를 줄이기 위한 변환 메서드 (기존에 있다면 유지)
    private DailyLogResponse convertToResponse(DailyLog log) {
        return DailyLogResponse.builder()
                .id(log.getId())
                .content(log.getContent())
                .emoji(log.getEmoji())
                .logDate(log.getLogDate())
                .build();
    }
}