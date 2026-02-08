package com.learn.library_management.service;

import com.learn.library_management.dto.UserActivityLogViewDTO;
import com.learn.library_management.entities.User;
import com.learn.library_management.entities.UserActivityLog;
import com.learn.library_management.mapper.UserActivityLogMapper;
import com.learn.library_management.repository.UserActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserActivityLogService {

    private final UserActivityLogRepository logRepository;
    private final UserActivityLogMapper userActivityLogMapper;


    public void logActivity(User user, String action, String entityType, Long entityId) {
        UserActivityLog log = UserActivityLog.builder()
                .user(user)
                .action(action)
                .entityType(entityType)
                .entityId(entityId)
                .build();
        logRepository.save(log);
    }

    public List<UserActivityLogViewDTO> getAllLogs() {
        return logRepository.findAll().stream()
                .map(userActivityLogMapper::toDTO)
                .toList();
    }

    public List<UserActivityLogViewDTO> getLogsByUser(Long userId) {
        return logRepository.findByUser_Id(userId).stream() 
                .map(userActivityLogMapper::toDTO)
                .toList();
    }
}