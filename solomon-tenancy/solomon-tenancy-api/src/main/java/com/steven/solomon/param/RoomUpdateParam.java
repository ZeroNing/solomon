package com.steven.solomon.param;

import com.steven.solomon.code.TenancyErrorCode;
import com.steven.solomon.enums.RoomTypeEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RoomUpdateParam implements Serializable {

    @NotBlank(message = TenancyErrorCode.ID_NOT_NULL)
    private String id;

    @NotNull(message = TenancyErrorCode.ROOM_TYPE_IS_NOT_NULL)
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
