package com.steven.solomon.properties;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;

public class TenantMongoProperties extends MongoProperties {

  private String tenantId;

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }
}
