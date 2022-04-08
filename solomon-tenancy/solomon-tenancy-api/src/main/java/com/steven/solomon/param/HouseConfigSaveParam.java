package com.steven.solomon.param;

import com.steven.solomon.enums.HouseConfigTypeEnum;
import com.steven.solomon.enums.RoomTypeEnum;
import java.io.Serializable;

public class HouseConfigSaveParam implements Serializable {

  /**
   * 房屋配置类型
   */
  private HouseConfigTypeEnum type;
  /**
   * 房间类型
   */
  private RoomTypeEnum        roomType;

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
