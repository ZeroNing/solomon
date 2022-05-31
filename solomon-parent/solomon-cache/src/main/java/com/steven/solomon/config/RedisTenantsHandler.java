package com.steven.solomon.config;

import com.steven.solomon.handler.AbstractTenantsHandler;
import com.steven.solomon.profile.TenantRedisProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;

@Service
public class RedisTenantsHandler extends AbstractTenantsHandler<RedisConnectionFactory> {

  private static final ThreadLocal<RedisConnectionFactory> REDIS_FACTORY_THREAD_LOCAL = new ThreadLocal<>();

  private static final Map<String, RedisConnectionFactory> REDIS_FACTORY_MAP = new ConcurrentHashMap<>();

  private static List<TenantRedisProperties> redisPropertiesList = new ArrayList<>();

  public static void setRedisFactoryMap(Map<String, RedisConnectionFactory> redisFactoryMap) {
    RedisTenantsHandler.REDIS_FACTORY_MAP.putAll(redisFactoryMap);
  }

  public static Map<String, RedisConnectionFactory> getRedisFactoryMap() {
    return RedisTenantsHandler.REDIS_FACTORY_MAP;
  }

  public static List<TenantRedisProperties> getRedisClientList(){
    return redisPropertiesList;
  }

  public static void setRedisClient(TenantRedisProperties properties){
    RedisTenantsHandler.redisPropertiesList.add(properties);
  };

  @Override
  public RedisConnectionFactory getFactory() {
    return REDIS_FACTORY_THREAD_LOCAL.get();
  }

  @Override
  public void removeFactory() {
    REDIS_FACTORY_THREAD_LOCAL.remove();
  }

  @Override
  public void setFactory(String name) {
    REDIS_FACTORY_THREAD_LOCAL.set(REDIS_FACTORY_MAP.get(name));
  }
}
