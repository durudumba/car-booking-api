package com.raontec.carbookingapi.utils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LogAspect {
    @Pointcut("within(com.raontec.carbookingapi.api.*)")
    public void controller() {
    }

    @AfterReturning(pointcut = "controller()", returning = "returnValue")
    public void afterController(JoinPoint joinpoint, Object returnValue) {
        log.info("Request Completed - {}", joinpoint.getSignature().toShortString());
    }

    @AfterThrowing(pointcut = "controller()", throwing = "ex")
    public void afterControllerThrowing(JoinPoint joinPoing, Exception ex) {
        log.error("Request Failed - {}", joinPoing.getSignature().toShortString());
    }

}
