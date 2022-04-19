package com.steven.solomon.aspect.controller;

import com.steven.solomon.utils.logger.LoggerUtils;
import com.steven.solomon.utils.verification.ValidateUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAspect {

  private static final Logger logger = LoggerUtils.logger(ControllerAspect.class);

  @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping) || "
      + "@annotation(org.springframework.web.bind.annotation.GetMapping) || "
      + "@annotation(org.springframework.web.bind.annotation.PutMapping) ||"
      + "@annotation(org.springframework.web.bind.annotation.DeleteMapping) ||"
      + "@annotation(org.springframework.web.bind.annotation.RequestMapping) ||"
      + "@annotation(org.springframework.web.bind.annotation.PatchMapping)")
  private void pointCutMethodService() {

  }

  @Around("pointCutMethodService()")
  public Object doAroundService(ProceedingJoinPoint pjp) throws Throwable {
    long   begin = System.nanoTime();
    Object obj   = null;
    try {
      obj = pjp.proceed();
      saveLog(pjp, begin, null);
    } catch (Throwable e) {
      throw e;
    }
    return obj;
  }

  private void saveLog(ProceedingJoinPoint pjp, long begin, Throwable e) {
    long   end                 = System.nanoTime();
    String proceedingJoinPoint = pjp.getSignature().toString();
    long   nanosecond          = end - begin;
    long   millisecond         = (end - begin) / 1000000;
    logger.info("调用controller方法:{},执行耗时:{}纳秒,耗时:{}毫秒", proceedingJoinPoint, nanosecond,
        millisecond);
  }
}
