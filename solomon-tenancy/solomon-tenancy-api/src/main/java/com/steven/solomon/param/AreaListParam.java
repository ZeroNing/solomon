package com.steven.solomon.param;

import com.steven.solomon.utils.verification.ValidateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel("地图列表请求参数")
public class AreaListParam implements Serializable {

  @ApiModelProperty("行政编码,不传默认查询省级数据")
  private String areaCode;

  public String getAreaCode() {
    return ValidateUtils.getOrDefault(areaCode,"0");
  }

  public void setAreaCode(String areaCode) {
    this.areaCode = areaCode;
  }
}