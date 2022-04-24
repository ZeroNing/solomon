package com.steven.solomon.service;

import com.steven.solomon.enums.CacheModeEnum;
import com.steven.solomon.holder.TenantContextHolder;
import com.steven.solomon.verification.ValidateUtils;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbsICacheService implements  ICacheService {

  @Value("${cache.mode}")
  private String cacheMode;

  public String assembleKey(String group, String key) {
    StringBuilder sb = new StringBuilder();
    if(CacheModeEnum.TENANT_PREFIX.toString().equals(cacheMode)){
      String tenantId = TenantContextHolder.getTenantId();
      if(ValidateUtils.isNotEmpty(tenantId)){
        sb.append(tenantId).append(":");
      }
    }
    if (ValidateUtils.isNotEmpty(group)) {
      sb.append(group).append(":");
    }
    return sb.append(key).toString();
  }
}
