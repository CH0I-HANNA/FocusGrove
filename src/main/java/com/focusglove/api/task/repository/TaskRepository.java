package com.focusglove.api.task.repository;

import com.focusglove.api.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUserId(Long userId);

    // 특정 기간 동안 생성된 전체 할 일 개수
    long countByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

    // 특정 기간 동안 생성되고 완료된 할 일 개수
    long countByUserIdAndIsCompletedTrueAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);
}