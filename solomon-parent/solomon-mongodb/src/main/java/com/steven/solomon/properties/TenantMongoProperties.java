package com.steven.solomon.properties;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;

public class TenantMongoProperties extends MongoProperties {

  private String tenantCode;

  public String getTenantCode() {
    return tenantCode;
  }

  public void setTenantCode(String tenantCode) {
    this.tenantCode = tenantCode;
  }
}
