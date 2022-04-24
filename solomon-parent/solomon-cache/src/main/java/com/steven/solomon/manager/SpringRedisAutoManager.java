package com.steven.solomon.manager;

import cn.hutool.core.util.StrUtil;
import com.steven.solomon.enums.CacheModeEnum;
import com.steven.solomon.holder.TenantContextHolder;
import com.steven.solomon.verification.ValidateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

public class SpringRedisAutoManager extends RedisCacheManager {

  @Value("${cache.mode}")
  private String cacheMode;

  public SpringRedisAutoManager(RedisCacheWriter cacheWriter,
      RedisCacheConfiguration defaultCacheConfiguration) {
    super(cacheWriter, defaultCacheConfiguration);
  }

  /**
   * 从上下文中获取租户ID，重写@Cacheable value 值
   * @param name
   * @return
   */
  @Override
  public Cache getCache(String name) {
    if(ValidateUtils.isEmpty(name) || CacheModeEnum.NORMAL.toString().equals(cacheMode)){
      return super.getCache(name);
    } else if(CacheModeEnum.TENANT_PREFIX.toString().equals(cacheMode)){
      String tenantId = TenantContextHolder.getTenantId();
      if(ValidateUtils.isEmpty(tenantId)){
        return super.getCache(name);
      }
      return super.getCache(1 + StrUtil.COLON + name);
    } else if(CacheModeEnum.SWITCH_DB.toString().equals(cacheMode)){
      //TODO 还没想好怎么做,按照默认返回
      return super.getCache(name);
    } else {
      return super.getCache(name);
    }
  }
}
