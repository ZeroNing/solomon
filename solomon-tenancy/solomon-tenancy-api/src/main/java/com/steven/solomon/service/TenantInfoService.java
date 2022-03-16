package com.steven.solomon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.entity.TenantInfo;
import com.steven.solomon.param.TenantInfoGetParam;
import com.steven.solomon.param.TenantInfoPageParam;
import com.steven.solomon.param.TenantInfoSaveParam;
import com.steven.solomon.param.TenantInfoUpdateParam;

public interface TenantInfoService {

    String save(TenantInfoSaveParam param) throws BaseException;

    void update(TenantInfoUpdateParam param) throws BaseException;

    IPage<TenantInfo> page(TenantInfoPageParam params);

    TenantInfo get(TenantInfoGetParam param);
}
