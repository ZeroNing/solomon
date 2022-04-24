package com.steven.solomon.holder;

import com.steven.solomon.verification.ValidateUtils;
import java.io.Serializable;

public class TenantContextHolder {

  private static final ThreadLocal<TenantHeardHolder> threadLocal = ThreadLocal.withInitial(() -> {
    TenantHeardHolder header = new TenantHeardHolder();
    return header;
  });

  public static String getTenantId(){
    TenantHeardHolder tenantHeardHolder = threadLocal.get();
    return ValidateUtils.getOrDefault(tenantHeardHolder.getTenantId(),"");
  }

  public static class TenantHeardHolder implements Serializable{
    private String tenantId;

    public String getTenantId() {
      return tenantId;
    }

    public void setTenantId(String tenantId) {
      this.tenantId = tenantId;
    }
  }
}


