package com.steven.solomon.controller;

import com.steven.solomon.base.controller.BaseController;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.param.TenantInfoGetParam;
import com.steven.solomon.param.TenantInfoPageParam;
import com.steven.solomon.param.TenantInfoSaveParam;
import com.steven.solomon.param.TenantInfoUpdateParam;
import com.steven.solomon.service.TenantInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/tenant-info")
public class TenantInfoController extends BaseController {

    @Resource
    private TenantInfoService tenantInfoService;

    @PostMapping("/save")
    public String save(@Valid @RequestBody TenantInfoSaveParam param) throws IOException, BaseException {
        return super.responseSuccessJson(tenantInfoService.save(param));
    }

    @PutMapping("/update")
    public String update(@Valid @RequestBody TenantInfoUpdateParam param) throws IOException, BaseException {
        tenantInfoService.update(param);
        return super.responseSuccessJson();
    }

    @PostMapping("/page")
    public String page(@Valid @RequestBody TenantInfoPageParam params) throws IOException,BaseException {
        return super.responseSuccessJson(tenantInfoService.page(params));
    }

    @PostMapping("/get")
    public String get(@Valid @RequestBody TenantInfoGetParam params) throws IOException,BaseException {
        return super.responseSuccessJson(tenantInfoService.get(params));
    }
}