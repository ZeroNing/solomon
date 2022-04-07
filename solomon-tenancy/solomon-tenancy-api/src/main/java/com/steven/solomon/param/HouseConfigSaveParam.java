package com.steven.solomon.param;

import com.steven.solomon.enums.HouseConfigTypeEnum;
import java.io.Serializable;

public class HouseConfigSaveParam implements Serializable {

  /**
   * 房屋配置类型
   */
  private String type;
  /**
   * 房间类型
   */
  private String roomType;
  /**
   * 楼层
   */
  private Integer              floor;
  /**
   * 房间数
   */
  private Integer            num;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getFloor() {
    return floor;
  }

  public void setFloor(Integer floor) {
    this.floor = floor;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public String getRoomType() {
    return roomType;
  }

  public void setRoomType(String roomType) {
    this.roomType = roomType;
  }
}