package com.steven.solomon.base.model;


import com.steven.solomon.annotation.JsonEnum;
import com.steven.solomon.base.enums.DelFlagEnum;
import com.steven.solomon.utils.date.DateTimeUtils;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类
 */
public class BaseEntity implements Serializable {

  private static final long          serialVersionUID = 1L;
  /**
   * 创建时间
   */
  private              LocalDateTime createDate;
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
   * 创建人id
   */
  private              String        createId;
  /**
   * 更新人id
   */
  private              String        updateId;
  /**
   * 主键id
   */
  private              String        id;
  /**
   * 备注
   */
  private String remark;

  public BaseEntity() {
    super();
  }

  public void create(String createId){
    this.createDate = DateTimeUtils.getLocalDateTime();
    this.updateDate = DateTimeUtils.getLocalDateTime();
    this.delFlag    = DelFlagEnum.NOT_DELETE.Value();
    this.createId = createId;
  }

  public void update(String updateId) {
    this.updateId = updateId;
    update();
  }

  public void update() {
    this.updateDate = DateTimeUtils.getLocalDateTime();
  }

  public void delete(String updateId) {
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCreateId() {
    return createId;
  }

  public void setCreateId(String createId) {
    this.createId = createId;
  }

  public String getUpdateId() {
    return updateId;
  }

  public void setUpdateId(String updateId) {
    this.updateId = updateId;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
