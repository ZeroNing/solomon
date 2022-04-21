package com.steven.solomon.pojo.param;

import com.steven.solomon.code.TenancyErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("房屋更新请求参数")
public class HouseUpdateParam implements Serializable {

  /**
   * 主键id
   */
  @NotBlank(message = TenancyErrorCode.ID_NOT_NULL)
  @ApiModelProperty(value = "房屋id",required = true)
  private String id;

  /**
   * 省份id
   */
  @NotNull(message = TenancyErrorCode.PROVINCE_ID_NOT_NULL)
  @ApiModelProperty(value = "省份id",required = true)
  private Long provinceId;

  /**
   * 市id
   */
  @NotNull(message = TenancyErrorCode.CITY_ID_NOT_NULL)
  @ApiModelProperty(value = "市级id",required = true)
  private Long cityId;

  /**
   * 区id
   */
  @NotNull(message = TenancyErrorCode.AREA_ID_NOT_NULL)
  @ApiModelProperty(value = "区域id",required = true)
  private Long areaId;

  /**
   * 地址
   */
  @NotBlank(message = TenancyErrorCode.ADDRESS_NOT_NULL)
  @ApiModelProperty(value = "地址",required = true)
  private String address;

  /**
   * 所属权人
   */
  @ApiModelProperty(value = "所属权人")
  private String owner;

  /**
   * 手机号码
   */
  @ApiModelProperty(value = "手机号码")
  private String phone;

  /**
   * 总层数
   */
  @ApiModelProperty(value = "总层数",required = true)
  @NotNull(message = TenancyErrorCode.TOTAL_FLOORS_NOT_NULL)
  private Integer totalFloors;

  /**
   * 房间数量配置
   */
  @ApiModelProperty(value = "房间数量配置")
  private List<HouseConfigSaveOrUpdateParam> houseConfigSaveOrUpdateParams;

  /**
   * 房间数量
   */
  @ApiModelProperty(value = "房间数量",required = true)
  @NotNull(message = TenancyErrorCode.TOTAL_FLOORS_NOT_NULL)
  private Integer                    num;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public List<HouseConfigSaveOrUpdateParam> getHouseConfigSaveOrUpdateParams() {
    return houseConfigSaveOrUpdateParams;
  }

  public void setHouseConfigSaveOrUpdateParams(List<HouseConfigSaveOrUpdateParam> houseConfigSaveOrUpdateParams) {
    this.houseConfigSaveOrUpdateParams = houseConfigSaveOrUpdateParams;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }
}
