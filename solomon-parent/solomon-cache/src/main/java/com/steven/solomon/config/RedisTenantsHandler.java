package com.steven.solomon.config;

import com.steven.solomon.handler.AbstractTenantsHandler;
import com.steven.solomon.profile.TenantRedisProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class RedisTenantsHandler extends AbstractTenantsHandler<RedisConnectionFactory,TenantRedisProperties> {

  private static final ThreadLocal<RedisConnectionFactory> REDIS_FACTORY_THREAD_LOCAL = new ThreadLocal<>();

  private static final Map<String, RedisConnectionFactory> REDIS_FACTORY_MAP = new ConcurrentHashMap<>();

  private static List<TenantRedisProperties> redisPropertiesList = new ArrayList<>();

  @Override
  public RedisConnectionFactory getFactory() {
    return RedisTenantsHandler.REDIS_FACTORY_THREAD_LOCAL.get();
  }

  @Override
  public void removeFactory() {
    RedisTenantsHandler.REDIS_FACTORY_THREAD_LOCAL.remove();
  }

  @Override
  public void setFactory(String name) {
    RedisTenantsHandler.REDIS_FACTORY_THREAD_LOCAL.set(REDIS_FACTORY_MAP.get(name));
  }

  @Override
  public void setProperties(TenantRedisProperties properties) {
    RedisTenantsHandler.redisPropertiesList.add(properties);

  }

  @Override
  public List<TenantRedisProperties> getProperties() {
    return RedisTenantsHandler.redisPropertiesList;
  }

  @Override
  public Map<String, RedisConnectionFactory> getFactoryMap() {
    return RedisTenantsHandler.REDIS_FACTORY_MAP;
  }

  @Override
  public void setFactoryMap(Map<String, RedisConnectionFactory> redisFactoryMap) {
    RedisTenantsHandler.REDIS_FACTORY_MAP.putAll(redisFactoryMap);
  }
}
