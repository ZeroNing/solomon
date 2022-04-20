package com.steven.solomon.graphics2D.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Receipt extends BaseReceipt {

  /**
   * 地址
   */
  private String     address = "";
  /**
   * 上月电费读数
   */
  private Long       lastMonthDegree  = 0L;
  /**
   * 上月水费读数
   */
  private Long       lastMonthTonnage = 0L;
  /**
   * 本月电费读数
   */
  private Long       monthDegree      = 0L;
  /**
   * 本月水费读数
   */
  private Long       monthTonnage     = 0L;
  /**
   * 水费单价
   */
  private BigDecimal waterRatePrice   = BigDecimal.ZERO;
  /**
   * 电费单价
   */
  private BigDecimal powerRatePrice   = BigDecimal.ZERO;
  /**
   * 房租
   */
  private String     rent = "";
  /**
   * 卫生费
   */
  private BigDecimal sanitationFee    = BigDecimal.ZERO;
  /**
   * 电视费
   */
  private BigDecimal tVFee            = BigDecimal.ZERO;
  /**
   * 网费
   */
  private BigDecimal internetFee      = BigDecimal.ZERO;
  /**
   * 管理费
   */
  private BigDecimal managementFee    = BigDecimal.ZERO;

  /**
   * 收款人
   */
  private String payee = "";

  /**
   * 租赁人名称
   * @return
   */
  private String tenantName = "";

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Long getLastMonthDegree() {
    return lastMonthDegree;
  }

  public void setLastMonthDegree(Long lastMonthDegree) {
    this.lastMonthDegree = lastMonthDegree;
  }

  public Long getLastMonthTonnage() {
    return lastMonthTonnage;
  }

  public void setLastMonthTonnage(Long lastMonthTonnage) {
    this.lastMonthTonnage = lastMonthTonnage;
  }

  public Long getMonthDegree() {
    return monthDegree;
  }

  public void setMonthDegree(Long monthDegree) {
    this.monthDegree = monthDegree;
  }

  public Long getMonthTonnage() {
    return monthTonnage;
  }

  public void setMonthTonnage(Long monthTonnage) {
    this.monthTonnage = monthTonnage;
  }

  public BigDecimal getWaterRatePrice() {
    return waterRatePrice;
  }

  public void setWaterRatePrice(BigDecimal waterRatePrice) {
    this.waterRatePrice = waterRatePrice;
  }

  public BigDecimal getPowerRatePrice() {
    return powerRatePrice;
  }

  public void setPowerRatePrice(BigDecimal powerRatePrice) {
    this.powerRatePrice = powerRatePrice;
  }

  public String getRent() {
    return rent;
  }

  public void setRent(String rent) {
    this.rent = rent;
  }

  public BigDecimal getSanitationFee() {
    return sanitationFee;
  }

  public void setSanitationFee(BigDecimal sanitationFee) {
    this.sanitationFee = sanitationFee;
  }

  public BigDecimal getTVFee() {
    return tVFee;
  }

  public void settVFee(BigDecimal tVFee) {
    this.tVFee = tVFee;
  }

  public BigDecimal getInternetFee() {
    return internetFee;
  }

  public void setInternetFee(BigDecimal internetFee) {
    this.internetFee = internetFee;
  }

  public BigDecimal getManagementFee() {
    return managementFee;
  }

  public void setManagementFee(BigDecimal managementFee) {
    this.managementFee = managementFee;
  }

  public String getPayee() {
    return payee;
  }

  public void setPayee(String payee) {
    this.payee = payee;
  }

  public String getTenantName() {
    return tenantName;
  }

  public void setTenantName(String tenantName) {
    this.tenantName = tenantName;
  }
}
