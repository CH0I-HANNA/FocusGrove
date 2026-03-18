package com.focusglove.api.task.service;

import com.focusglove.api.task.entity.Task;
import com.focusglove.api.task.repository.TaskRepository;
import com.focusglove.api.user.entity.User;
import com.focusglove.api.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public Long createTask(Long userId, String title) {
        // 1. 유저 존재 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 2. Task 객체 생성
        Task task = Task.builder()
                .user(user)
                .title(title)
                .build();

        // 3. DB 저장 및 ID 반환
        return taskRepository.save(task).getId();
    }
}