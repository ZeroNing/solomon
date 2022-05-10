package com.steven.solomon.profile;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

public class TenantRedisProperties extends RedisProperties {

  private String tenantCode;

  public String getTenantCode() {
    return tenantCode;
  }

  public void setTenantCode(String tenantCode) {
    this.tenantCode = tenantCode;
  }
}
