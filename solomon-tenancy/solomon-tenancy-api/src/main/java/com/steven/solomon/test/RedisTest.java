package com.steven.solomon.test;

import com.steven.solomon.config.RedisClientPropertiesService;
import com.steven.solomon.config.RedisTenantsHandler;
import com.steven.solomon.profile.TenantRedisProperties;
import javax.annotation.Resource;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Component;

@Component
public class RedisTest implements RedisClientPropertiesService {

  @Resource
  private RedisProperties redisProperties;

  @Override
  public void setRedisClient() {
    TenantRedisProperties tenantRedisProperties = new TenantRedisProperties();
    tenantRedisProperties.setTenantCode("1");
    tenantRedisProperties.setHost("106.52.186.166");
    tenantRedisProperties.setPort(6379);
    tenantRedisProperties.setPassword("31863199a");
    tenantRedisProperties.setDatabase(1);
    RedisTenantsHandler.setProperties(tenantRedisProperties);
    tenantRedisProperties = new TenantRedisProperties();
    tenantRedisProperties.setTenantCode("2");
    tenantRedisProperties.setHost("106.52.186.166");
    tenantRedisProperties.setPort(6379);
    tenantRedisProperties.setPassword("31863199a");
    tenantRedisProperties.setDatabase(2);
    RedisTenantsHandler.setProperties(tenantRedisProperties);
  }
}
