package com.steven.solomon.enums;

import com.steven.solomon.base.enums.BaseEnum;

public enum HouseConfigTypeEnum implements BaseEnum {
  /**
   * 楼层房间数配置
   */
  FLOOR_ROOM("FLOOR_ROOM");

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
