package com.steven.solomon.pojo.entity;

import java.io.Serializable;

public class HouseConfigFloorRoom implements Serializable {

  /**
   * 楼层
   */
  private Integer floor;
  /**
   * 房间数
   */
  private Integer num;

  /**
   * 房间类型
   */
  private String roomType;

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
