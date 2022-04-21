package com.steven.solomon.pojo.enums;

import com.steven.solomon.base.enums.BaseEnum;

public enum RoomTypeEnum implements BaseEnum {
  /**
   * 单间
   */
  SINGLE_ROOM("SINGLE_ROOM"),
  /**
   * 一房一厅
   */
  ONE_ROOM_ONE_HALL("ONE_ROOM_ONE_HALL"),
  /**
   * 两房一厅
   */
  TWO_ROOMS_ONE_HALL("TWO_ROOMS_ONE_HALL"),
  /**
   * 两房两厅
   */
  TWO_ROOM_TWO_HALL("TWO_ROOM_TWO_HALL"),
  /**
   * 商铺
   */
  SHOPS("SHOPS");

  private final String label;

  RoomTypeEnum(String label) {
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
