package com.ing.stockexchange.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.ing.stockexchange.controller.*Controller.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info(String.format("Entering method: %s", joinPoint.getSignature().getName()));
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                logger.info(String.format("Input: %s",  arg));
            }
        }
    }

    @AfterReturning(pointcut = "execution(* com.ing.stockexchange.controller.*Controller.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info(String.format("Exiting method: %s",joinPoint.getSignature().getName()));
        if (result instanceof ResponseEntity) {
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
            logger.info(String.format("Output: %s",responseEntity.getBody()));
        } else {
            logger.info(String.format("Output: %s",result));
        }
    }
}

