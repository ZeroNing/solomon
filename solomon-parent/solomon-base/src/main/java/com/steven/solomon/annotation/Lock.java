package com.steven.solomon.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Lock {

  /**
   * redis加锁Key
   *
   * @return
   */
  String lockKey() default "";

  /**
   * 过期时间（秒）
   *
   * @return
   */
  int expire() default 30;

  /**
   * 报错异常编码
   *
   * @return
   */
  String errorCode();
}
