package com.steven.solomon.pojo.param;

import com.steven.solomon.base.model.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("房屋分页请求参数")
public class HousePageParam extends BasePageParam {

  /**
   * 省份id
   */
  @ApiModelProperty(value = "省份id")
  private Long provinceId;

  /**
   * 市id
   */
  @ApiModelProperty(value = "市级id")
  private Long cityId;

  /**
   * 区id
   */
  @ApiModelProperty(value = "区域id")
  private Long areaId;

  public Long getProvinceId() {
    return provinceId;
  }

  public void setProvinceId(Long provinceId) {
    this.provinceId = provinceId;
  }

  public Long getCityId() {
    return cityId;
  }

  public void setCityId(Long cityId) {
    this.cityId = cityId;
  }

  public Long getAreaId() {
    return areaId;
  }

  public void setAreaId(Long areaId) {
    this.areaId = areaId;
  }
}
