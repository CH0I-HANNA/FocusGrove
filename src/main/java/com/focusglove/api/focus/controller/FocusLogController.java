package com.focusglove.api.focus.controller;

import com.focusglove.api.focus.dto.request.FocusLogRequest;
import com.focusglove.api.focus.dto.response.FocusLogResponse;
import com.focusglove.api.focus.service.FocusLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    //집중 기록(focusLog) 기록 삭제를 위한 코드
    //삭제는 보통 성공 시 응답 바디 없이 24 No Content를 보냅니다.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        focusLogService.deleteFocusLog(id);
        return ResponseEntity.noContent().build();
    }

    //특정 날짜의 focusTotalTime 조회를 위한 코드
    @GetMapping("/stats/{userId}")
    public ResponseEntity<Integer> getStats(
            @PathVariable("userId") Long userId,
            @RequestParam("date") @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate date) {

        // 이전에 서비스에 만들어둔 특정 날짜 조회 로직을 호출합니다.
        Integer totalTime = focusLogService.getTotalFocusTime(userId, date);
        return ResponseEntity.ok(totalTime);
    }

    // 1. 특정 날짜 기록 상세 리스트 조회를 위한 코드
    @GetMapping("/list/{userId}")
    public ResponseEntity<List<FocusLogResponse>> getList(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        return ResponseEntity.ok(focusLogService.getFocusHistory(userId, targetDate));
    }
}