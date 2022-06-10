package com.steven.solomon.base.enums;

import com.steven.solomon.enums.BaseEnum;

/**
 * 删除枚举
 */

public enum DelFlagEnum implements BaseEnum {
  /**
   * 未删除
   */
  NOT_DELETE("0"),
  /**
   * 已删除
   */
  DELETE("1");

  private final String label;

  DelFlagEnum(String label) {
    this.label = label;
  }

  @Override
  public String label() {
    return this.label;
  }

  @Override
  public String key() {
    return this.name();
  }
}
