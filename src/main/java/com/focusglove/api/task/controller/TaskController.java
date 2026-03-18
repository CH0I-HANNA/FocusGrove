package com.focusglove.api.task.controller;

import com.focusglove.api.task.dto.request.TaskCreateRequest;
import com.focusglove.api.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    //DTO 적용 전
    /*
    @PostMapping("/{userId}")
    public ResponseEntity<String> create(@PathVariable Long userId, @RequestBody String title) {
        taskService.createTask(userId, title);
        return ResponseEntity.ok("할 일이 등록되었습니다!");
    }
    */

    //DTO 적용 후
    @PostMapping("/{userId}")
    public ResponseEntity<String> create(
            @PathVariable("userId") Long userId,
            @RequestBody TaskCreateRequest request // DTO로 변경!
    ) {
        taskService.createTask(userId, request.getTitle());
        return ResponseEntity.ok("할 일이 등록되었습니다!");
    }
}