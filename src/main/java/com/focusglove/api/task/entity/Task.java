package com.focusglove.api.task.entity;

import com.focusglove.api.common.entity.BaseEntity;
import com.focusglove.api.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tasks")
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 유저 한 명이 여러 태스크를 가짐
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    private boolean isCompleted = false;

    private Integer pomodoroCount = 0;

    @Builder
    public Task(User user, String title) {
        this.user = user;
        this.title = title;
    }

    //com.focusglove.api.task.entity.Task 클래스에 상태를 반전시키는 메서드를 추가합니다.
    //(엔티티 스스로 자기 상태를 바꾸게 하는 것이 좋은 설계입니다.)
    public void toggleStatus() {
        this.isCompleted = !this.isCompleted;
    }

    //카운트를 올리는 메서드를 추가
    public void incrementPomodoro() {
        if (this.pomodoroCount == null) {
            this.pomodoroCount = 0;
        }
        this.pomodoroCount++;
    }
}
