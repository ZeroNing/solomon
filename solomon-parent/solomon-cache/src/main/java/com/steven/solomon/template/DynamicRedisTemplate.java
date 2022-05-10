package com.steven.solomon.template;

import com.steven.solomon.context.RedisContext;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

public class DynamicRedisTemplate<K,V> extends RedisTemplate<K,V> {

  public DynamicRedisTemplate(){
    super();
  }

  @Override
  public RedisConnectionFactory getConnectionFactory() {
    return RedisContext.getRedisFactory();
  }
}
