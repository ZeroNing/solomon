package com.steven.solomon.config;

import com.steven.solomon.profile.TenantRedisProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.data.redis.connection.RedisConnectionFactory;

public class RedisTenantsHandler {

  private static final ThreadLocal<RedisConnectionFactory> REDIS_FACTORY_THREAD_LOCAL = new ThreadLocal<>();

  private static final Map<String, RedisConnectionFactory> REDIS_FACTORY_MAP = new ConcurrentHashMap<>();

  private static List<TenantRedisProperties> redisPropertiesList = new ArrayList<>();

  public static RedisConnectionFactory getFactory() {
    return RedisTenantsHandler.REDIS_FACTORY_THREAD_LOCAL.get();
  }

  public static void removeFactory() {
    RedisTenantsHandler.REDIS_FACTORY_THREAD_LOCAL.remove();
  }

  public static void setFactory(String name) {
    RedisTenantsHandler.REDIS_FACTORY_THREAD_LOCAL.set(REDIS_FACTORY_MAP.get(name));
  }

  public static void setProperties(TenantRedisProperties properties) {
    RedisTenantsHandler.redisPropertiesList.add(properties);

  }

  public static List<TenantRedisProperties> getProperties() {
    return RedisTenantsHandler.redisPropertiesList;
  }

  
  public static Map<String, RedisConnectionFactory> getFactoryMap() {
    return RedisTenantsHandler.REDIS_FACTORY_MAP;
  }

  
  public static void setFactoryMap(Map<String, RedisConnectionFactory> redisFactoryMap) {
    RedisTenantsHandler.REDIS_FACTORY_MAP.putAll(redisFactoryMap);
  }
}
