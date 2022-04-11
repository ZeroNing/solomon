package com.steven.solomon.param;

import com.steven.solomon.code.TenancyErrorCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
@ApiModel("租户信息获取请求参数")
public class TenantInfoGetParam implements Serializable {

  @NotBlank(message = TenancyErrorCode.ID_NOT_NULL)
  @ApiModelProperty(value = "租户信息id")
  String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
