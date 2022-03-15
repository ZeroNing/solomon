package com.steven.solomon.code;

import com.steven.solomon.base.code.error.BaseExceptionCode;

public interface TenancyErrorCode extends BaseExceptionCode {
  /**
   *省份不存在
   */
  String PROVINCE_NON_EXISTENT="province_non_existent";
  /**
   * 市不存在
   */
  String CITY_NON_EXISTENT="city_non_existent";
  /**
   * 区域不存在
   */
  String AREA_NON_EXISTENT="area_non_existent";

  /**
   * 省份id不允许为空
   */
  String PROVINCE_ID_NOT_NULL="province_id_not_null";
  /**
   * 市份id不允许为空
   */
  String CITY_ID_NOT_NULL="city_id_not_null";
  /**
   * 区域id不允许为空
   */
  String AREA_ID_NOT_NULL="area_id_not_null";

  /**
   * 地址不允许为空
   */
  String ADDRESS_NOT_NULL="address_not_null";

  /**
   * 所属权人不允许为空
   */
  String OWNER_NOT_NULL="owner_not_null";
  /**
   * 手机号码不允许为空
   */
  String PHONE_NOT_NULL="phone_not_null";
  /**
   * 总层数不允许为空
   */
  String TOTAL_FLOORS_NOT_NULL="total_floors_not_null";
}
