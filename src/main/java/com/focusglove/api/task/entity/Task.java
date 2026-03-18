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
    }}
