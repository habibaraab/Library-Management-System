package com.learn.library_management.aop;

import com.learn.library_management.config.HasId;
import com.learn.library_management.entities.User;
import com.learn.library_management.service.UserActivityLogService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ActivityLoggingAspect {

    private final UserActivityLogService logService;

    @AfterReturning(
            value = "execution(* com.learn.library_management.service.*.create*(..)) || " +
                    "execution(* com.learn.library_management.service.*.update*(..))",
            returning = "result")
    public void logCreateOrUpdate(JoinPoint joinPoint, Object result) {
        log(joinPoint, result, null);
    }

    @AfterReturning("execution(* com.learn.library_management.service.*.delete*(..))")
    public void logDelete(JoinPoint joinPoint) {
        Long entityId = null;

        if (joinPoint.getArgs().length > 0 && joinPoint.getArgs()[0] instanceof Long id) {
            entityId = id;
        }

        log(joinPoint, null, entityId);
    }

    private void log(JoinPoint joinPoint, Object result, Long entityIdFromArgs) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;

        if (auth != null && auth.getPrincipal() instanceof User user) {
            currentUser = user;
        }

        String methodName = joinPoint.getSignature().getName();
        String action = methodName.startsWith("create") ? "CREATE"
                : methodName.startsWith("update") ? "UPDATE"
                : "DELETE";

        String serviceName = joinPoint.getTarget().getClass().getSimpleName();
        String entityType = serviceName.replace("Service", "");

        Long entityId = entityIdFromArgs;
        if (entityId == null && result instanceof HasId hasId) {
            entityId = hasId.getId();
        }

        logService.logActivity(currentUser, action, entityType, entityId);
    }
}
