package com.steven.solomon.param;

import com.steven.solomon.enums.RoomConfigTypeEnum;
import java.io.Serializable;

public class RoomConfigSaveParam implements Serializable {

  /**
   * 房屋配置类型
   */
  private RoomConfigTypeEnum type;
  /**
   * 楼层
   */
  private String             floor;
  /**
   * 房间数
   */
  private Integer            num;

  public RoomConfigTypeEnum getType() {
    return type;
  }

  public void setType(RoomConfigTypeEnum type) {
    this.type = type;
  }

  public String getFloor() {
    return floor;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }
}
