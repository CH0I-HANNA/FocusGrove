package com.focusglove.api.task.service;

import com.focusglove.api.task.dto.response.TaskResponse;
import com.focusglove.api.task.entity.Task;
import com.focusglove.api.task.repository.TaskRepository;
import com.focusglove.api.user.entity.User;
import com.focusglove.api.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    //유저 ID로 검색해서 나온 엔티티들을 DTO로 변환해서 반환합니다.
    @Transactional(readOnly = true) // 조회 전용 모드 (성능 최적화)
    public List<TaskResponse> getTasksByUserId(Long userId) {
        return taskRepository.findAllByUserId(userId).stream()
                .map(task -> new TaskResponse(task.getId(), task.getTitle(), task.isCompleted()))
                .collect(Collectors.toList());
    }

    //task-update api를 위한 수정 로직
    @Transactional
    public void toggleTaskStatus(Long taskId) {
        // 1. 수정할 Task가 있는지 확인
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("해당 할 일이 존재하지 않습니다. ID: " + taskId));

        // 2. 상태 반전 (Dirty Checking에 의해 자동으로 DB 반영됨)
        task.toggleStatus();
    }
}