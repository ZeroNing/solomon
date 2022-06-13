package com.steven.solomon.pojo.enums;

import com.steven.solomon.enums.BaseEnum;

public enum HouseConfigTypeEnum implements BaseEnum {
  /**
   * 楼层房间数配置
   */
  FLOOR_ROOM("FLOOR_ROOM"),
  /**
   * 水费
   */
  WATER_RATE("WATER_RATE"),
  /**
   * 电费
   */
  ELECTRICITY_FEES("ELECTRICITY_FEES"),
  /**
   * 网费
   */
  NETWORK_FEE("NETWORK_FEE");

  private final String label;

  HouseConfigTypeEnum(String label) {
    this.label = label;
  }

  @Override
  public String key() {
    return this.label;
  }

  @Override
  public String label() {
    return this.name();
  }
}
