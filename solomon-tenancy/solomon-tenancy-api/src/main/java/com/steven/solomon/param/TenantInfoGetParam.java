package com.steven.solomon.param;

import com.steven.solomon.code.TenancyErrorCode;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class TenantInfoGetParam implements Serializable {

  @NotBlank(message = TenancyErrorCode.ID_NOT_NULL)
  String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
