package com.steven.solomon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.steven.solomon.annotation.JsonEnum;
import com.steven.solomon.base.model.BaseEntity;
import com.steven.solomon.enums.HouseConfigTypeEnum;
import java.util.UUID;

@TableName("house_config")
public class HouseConfig extends BaseEntity<String> {

  /**
   * 房屋id
   */
  private String houseId;

  /**
   * 房屋拓展类型
   */
  @JsonEnum(enumClass = HouseConfigTypeEnum.class)
  private String type;

  /**
   * 拓展json
   */
  private String json;

  public HouseConfig(){
    super();
    setId(UUID.randomUUID().toString());
    super.create();
  }

  public String getHouseId() {
    return houseId;
  }

  public void setHouseId(String houseId) {
    this.houseId = houseId;
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
