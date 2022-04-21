package com.steven.solomon.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.steven.solomon.annotation.JsonEnum;
import com.steven.solomon.base.model.BaseEntity;
import com.steven.solomon.pojo.enums.RoomTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;

@TableName("room")
@ApiModel("房屋房间实体类")
public class Room extends BaseEntity<String> {

  /**
   * 房间类型 例如:一房一厅、单间、商铺等等····
   */
  @JsonEnum(enumClass = RoomTypeEnum.class)
  @ApiModelProperty(value="房屋类型")
  private String type;

  /**
   * 房号
   */
  @ApiModelProperty(value="房号")
  private String roomNum;

  /**
   * 房屋id
   */
  @ApiModelProperty(value="房屋id")
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
