package com.focusglove.api.log.controller;

import com.focusglove.api.log.dto.request.DailyLogRequest;
import com.focusglove.api.log.dto.response.DailyLogResponse;
import com.focusglove.api.log.service.DailyLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/daily-log")
@RequiredArgsConstructor
public class DailyLogController {

    private final DailyLogService dailyLogService;

    @PostMapping("/{userId}")
    public ResponseEntity<DailyLogResponse> saveLog(
            @PathVariable("userId") Long userId,
            @RequestBody DailyLogRequest request) {
        return ResponseEntity.ok(dailyLogService.saveOrUpdate(userId, request));
    }

    @GetMapping("/today/{userId}")
    public ResponseEntity<DailyLogResponse> getTodayLog(
            @PathVariable("userId") Long userId) { // 에러 방지를 위해 "userId" 명시

        DailyLogResponse response = dailyLogService.getTodayLog(userId);

        // 데이터가 없어도 200 OK와 함께 null(또는 빈 객체)을 보냅니다.
        return ResponseEntity.ok(response);
    }
}