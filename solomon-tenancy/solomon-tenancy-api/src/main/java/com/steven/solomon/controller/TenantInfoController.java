package com.steven.solomon.controller;

import com.steven.solomon.base.controller.BaseController;
import com.steven.solomon.exception.BaseException;
import com.steven.solomon.pojo.param.TenantInfoGetParam;
import com.steven.solomon.pojo.param.TenantInfoPageParam;
import com.steven.solomon.pojo.param.TenantInfoSaveParam;
import com.steven.solomon.pojo.param.TenantInfoUpdateParam;
import com.steven.solomon.service.TenantInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/tenant-info")
@Api(tags  = "租户信息接口")
public class TenantInfoController extends BaseController {

    @Resource
    private TenantInfoService tenantInfoService;

    @PostMapping("/save")
    @ApiOperation(value="租户信息保存接口")
    public String save(@Valid @RequestBody TenantInfoSaveParam param) throws IOException, BaseException {
        return super.responseSuccessJson(tenantInfoService.save(param));
    }

    @PutMapping("/update")
    @ApiOperation(value="租户信息更新接口")
    public String update(@Valid @RequestBody TenantInfoUpdateParam param) throws IOException, BaseException {
        tenantInfoService.update(param);
        return super.responseSuccessJson();
    }

    @PostMapping("/page")
    @ApiOperation(value="租户信息分页接口")
    public String page(@Valid @RequestBody TenantInfoPageParam params) throws IOException,BaseException {
        return super.responseSuccessJson(tenantInfoService.page(params));
    }

    @PostMapping("/get")
    @ApiOperation(value="租户信息根据id获取接口")
    public String get(@Valid @RequestBody TenantInfoGetParam params) throws IOException,BaseException {
        return super.responseSuccessJson(tenantInfoService.get(params));
    }
}
