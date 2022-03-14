package com.steven.solomon.base.code;

public interface BaseICacheCode {
    /**
     * rabbitMq队列失败redis分组
     */
    String RABBIT_FAIL_GROUP = "rabbit-fail";
    /**
     * 分布式锁接口redis分组
     */
    String REDIS_LOCK = "redis:lock";
}
