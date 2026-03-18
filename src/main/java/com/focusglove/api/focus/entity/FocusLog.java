package com.focusglove.api.focus.entity;

import com.focusglove.api.common.entity.BaseEntity;
import com.focusglove.api.task.entity.Task;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FocusLog extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer focusTime; // 분 단위 저장

    @Builder
    public FocusLog(Task task, LocalDateTime startTime, LocalDateTime endTime, Integer focusTime) {
        this.task = task;
        this.startTime = startTime;
        this.endTime = endTime;
        this.focusTime = focusTime;
    }
}