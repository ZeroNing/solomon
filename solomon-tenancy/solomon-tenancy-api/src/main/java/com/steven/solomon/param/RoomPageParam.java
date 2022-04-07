package com.steven.solomon.param;

import com.steven.solomon.base.model.BasePageParam;
import com.steven.solomon.code.TenancyErrorCode;

import javax.validation.constraints.NotBlank;

public class RoomPageParam extends BasePageParam {

    @NotBlank(message = TenancyErrorCode.ID_NOT_NULL)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
