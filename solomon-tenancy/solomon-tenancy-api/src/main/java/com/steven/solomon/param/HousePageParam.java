package com.steven.solomon.param;

import com.steven.solomon.base.model.BasePageParam;
import com.steven.solomon.code.TenancyErrorCode;
import javax.validation.constraints.NotNull;

public class HousePageParam extends BasePageParam {

  /**
   * 省份id
   */
  private Long provinceId;

  /**
   * 市id
   */
  private Long cityId;

  /**
   * 区id
   */
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
