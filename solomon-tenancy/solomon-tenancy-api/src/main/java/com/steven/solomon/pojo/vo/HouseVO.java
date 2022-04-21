package com.steven.solomon.pojo.vo;

import com.steven.solomon.pojo.entity.House;

public class HouseVO extends House {

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

  public String getProvinceCityAreaAddress(){
    return getProvinceName()+getCityName()+getCityName()+getAddress();
  }
}
