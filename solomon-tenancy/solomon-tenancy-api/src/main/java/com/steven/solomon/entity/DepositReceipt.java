package com.steven.solomon.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class DepositReceipt implements Serializable {

  /**
   * 地址
   */
  private String     address;

  /**
   * 租赁者名称
   */
  private String tenantName;

  /**
   * 押金
   */
  private BigDecimal deposit =BigDecimal.ZERO;

  /**
   * 收款人
   */
  private String payee;

  /**
   * 钥匙押金
   * @return
   */
  private BigDecimal keyDeposit = BigDecimal.ZERO;
  /**
   * 初始水表读数
   */
  private Long initialWaterMeterReading = 0L;
  /**
   * 初始电费读数
   */
  private Long initialPowerMeterReading = 0L;

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getTenantName() {
    return tenantName;
  }

  public void setTenantName(String tenantName) {
    this.tenantName = tenantName;
  }

  public BigDecimal getDeposit() {
    return deposit;
  }

  public void setDeposit(BigDecimal deposit) {
    this.deposit = deposit;
  }

  public String getPayee() {
    return payee;
  }

  public void setPayee(String payee) {
    this.payee = payee;
  }

  public BigDecimal getKeyDeposit() {
    return keyDeposit;
  }

  public void setKeyDeposit(BigDecimal keyDeposit) {
    this.keyDeposit = keyDeposit;
  }

  public Long getInitialWaterMeterReading() {
    return initialWaterMeterReading;
  }

  public void setInitialWaterMeterReading(Long initialWaterMeterReading) {
    this.initialWaterMeterReading = initialWaterMeterReading;
  }

  public Long getInitialPowerMeterReading() {
    return initialPowerMeterReading;
  }

  public void setInitialPowerMeterReading(Long initialPowerMeterReading) {
    this.initialPowerMeterReading = initialPowerMeterReading;
  }
}
