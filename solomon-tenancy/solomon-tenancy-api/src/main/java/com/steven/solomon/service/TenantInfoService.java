package com.steven.solomon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.entity.TenantInfo;
import com.steven.solomon.param.TenantInfoGetParam;
import com.steven.solomon.param.TenantInfoPageParam;
import com.steven.solomon.param.TenantInfoSaveParam;
import com.steven.solomon.param.TenantInfoUpdateParam;
import com.steven.solomon.vo.TenantInfoVO;

public interface TenantInfoService {

    String save(TenantInfoSaveParam param) throws BaseException;

    void update(TenantInfoUpdateParam param) throws BaseException;

    IPage<TenantInfoVO> page(TenantInfoPageParam params);

    TenantInfo get(TenantInfoGetParam param);
}
