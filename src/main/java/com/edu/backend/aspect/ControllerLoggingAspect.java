package com.edu.backend.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class ControllerLoggingAspect {

    @After("execution(* com.edu.backend.controller..*(..))")
    public void logAfter(JoinPoint joinPoint) {
        //todo разобраться что тут делается
        log.debug("Входящий запрос к контроллеру: " + joinPoint.toLongString());
        log.debug("Входящий запрос к контроллеру getArgs: " + Arrays.toString(joinPoint.getArgs()));
    }
}
