package com.focusglove.api.focus.repository;

import com.focusglove.api.focus.entity.FocusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FocusLogRepository extends JpaRepository<FocusLog, Long> {
    // 특정 할 일의 기록들만 모아서 보고 싶을 때 사용
    List<FocusLog> findAllByTaskId(Long taskId);

    @Query("SELECT SUM(f.focusTime) FROM FocusLog f " +
            "WHERE f.task.user.id = :userId " + // f.task.userId -> f.task.user.id 로 수정
            "AND f.startTime >= :start " +
            "AND f.startTime <= :end")
    Integer sumFocusTimeByUserIdAndDate(
            @Param("userId") Long userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}