package com.focusglove.api.log.repository;

import com.focusglove.api.log.entity.DailyLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyLogRepository extends JpaRepository<DailyLog, Long> {
    // 특정 날짜에 이미 쓴 글이 있는지 확인하기 위해 필요함
    Optional<DailyLog> findByUserIdAndLogDate(Long userId, LocalDate logDate);
}
