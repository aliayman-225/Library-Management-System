package com.example.Library_Management_System.apect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.Library_Management_System.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering method: {} in class: {} with arguments: {}",
                joinPoint.getSignature().getName(),
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getArgs());
    }

    @After("execution(* com.example.Library_Management_System.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Exiting method: {} in class: {}",
                joinPoint.getSignature().getName(),
                joinPoint.getTarget().getClass().getSimpleName());
    }

    @AfterThrowing(pointcut = "execution(* com.example.Library_Management_System.service.*.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        logger.error("Exception in method: {} in class: {} with cause: {}",
                joinPoint.getSignature().getName(),
                joinPoint.getTarget().getClass().getSimpleName(),
                ex.getMessage(), ex);
    }

    @Around("execution(* com.example.Library_Management_System.service.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - startTime;
            logger.info("Method: {} in class: {} executed in {} ms with result: {}",
                    joinPoint.getSignature().getName(),
                    joinPoint.getTarget().getClass().getSimpleName(),
                    elapsedTime,
                    result);
            return result;
        } catch (Throwable ex) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            logger.error("Method: {} in class: {} failed after {} ms with cause: {}",
                    joinPoint.getSignature().getName(),
                    joinPoint.getTarget().getClass().getSimpleName(),
                    elapsedTime,
                    ex.getMessage(), ex);
            throw ex;
        }
    }
}
