package com.steven.solomon.template;

import com.steven.solomon.config.RedisConfig;
import com.steven.solomon.config.RedisTenantsHandler;
import com.steven.solomon.verification.ValidateUtils;
import javax.annotation.Resource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

public class DynamicRedisTemplate<K,V> extends RedisTemplate<K,V> {

  public DynamicRedisTemplate(){
    super();
  }

  private RedisTenantsHandler redisTenantsHandler = new RedisTenantsHandler();

  @Override
  public RedisConnectionFactory getConnectionFactory() {
    RedisConnectionFactory factory = redisTenantsHandler.getFactory();
    return ValidateUtils.isEmpty(factory) ? super.getConnectionFactory() : factory;
  }
}
