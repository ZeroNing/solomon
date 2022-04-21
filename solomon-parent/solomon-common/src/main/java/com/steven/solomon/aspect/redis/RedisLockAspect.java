package com.steven.solomon.aspect.redis;

import com.steven.solomon.annotation.Lock;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.constant.code.BaseICacheCode;
import com.steven.solomon.utils.redis.ICaheService;
import com.steven.solomon.utils.verification.ValidateUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Redis Lock的AOP实现类
 */
@Aspect
@Component
public class RedisLockAspect {

  @Resource(name="redisService")
  private ICaheService iCaheService;

  /**
   * 切点，拦截带有@RedisLock的注解方法
   */
  @Pointcut("@annotation(com.steven.solomon.annotation.Lock)")
  void cutPoint() {}

  @Around("cutPoint()")
  public Object around(ProceedingJoinPoint point) throws Throwable {
    Signature       signature       = point.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    Method          targetMethod    = methodSignature.getMethod();
    //获取方法上的注解
    Lock lock = targetMethod.getAnnotation(Lock.class);

    if (ValidateUtils.isEmpty(lock)) {
      Object result = point.proceed();
      return result;
    }

    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String             url     = request.getRequestURL().toString();
    String             key     = url;
    //通过setnx设置值，如果值不存在，则获得该锁
    boolean flag = iCaheService.lockSet(BaseICacheCode.REDIS_LOCK,key, 0, lock.expire());
    if (flag) {
      try {
        Object result = point.proceed();
        return result;
      } finally {
        iCaheService.deleteLock(BaseICacheCode.REDIS_LOCK,key);
      }
    } else {
      //查找错误处理器
      throw new BaseException(lock.errorCode());
    }

  }
}
