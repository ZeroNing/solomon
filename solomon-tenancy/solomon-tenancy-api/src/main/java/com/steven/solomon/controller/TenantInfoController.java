package com.steven.solomon.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.steven.solomon.base.model.BaseResponseVO;
import com.steven.solomon.exception.BaseException;
import com.steven.solomon.pojo.param.TenantInfoGetParam;
import com.steven.solomon.pojo.param.TenantInfoPageParam;
import com.steven.solomon.pojo.param.TenantInfoSaveParam;
import com.steven.solomon.pojo.param.TenantInfoUpdateParam;
import com.steven.solomon.pojo.vo.TenantInfoVO;
import com.steven.solomon.service.TenantInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tenant-info")
@Api(tags  = "租户信息接口")
public class TenantInfoController {

    @Resource
    private TenantInfoService tenantInfoService;

    @PostMapping("/save")
    @ApiOperation(value="租户信息保存接口")
    public BaseResponseVO<String> save(@Valid @RequestBody TenantInfoSaveParam param) throws BaseException {
        return new BaseResponseVO(tenantInfoService.save(param));
    }

    @PutMapping("/update")
    @ApiOperation(value="租户信息更新接口")
    public BaseResponseVO update(@Valid @RequestBody TenantInfoUpdateParam param) throws BaseException {
        tenantInfoService.update(param);
        return new BaseResponseVO();
    }

    @PostMapping("/page")
    @ApiOperation(value="租户信息分页接口")
    public BaseResponseVO<IPage<TenantInfoVO>> page(@Valid @RequestBody TenantInfoPageParam params) {
        return new BaseResponseVO(tenantInfoService.page(params));
    }

    @PostMapping("/get")
    @ApiOperation(value="租户信息根据id获取接口")
    public BaseResponseVO<TenantInfoVO> get(@Valid @RequestBody TenantInfoGetParam params){
        return new BaseResponseVO(tenantInfoService.get(params));
    }
}
