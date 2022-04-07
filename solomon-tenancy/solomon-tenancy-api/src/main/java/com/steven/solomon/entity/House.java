package com.steven.solomon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.steven.solomon.base.model.BaseEntity;

import java.util.UUID;

@TableName("house")
public class House extends BaseEntity<String> {
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

  /**
   * 房间数
   */
  private Integer num;

  /**
   * 初始化状态
   */
  private Boolean initStatus;

  public House(){
    super();
    setId(UUID.randomUUID().toString());
    super.create();
  }

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

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public Boolean getInitStatus() {
    return initStatus;
  }

  public void setInitStatus(Boolean initStatus) {
    this.initStatus = initStatus;
  }
}