package com.steven.solomon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

@TableName("area")
public class Area implements Serializable {

  /**
   * id
   */
  private Long id;

  /**
   * 层级
   */
  private int levelCode;

  /**
   * 父级行政代码
   */
  private String parentCode;

  /**
   * 行政代码
   */
  private String areaCode;

  /**
   * 邮政编码
   */
  private String zipCode;

  /**
   * 区号
   */
  private String cityCode;

  /**
   * 名称
   */
  private String name;

  /**
   * 简称
   */
  private String shortName;

  /**
   * 组合名
   */
  private String mergerName;

  /**
   * 拼音
   */
  private String pinyin;

  /**
   * 经度
   */
  private BigDecimal lng;

  /**
   * 纬度
   */
  private BigDecimal lat;

  public Area() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getLevelCode() {
    return levelCode;
  }

  public void setLevelCode(int levelCode) {
    this.levelCode = levelCode;
  }

  public String getParentCode() {
    return parentCode;
  }

  public void setParentCode(String parentCode) {
    this.parentCode = parentCode;
  }

  public String getAreaCode() {
    return areaCode;
  }

  public void setAreaCode(String areaCode) {
    this.areaCode = areaCode;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getCityCode() {
    return cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public String getMergerName() {
    return mergerName;
  }

  public void setMergerName(String mergerName) {
    this.mergerName = mergerName;
  }

  public String getPinyin() {
    return pinyin;
  }

  public void setPinyin(String pinyin) {
    this.pinyin = pinyin;
  }

  public BigDecimal getLng() {
    return lng;
  }

  public void setLng(BigDecimal lng) {
    this.lng = lng;
  }

  public BigDecimal getLat() {
    return lat;
  }

  public void setLat(BigDecimal lat) {
    this.lat = lat;
  }
}
