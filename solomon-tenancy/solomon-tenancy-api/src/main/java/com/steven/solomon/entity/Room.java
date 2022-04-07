package com.steven.solomon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.steven.solomon.base.model.BaseEntity;
import java.util.UUID;

@TableName("room")
public class Room extends BaseEntity<String> {

  /**
   * 房间类型 例如:一房一厅、单间、商铺等等····
   */
  private String type;

  /**
   * 房号
   */
  private String roomNum;

  /**
   * 房屋id
   */
  private String houseId;

  public Room() {
    super();
    setId(UUID.randomUUID().toString());
    super.create();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getRoomNum() {
    return roomNum;
  }

  public void setRoomNum(String roomNum) {
    this.roomNum = roomNum;
  }

  public String getHouseId() {
    return houseId;
  }

  public void setHouseId(String houseId) {
    this.houseId = houseId;
  }
}
