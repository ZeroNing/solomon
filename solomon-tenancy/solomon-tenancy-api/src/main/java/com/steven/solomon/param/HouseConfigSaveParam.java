package com.steven.solomon.param;

import com.steven.solomon.code.TenancyErrorCode;
import com.steven.solomon.enums.HouseConfigTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@ApiModel("房屋配置保存请求参数")
public class HouseConfigSaveParam implements Serializable {

  @NotBlank(message = TenancyErrorCode.ID_NOT_NULL)
  @ApiModelProperty(value = "房屋id",required = true)
  private String id;

  @NotNull(message = TenancyErrorCode.ROOM_TYPE_CONFIG_IS_NOT_NULL)
  @ApiModelProperty(value = "房屋配置数组对象",required = true)
  private List<HouseConfigParam> params;

  @ApiModel("房屋配置保存请求参数")
  public class HouseConfigParam implements Serializable {
    /**
     * 房屋配置类型
     */
    @NotNull(message = TenancyErrorCode.ROOM_TYPE_IS_NOT_NULL)
    @ApiModelProperty(value = "房屋配置类型",required = true)
    private HouseConfigTypeEnum type;

    @NotNull(message = TenancyErrorCode.ROOM_TYPE_CONFIG_IS_NOT_NULL)
    @ApiModelProperty(value = "房屋配置对象为:object",required = true)
    private Object date;

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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<HouseConfigParam> getParams() {
    return params;
  }

  public void setParams(List<HouseConfigParam> params) {
    this.params = params;
  }
}
