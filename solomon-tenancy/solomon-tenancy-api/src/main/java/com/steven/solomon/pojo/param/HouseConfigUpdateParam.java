package com.steven.solomon.pojo.param;

import com.steven.solomon.code.TenancyErrorCode;
import com.steven.solomon.pojo.enums.HouseConfigTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("房屋配置更新请求参数")
public class HouseConfigUpdateParam implements Serializable {

  @NotBlank(message = TenancyErrorCode.ID_NOT_NULL)
  @ApiModelProperty(value = "房屋配置id",required = true)
  private String id;

  @NotNull(message = TenancyErrorCode.ROOM_TYPE_IS_NOT_NULL)
  @ApiModelProperty(value = "房屋配置类型",required = true)
  private HouseConfigTypeEnum type;

  @NotNull(message = TenancyErrorCode.ROOM_TYPE_CONFIG_IS_NOT_NULL)
  @ApiModelProperty(value = "房屋配置对象为:Object",required = true)
  private Object date;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public HouseConfigTypeEnum getType() {
    return type;
  }

  public void setType(HouseConfigTypeEnum type) {
    this.type = type;
  }

  public Object getDate() {
    return date;
  }

  public void setDate(Object date) {
    this.date = date;
  }
}
