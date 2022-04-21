package com.steven.solomon.code;


import com.steven.solomon.constant.code.BaseExceptionCode;

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
  String TOTAL_FLOORS_NOT_NULL ="TOTAL_FLOORS_NOT_NULL";

  /**
   * 房间总数不允许为空
   */
  String ROOM_NUM_NOT_NULL ="ROOM_NUM_NOT_NULL";

  /**
   * 房间记录不存在
   */
  String HOUSE_IS_NULL          = "HOUSE_IS_NULL";
  /**
   * 身份证不允许为空
   */
  String IDENTITY_CARD_NOT_NULL = "IDENTITY_CARD_NOT_NULL";

  /**
   * 名称不允许为空
   */
  String NAME_NOT_NULL = "NAME_NOT_NULL";

  /**
   * 租户信息不存在
   */
  String TENANT_IS_NULL = "TENANT_IS_NULL";

  /**
   * 房间配置类型不存在
   */
  String HOUSE_CONFIG_TYPE_IS_NULL="HOUSE_CONFIG_TYPE_IS_NULL";

  /**
   * 房屋记录已经初始化成功,不允许修改
   */
  String HOUSE_IS_INIT_SUCCESS = "HOUSE_IS_INIT_SUCCESS";

  /**
   * 房间配置类型不允许出现重复
   */
  String HOUSE_CONFIG_TYPE_NOT_REPEAT = "HOUSE_CONFIG_TYPE_NOT_REPEAT";

  /**
   * 房间类型不存在
   */
  String ROOM_TYPE_IS_NULL="ROOM_TYPE_IS_NULL";

  /**
   * 房间类型不允许为空
   */
  String ROOM_TYPE_IS_NOT_NULL = "ROOM_TYPE_IS_NOT_NULL";

  /**
   * 房间记录不存在
   */
  String ROOM_IS_NULL="ROOM_IS_NULL";

  /**
   * 房间配置不允许为空
   */
  String ROOM_TYPE_CONFIG_IS_NOT_NULL="ROOM_TYPE_CONFIG_IS_NOT_NULL";

  /**
   * 楼层房间数配置不允许新建或更新
   */
  String FLOOR_ROOM_NOT_SAVE_OR_UPDATE = "FLOOR_ROOM_NOT_SAVE_OR_UPDATE";

  /**
   * 房屋配置信息不存在
   */
  String ROOM_CONFIG_IS_NULL="ROOM_CONFIG_IS_NULL";
}
