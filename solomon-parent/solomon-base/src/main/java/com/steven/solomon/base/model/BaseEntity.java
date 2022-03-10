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
  private              String        createBy;
  /**
   * 更新人id
   */
  private              String        updateBy;
  /**
   * 创建人名称
   */
  private              String        createName;
  /**
   * 更新人名称
   */
  private              String        updateName;
  /**
   * 主键id
   */
  private              String        id;

  public BaseEntity() {
    super();
  }

  public void create(){
    this.createDate = DateTimeUtils.getLocalDateTime();
    this.updateDate = DateTimeUtils.getLocalDateTime();
    this.delFlag    = DelFlagEnum.NOT_DELETE.Value();
  }

  public void create(String createName){
    this.createName = createName;
    this.create();
  }

  public void update(String updateName) {
    this.updateName = updateName;
    update();
  }

  public void update() {
    this.updateDate = DateTimeUtils.getLocalDateTime();
  }

  public void delete(String deleteName) {
    this.updateName = deleteName;
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

  public String getCreateBy() {
    return createBy;
  }

  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }

  public String getUpdateBy() {
    return updateBy;
  }

  public void setUpdateBy(String updateBy) {
    this.updateBy = updateBy;
  }

  public String getCreateName() {
    return createName;
  }

  public void setCreateName(String createName) {
    this.createName = createName;
  }

  public String getUpdateName() {
    return updateName;
  }

  public void setUpdateName(String updateName) {
    this.updateName = updateName;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
