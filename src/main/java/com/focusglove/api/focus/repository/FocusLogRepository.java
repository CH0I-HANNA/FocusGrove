package com.focusglove.api.focus.repository;

import com.focusglove.api.focus.entity.FocusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FocusLogRepository extends JpaRepository<FocusLog, Long> {

    // 1. 특정 할 일(Task)의 모든 기록 조회
    List<FocusLog> findAllByTaskId(Long taskId);

    // 2. 특정 유저의 특정 기간 내 모든 기록 상세 조회 (최신순 정렬)
    // JPA가 메소드 이름을 분석하여 JOIN 문과 WHERE 절을 자동으로 생성합니다.
    List<FocusLog> findAllByTaskUserIdAndStartTimeBetweenOrderByStartTimeDesc(
            Long userId,
            LocalDateTime start,
            LocalDateTime end
    );

    @Query("SELECT SUM(f.focusTime) FROM FocusLog f " +
            "WHERE f.task.user.id = :userId " + // f.task.userId -> f.task.user.id 로 수정
            "AND f.startTime >= :start " +
            "AND f.startTime <= :end")
    Integer sumFocusTimeByUserIdAndDate(
            @Param("userId") Long userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("SELECT SUM(f.focusTime) FROM FocusLog f WHERE f.task.user.id = :userId")
    Integer sumAllFocusTimeByUserId(@Param("userId") Long userId);

    // 오늘 날짜 이후의 기록 개수 세기
    long countByTaskUserIdAndStartTimeAfter(Long userId, LocalDateTime start);

    boolean existsByTaskUserIdAndStartTimeBetween(Long userId, LocalDateTime start, LocalDateTime end);
}