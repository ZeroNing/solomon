package com.steven.solomon.enums;

public enum OrderByEnum {
  DESC("DESC"), ASC("ASC");
  String value;

  OrderByEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
