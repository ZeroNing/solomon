package com.steven.solomon.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.steven.solomon.base.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;

@TableName("house")
@ApiModel("房屋实体类")
public class House extends BaseEntity<String> {
  /**
   * 省份id
   */
  @ApiModelProperty(value="省份id")
  private Long provinceId;

  /**
   * 市id
   */
  @ApiModelProperty(value="市级id")
  private Long cityId;

  /**
   * 区id
   */
  @ApiModelProperty(value="区id")
  private Long areaId;

  /**
   * 地址
   */
  @ApiModelProperty(value="地址")
  private String address;

  /**
   * 所属权人
   */
  @ApiModelProperty(value="所属权人")
  private String owner;

  /**
   * 手机号码
   */
  @ApiModelProperty(value="手机号码")
  private String phone;

  /**
   * 总层数
   */
  @ApiModelProperty(value="总层数")
  private Integer totalFloors;

  /**
   * 房间数
   */
  @ApiModelProperty(value="房间数")
  private Integer num;

  /**
   * 初始化状态
   */
  @ApiModelProperty(value="初始化状态")
  private Boolean initStatus;

  public House(){
    super();
    setId(UUID.randomUUID().toString());
    super.create();
  }

  public Long getProvinceId() {
    return provinceId;
  }

  public void setProvinceId(Long provinceId) {
    this.provinceId = provinceId;
  }

  public Long getCityId() {
    return cityId;
  }

  public void setCityId(Long cityId) {
    this.cityId = cityId;
  }

  public Long getAreaId() {
    return areaId;
  }

  public void setAreaId(Long areaId) {
    this.areaId = areaId;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Integer getTotalFloors() {
    return totalFloors;
  }

  public void setTotalFloors(Integer totalFloors) {
    this.totalFloors = totalFloors;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public Boolean getInitStatus() {
    return initStatus;
  }

  public void setInitStatus(Boolean initStatus) {
    this.initStatus = initStatus;
  }
}
