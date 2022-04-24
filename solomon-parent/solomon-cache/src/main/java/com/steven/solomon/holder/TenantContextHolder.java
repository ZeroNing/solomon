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

  public static String getCode(){
    TenantHeardHolder tenantHeardHolder = threadLocal.get();
    return ValidateUtils.getOrDefault(tenantHeardHolder.getCode(),"");
  }

  public static String getName(){
    TenantHeardHolder tenantHeardHolder = threadLocal.get();
    return ValidateUtils.getOrDefault(tenantHeardHolder.getName(),"");
  }

  public static class TenantHeardHolder implements Serializable{

    /**
     * SAAS租户id
     */
    private String tenantId;
    /**
     * SAAS租户名称
     */
    private String name;
    /**
     * SAAS租户编码
     */
    private String code;

    public String getTenantId() {
      return tenantId;
    }

    public void setTenantId(String tenantId) {
      this.tenantId = tenantId;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }
  }
}


