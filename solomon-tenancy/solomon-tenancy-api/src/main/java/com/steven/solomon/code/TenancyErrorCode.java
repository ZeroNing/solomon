package com.steven.solomon.code;

import com.steven.solomon.base.code.error.BaseExceptionCode;

public interface TenancyErrorCode extends BaseExceptionCode {
  /**
   *省份不存在
   */
  String PROVINCE_NON_EXISTENT="PROVINCE_NON_EXISTENT";
  /**
   * 市不存在
   */
  String CITY_NON_EXISTENT="CITY_NON_EXISTENT";
  /**
   * 区域不存在
   */
  String AREA_NON_EXISTENT="AREA_NON_EXISTENT";

  /**
   * 省份id不允许为空
   */
  String PROVINCE_ID_NOT_NULL="PROVINCE_ID_NOT_NULL";
  /**
   * 市份id不允许为空
   */
  String CITY_ID_NOT_NULL="CITY_ID_NOT_NULL";
  /**
   * 区域id不允许为空
   */
  String AREA_ID_NOT_NULL="AREA_ID_NOT_NULL";

  /**
   * 地址不允许为空
   */
  String ADDRESS_NOT_NULL="ADDRESS_NOT_NULL";

  /**
   * 所属权人不允许为空
   */
  String OWNER_NOT_NULL="OWNER_NOT_NULL";
  /**
   * 手机号码不允许为空
   */
  String PHONE_NOT_NULL="PHONE_NOT_NULL";
  /**
   * 总层数不允许为空
   */
  String TOTAL_FLOORS_NOT_NULL="TOTAL_FLOORS_NOT_NULL";
}
