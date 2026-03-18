package com.focusglove.api.focus.controller;

import com.focusglove.api.focus.dto.request.FocusLogRequest;
import com.focusglove.api.focus.service.FocusLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/focus")
@RequiredArgsConstructor
public class FocusLogController {

    private final FocusLogService focusLogService;

    @PostMapping
    public ResponseEntity<String> record(@RequestBody FocusLogRequest request) {
        focusLogService.recordFocus(request);
        return ResponseEntity.ok("집중 기록이 저장되었습니다. 🍅");
    }

    //사용자가 GET 요청을 보냈을 때 총 시간을 숫자로 내려줍니다.
    @GetMapping("/stats/today/{userId}")
    public ResponseEntity<Integer> getTotalTime(@PathVariable("userId") Long userId) {
        Integer totalTime = focusLogService.getTotalFocusTimeToday(userId);
        return ResponseEntity.ok(totalTime);
    }
}