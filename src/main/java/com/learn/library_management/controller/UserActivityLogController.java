package com.learn.library_management.controller;

import com.learn.library_management.config.Messages;
import com.learn.library_management.dto.BasicResponse;
import com.learn.library_management.dto.UserActivityLogViewDTO;
import com.learn.library_management.service.UserActivityLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Activity Log Controller", description = "API for viewing user activity logs")
@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class UserActivityLogController {

    private final UserActivityLogService logService;

    @Operation(summary = "Get all user activity logs", description = "Retrieve all logs of user activities")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BasicResponse> getAllLogs() {
        List<UserActivityLogViewDTO> response = logService.getAllLogs();
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_ALL_LOGS, response));
    }

    @Operation(summary = "Get logs by user ID", description = "Retrieve activity logs of a specific user by ID")
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BasicResponse> getLogsByUser(@PathVariable Long userId) {
        List<UserActivityLogViewDTO> response = logService.getLogsByUser(userId);
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_LOGS, response));
    }
}
