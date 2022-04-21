package com.steven.solomon.pojo.param;

import com.steven.solomon.code.TenancyErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;

@ApiModel("房屋初始化请求参数")
public class HouseInitParam implements Serializable {

  @NotBlank(message = TenancyErrorCode.ID_NOT_NULL)
  @ApiModelProperty(value = "房屋id",required = true)
  String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
