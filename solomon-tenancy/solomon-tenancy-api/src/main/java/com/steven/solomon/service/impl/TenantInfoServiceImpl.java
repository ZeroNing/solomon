package com.steven.solomon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.code.TenancyErrorCode;
import com.steven.solomon.entity.TenantInfo;
import com.steven.solomon.mapper.TenantInfoMapper;
import com.steven.solomon.param.TenantInfoGetParam;
import com.steven.solomon.param.TenantInfoPageParam;
import com.steven.solomon.param.TenantInfoSaveParam;
import com.steven.solomon.param.TenantInfoUpdateParam;
import com.steven.solomon.service.AreaService;
import com.steven.solomon.service.TenantInfoService;
import com.steven.solomon.utils.rsa.RSAUtils;
import com.steven.solomon.utils.verification.ValidateUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@DubboService
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class TenantInfoServiceImpl extends ServiceImpl<TenantInfoMapper, TenantInfo> implements TenantInfoService {

  @Resource
  private AreaService areaService;

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public String save(TenantInfoSaveParam param) throws BaseException {
    TenantInfo entity = new TenantInfo();
    entity.create("1");

    ValidateUtils.isEmpty(areaService.findById(param.getProvinceId()), TenancyErrorCode.PROVINCE_NON_EXISTENT,
        param.getProvinceId().toString());
    ValidateUtils.isEmpty(areaService.findById(param.getCityId()), TenancyErrorCode.CITY_NON_EXISTENT,
        param.getCityId().toString());
    ValidateUtils.isEmpty(areaService.findById(param.getAreaId()), TenancyErrorCode.AREA_NON_EXISTENT,
        param.getAreaId().toString());

    entity.setProvinceId(param.getProvinceId());
    entity.setCityId(param.getCityId());
    entity.setAreaId(param.getAreaId());
    entity.setAddress(RSAUtils.encrypt(param.getAddress()));
    entity.setPhone(param.getPhone());
    entity.setName(param.getName());
    entity.setIdentityCard(RSAUtils.encrypt(param.getIdentityCard()));
    baseMapper.insert(entity);
    return entity.getId();
  }

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public void update(TenantInfoUpdateParam param) throws BaseException {
    TenantInfo entity = ValidateUtils.isEmpty(baseMapper.selectById(param.getId()), TenancyErrorCode.TENANT_IS_NULL);
    entity.update("1");

    ValidateUtils.isEmpty(areaService.findById(param.getProvinceId()), TenancyErrorCode.PROVINCE_NON_EXISTENT,
        param.getProvinceId().toString());
    ValidateUtils.isEmpty(areaService.findById(param.getCityId()), TenancyErrorCode.CITY_NON_EXISTENT,
        param.getCityId().toString());
    ValidateUtils.isEmpty(areaService.findById(param.getAreaId()), TenancyErrorCode.AREA_NON_EXISTENT,
        param.getAreaId().toString());

    LambdaUpdateWrapper<TenantInfo> updateQueryWrapper = new LambdaUpdateWrapper<TenantInfo>();
    updateQueryWrapper.eq(TenantInfo::getId, param.getId()).set(TenantInfo::getAddress, param.getAddress())
        .set(TenantInfo::getUpdateDate, entity.getUpdateDate()).set(TenantInfo::getUpdateId, entity.getUpdateId())
        .set(TenantInfo::getProvinceId, param.getProvinceId()).set(TenantInfo::getCityId, param.getCityId())
        .set(TenantInfo::getAreaId, param.getAreaId()).set(TenantInfo::getAddress, RSAUtils.encrypt(param.getAddress()))
        .set(TenantInfo::getPhone, param.getPhone()).set(TenantInfo::getName, param.getName())
        .set(TenantInfo::getIdentityCard, RSAUtils.encrypt(param.getIdentityCard()));
    baseMapper.update(null, updateQueryWrapper);
  }

  @Override
  public IPage<TenantInfo> page(TenantInfoPageParam param) {
    LambdaQueryWrapper<TenantInfo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(false, TenantInfo::getProvinceId, param.getProvinceId());
    queryWrapper.eq(false, TenantInfo::getCityId, param.getCityId());

    queryWrapper.eq(false, TenantInfo::getAreaId, param.getAreaId());
    IPage<TenantInfo> page = baseMapper
        .selectPage(new Page<TenantInfo>(param.getPageNo(), param.getPageSize()), queryWrapper);
    for (TenantInfo entity : page.getRecords()) {
      entity.setAddress(RSAUtils.decrypt(entity.getAddress()));
      entity.setIdentityCard(RSAUtils.decrypt(entity.getIdentityCard()));
    }
    return page;
  }

  @Override
  public TenantInfo get(TenantInfoGetParam param) {
    TenantInfo entity = baseMapper.selectById(param.getId());
    if (ValidateUtils.isEmpty(entity)) {
      entity.setAddress(RSAUtils.decrypt(entity.getAddress()));
      entity.setIdentityCard(RSAUtils.decrypt(entity.getIdentityCard()));
    }
    return entity;
  }
}
