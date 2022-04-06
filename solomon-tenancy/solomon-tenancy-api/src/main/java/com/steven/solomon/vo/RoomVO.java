package com.steven.solomon.vo;

import com.steven.solomon.entity.Room;

public class RoomVO extends Room {

  /**
   * 省份名称
   */
  String provinceName;
  /**
   * 市级名称
   */
  String cityName;
  /**
   * 区级名称
   */
  String areaName;

  public String getProvinceName() {
    return provinceName;
  }

  public void setProvinceName(String provinceName) {
    this.provinceName = provinceName;
  }

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public String getAreaName() {
    return areaName;
  }

  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }
}
