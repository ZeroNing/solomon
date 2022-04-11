package com.steven.solomon.param;

import com.steven.solomon.enums.HouseConfigTypeEnum;
import com.steven.solomon.enums.RoomTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
@ApiModel("房屋配置保存或者更新请求参数")
public class HouseConfigSaveOrUpdateParam implements Serializable {

  /**
   * 房屋配置类型
   */
  @ApiModelProperty(value = "房屋配置类型")
  private HouseConfigTypeEnum type;
  /**
   * 房间类型
   */
  @ApiModelProperty(value = "房间类型")
  private RoomTypeEnum        roomType;

  @ApiModelProperty(value = "房屋配置对象为:object")
  private Object date;

  public HouseConfigTypeEnum getType() {
    return type;
  }

  public void setType(HouseConfigTypeEnum type) {
    this.type = type;
  }

  public RoomTypeEnum getRoomType() {
    return roomType;
  }

  public void setRoomType(RoomTypeEnum roomType) {
    this.roomType = roomType;
  }

  public Object getDate() {
    return date;
  }

  public void setDate(Object date) {
    this.date = date;
  }
}
