package com.steven.solomon.param;

import com.steven.solomon.code.TenancyErrorCode;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

public class RoomSaveParam implements Serializable {

  /**
   * 省份id
   */
  @NotNull(message = TenancyErrorCode.PROVINCE_ID_NOT_NULL)
  private Long provinceId;

  /**
   * 市id
   */
  @NotNull(message = TenancyErrorCode.CITY_ID_NOT_NULL)
  private Long cityId;

  /**
   * 区id
   */
  @NotNull(message = TenancyErrorCode.AREA_ID_NOT_NULL)
  private Long areaId;

  /**
   * 地址
   */
  @NotEmpty(message = TenancyErrorCode.ADDRESS_NOT_NULL)
  private String address;

  /**
   * 所属权人
   */
  private String owner;

  /**
   * 手机号码
   */
  private String phone;

  /**
   * 总层数
   */
  @NotNull(message = TenancyErrorCode.TOTAL_FLOORS_NOT_NULL)
  private Integer totalFloors;

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

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Integer getTotalFloors() {
    return totalFloors;
  }

  public void setTotalFloors(Integer totalFloors) {
    this.totalFloors = totalFloors;
  }
}
