package com.steven.solomon.param;

import java.io.Serializable;

public class RoomUpdateParam implements Serializable {

  /**
   * 主键id
   */
  private String id;

  /**
   * 省份id
   */
  private String provinceId;

  /**
   * 市id
   */
  private String cityId;

  /**
   * 区id
   */
  private String areaId;

  /**
   * 地址
   */
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
  private Integer totalFloors;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getProvinceId() {
    return provinceId;
  }

  public void setProvinceId(String provinceId) {
    this.provinceId = provinceId;
  }

  public String getCityId() {
    return cityId;
  }

  public void setCityId(String cityId) {
    this.cityId = cityId;
  }

  public String getAreaId() {
    return areaId;
  }

  public void setAreaId(String areaId) {
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
