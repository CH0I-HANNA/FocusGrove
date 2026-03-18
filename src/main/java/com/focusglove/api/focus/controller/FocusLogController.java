package com.focusglove.api.focus.controller;

import com.focusglove.api.focus.dto.request.FocusLogRequest;
import com.focusglove.api.focus.service.FocusLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}