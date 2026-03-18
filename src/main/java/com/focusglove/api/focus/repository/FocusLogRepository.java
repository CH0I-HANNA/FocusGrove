package com.focusglove.api.focus.repository;

import com.focusglove.api.focus.entity.FocusLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FocusLogRepository extends JpaRepository<FocusLog, Long> {
    // 특정 할 일의 기록들만 모아서 보고 싶을 때 사용
    List<FocusLog> findAllByTaskId(Long taskId);
}