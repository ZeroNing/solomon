package com.steven.solomon.param;

import com.steven.solomon.code.TenancyErrorCode;
import com.steven.solomon.enums.HouseConfigTypeEnum;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class HouseConfigSaveParam implements Serializable {

  @NotBlank(message = TenancyErrorCode.ID_NOT_NULL)
  private String id;

  @NotNull(message = TenancyErrorCode.ROOM_TYPE_CONFIG_IS_NOT_NULL)
  private List<HouseConfigParam> params;

  public class HouseConfigParam implements Serializable {
    /**
     * 房屋配置类型
     */
    @NotNull(message = TenancyErrorCode.ROOM_TYPE_IS_NOT_NULL)
    private HouseConfigTypeEnum type;

    @NotNull(message = TenancyErrorCode.ROOM_TYPE_CONFIG_IS_NOT_NULL)
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
