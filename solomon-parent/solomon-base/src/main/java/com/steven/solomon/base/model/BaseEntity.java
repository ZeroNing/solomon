package com.steven.solomon.base.model;


import com.steven.solomon.annotation.JsonEnum;
import com.steven.solomon.base.enums.DelFlagEnum;

import com.steven.solomon.date.DateTimeUtils;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类
 */
public class BaseEntity<I> implements Serializable {

  private static final long          serialVersionUID = 1L;

  /**
   * 主键id
   */
  private              I        id;

  /**
   * 创建人id
   */
  private              I        createId;

  /**
   * 创建时间
   */
  private              LocalDateTime createDate;

  /**
   * 更新人id
   */
  private              I        updateId;

  /**
   * 更新时间
   */
  private              LocalDateTime updateDate;
  /**
   * 删除标记
   */
  @JsonEnum(enumClass = DelFlagEnum.class)
  private              String        delFlag;

  /**
   * 备注
   */
  private              String        remark;

  public BaseEntity() {
    super();
  }

  public void create() {
    this.createDate = DateTimeUtils.getLocalDateTime();
    this.updateDate = DateTimeUtils.getLocalDateTime();
    this.delFlag    = DelFlagEnum.NOT_DELETE.Value();
  }

  public void create(I createId) {
    this.create();
    this.createId = createId;
    this.updateId   = createId;
  }

  public void update(I updateId) {
    this.updateId = updateId;
    update();
  }

  public void update() {
    this.updateDate = DateTimeUtils.getLocalDateTime();
  }

  public void delete(I updateId) {
    this.updateId = updateId;
    delete();
  }

  public void delete() {
    this.updateDate = DateTimeUtils.getLocalDateTime();
    this.delFlag    = DelFlagEnum.DELETE.Value();
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

  public LocalDateTime getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(LocalDateTime updateDate) {
    this.updateDate = updateDate;
  }

  public String getDelFlag() {
    return delFlag;
  }

  public void setDelFlag(String delFlag) {
    this.delFlag = delFlag;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public I getId() {
    return id;
  }

  public void setId(I id) {
    this.id = id;
  }

  public I getCreateId() {
    return createId;
  }

  public void setCreateId(I createId) {
    this.createId = createId;
  }

  public I getUpdateId() {
    return updateId;
  }

  public void setUpdateId(I updateId) {
    this.updateId = updateId;
  }
}
