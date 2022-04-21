package com.steven.solomon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.steven.solomon.exception.BaseException;
import com.steven.solomon.pojo.entity.TenantInfo;
import com.steven.solomon.pojo.param.TenantInfoGetParam;
import com.steven.solomon.pojo.param.TenantInfoPageParam;
import com.steven.solomon.pojo.param.TenantInfoSaveParam;
import com.steven.solomon.pojo.param.TenantInfoUpdateParam;
import com.steven.solomon.pojo.vo.TenantInfoVO;

public interface TenantInfoService {

    String save(TenantInfoSaveParam param) throws BaseException;

    void update(TenantInfoUpdateParam param) throws BaseException;

    IPage<TenantInfoVO> page(TenantInfoPageParam params);

    TenantInfo get(TenantInfoGetParam param);
}
