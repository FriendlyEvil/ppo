package ru.friendlyevil.profiler.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import ru.friendlyevil.profiler.service.ProfilerService;

@Aspect
public class ProfilerAspect {

    @Autowired
    private ProfilerService profilerService;

    @Around("execution(* ru.friendlyevil.application..*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        Object result = joinPoint.proceed(joinPoint.getArgs());
        long end = System.nanoTime();

        profilerService.registerCall(joinPoint.getSignature(), end - start);

        return result;
    }
}
