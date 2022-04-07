package com.steven.solomon.param;

import com.steven.solomon.enums.HouseConfigTypeEnum;
import java.io.Serializable;

public class HouseConfigSaveParam implements Serializable {

  /**
   * 房屋配置类型
   */
  private HouseConfigTypeEnum type;
  /**
   * 楼层
   */
  private String              floor;
  /**
   * 房间数
   */
  private Integer            num;

  public HouseConfigTypeEnum getType() {
    return type;
  }

  public void setType(HouseConfigTypeEnum type) {
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
