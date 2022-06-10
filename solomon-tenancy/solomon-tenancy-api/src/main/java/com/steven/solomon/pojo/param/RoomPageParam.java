package com.steven.solomon.pojo.param;

import com.steven.solomon.code.TenancyErrorCode;
import com.steven.solomon.param.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;

@ApiModel("房屋房间分页请求参数")
public class RoomPageParam extends BasePageParam {

    @NotBlank(message = TenancyErrorCode.ID_NOT_NULL)
    @ApiModelProperty(value = "房屋id",required = true)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
