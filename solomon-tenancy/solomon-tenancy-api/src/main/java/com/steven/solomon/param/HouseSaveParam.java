package com.steven.solomon.param;

import com.steven.solomon.code.TenancyErrorCode;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class HouseSaveParam implements Serializable {

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
  @NotBlank(message = TenancyErrorCode.ADDRESS_NOT_NULL)
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
  private Integer                    totalFloors;
  /**
   * 房间数量配置
   */
  private List<HouseConfigSaveParam> houseConfigSaveParams;

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

  public List<HouseConfigSaveParam> getHouseConfigSaveParams() {
    return houseConfigSaveParams;
  }

  public void setHouseConfigSaveParams(List<HouseConfigSaveParam> houseConfigSaveParams) {
    this.houseConfigSaveParams = houseConfigSaveParams;
  }
}
