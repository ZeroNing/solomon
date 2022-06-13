package com.steven.solomon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.steven.solomon.code.TenancyErrorCode;
import com.steven.solomon.enums.DelFlagEnum;
import com.steven.solomon.exception.BaseException;
import com.steven.solomon.lambda.LambdaUtils;
import com.steven.solomon.mapper.TenantInfoMapper;
import com.steven.solomon.pojo.entity.Area;
import com.steven.solomon.pojo.entity.TenantInfo;
import com.steven.solomon.pojo.param.TenantInfoGetParam;
import com.steven.solomon.pojo.param.TenantInfoPageParam;
import com.steven.solomon.pojo.param.TenantInfoSaveParam;
import com.steven.solomon.pojo.param.TenantInfoUpdateParam;
import com.steven.solomon.pojo.vo.TenantInfoVO;
import com.steven.solomon.rsa.RSAUtils;
import com.steven.solomon.service.AreaService;
import com.steven.solomon.service.TenantInfoService;
import com.steven.solomon.verification.ValidateUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@DubboService
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
  public IPage<TenantInfoVO> page(TenantInfoPageParam param) {
    QueryWrapper<TenantInfo> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq(ValidateUtils.isNotEmpty(param.getProvinceId()), "a.province_id", param.getProvinceId());
    queryWrapper.eq(ValidateUtils.isNotEmpty(param.getCityId()), "a.city_id", param.getCityId());
    queryWrapper.eq(ValidateUtils.isNotEmpty(param.getAreaId()), "a.area_id", param.getAreaId());
    queryWrapper.eq("a.del_flag", DelFlagEnum.NOT_DELETE.label());
    IPage<TenantInfoVO> page    = baseMapper.page(new Page<TenantInfo>(param.getPageNo(), param.getPageSize()), queryWrapper);
    if(ValidateUtils.isEmpty(page)){
      return page;
    }
    List<TenantInfoVO> records = page.getRecords();
    List<Long>   areaIds = new ArrayList<>();
    areaIds.addAll(
        LambdaUtils.toList(records,tenantInfo -> ValidateUtils.isNotEmpty(tenantInfo.getProvinceId()),TenantInfo::getProvinceId));
    areaIds.addAll(LambdaUtils.toList(records,tenantInfo -> ValidateUtils.isNotEmpty(tenantInfo.getCityId()),TenantInfo::getCityId));
    areaIds.addAll(LambdaUtils.toList(records,tenantInfo -> ValidateUtils.isNotEmpty(tenantInfo.getAreaId()),TenantInfo::getAreaId));

    Map<Long,Area> areaMap = areaService.findMapByIds(areaIds);

    for (TenantInfoVO entity : records) {
      entity.setAddress(RSAUtils.decrypt(entity.getAddress()));
      entity.setIdentityCard(RSAUtils.decrypt(entity.getIdentityCard()));

      Area area = areaMap.get(entity.getProvinceId());
      entity.setProvinceName(ValidateUtils.isNotEmpty(area) ? area.getName() : null);
      area = areaMap.get(entity.getCityId());
      entity.setCityName(ValidateUtils.isNotEmpty(area) ? area.getName() : null);
      area = areaMap.get(entity.getAreaId());
      entity.setAreaName(ValidateUtils.isNotEmpty(area) ? area.getName() : null);
    }
    page.setRecords(records);
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
