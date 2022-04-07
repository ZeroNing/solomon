package com.steven.solomon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.steven.solomon.annotation.JsonEnum;
import com.steven.solomon.base.model.BaseEntity;
import com.steven.solomon.enums.RoomConfigTypeEnum;

@TableName("room_config")
public class RoomConfig extends BaseEntity<String> {

  /**
   * 房屋id
   */
  private String roomId;

  /**
   * 房屋拓展类型
   */
  @JsonEnum(enumClass = RoomConfigTypeEnum.class)
  private String type;

  /**
   * 拓展json
   */
  private String json;

  public String getRoomId() {
    return roomId;
  }

  public void setRoomId(String roomId) {
    this.roomId = roomId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getJson() {
    return json;
  }

  public void setJson(String json) {
    this.json = json;
  }
}
