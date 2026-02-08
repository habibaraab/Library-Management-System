package com.learn.library_management.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect {

	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	@Pointcut("execution(* com.learn.erp.service..*(..))")
	public void serviceMethods() {
	}

	@Around("serviceMethods()")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();

		try {
			Object result = joinPoint.proceed();
			long end = System.currentTimeMillis();

			logger.info("Method: {} executed in {} ms | Result: {}", joinPoint.getSignature().getName(), (end - start),
					result);
			return result;
		} catch (Exception ex) {
			long end = System.currentTimeMillis();

			logger.error("Exception in method: {} after {} ms | Message: {}", joinPoint.getSignature().getName(),
					(end - start), ex.getMessage());

			throw ex;
		}
	}
}