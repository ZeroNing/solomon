package com.steven.solomon.pojo.param;

import com.steven.solomon.code.TenancyErrorCode;
import com.steven.solomon.enums.RoomTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("房屋房间更新请求参数")
public class RoomUpdateParam implements Serializable {

    @NotBlank(message = TenancyErrorCode.ID_NOT_NULL)
    @ApiModelProperty(value = "房间id",required = true)
    private String id;

    @NotNull(message = TenancyErrorCode.ROOM_TYPE_IS_NOT_NULL)
    @ApiModelProperty(value = "房屋房间类型",required = true)
    private RoomTypeEnum type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RoomTypeEnum getType() {
        return type;
    }

    public void setType(RoomTypeEnum type) {
        this.type = type;
    }
}
