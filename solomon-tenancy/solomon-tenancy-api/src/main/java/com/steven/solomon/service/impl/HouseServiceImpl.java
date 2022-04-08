package com.steven.solomon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.steven.solomon.base.enums.DelFlagEnum;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.code.TenancyErrorCode;
import com.steven.solomon.entity.Area;
import com.steven.solomon.entity.House;
import com.steven.solomon.entity.HouseConfig;
import com.steven.solomon.enums.HouseConfigTypeEnum;
import com.steven.solomon.mapper.HouseMapper;
import com.steven.solomon.param.*;
import com.steven.solomon.service.AreaService;
import com.steven.solomon.service.HouseConfigService;
import com.steven.solomon.service.HouseService;
import com.steven.solomon.service.RoomService;
import com.steven.solomon.utils.lambda.LambdaUtils;
import com.steven.solomon.utils.rsa.RSAUtils;
import com.steven.solomon.utils.verification.ValidateUtils;
import com.steven.solomon.vo.HouseVO;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@DubboService
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {

  @Resource
  private AreaService areaService;

  @Resource
  private HouseConfigService houseConfigService;

  @Resource
  private RoomService roomService;

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public String save(HouseSaveParam param) throws BaseException, IOException {
    House house = new House();
    house.create("1");

    ValidateUtils.isEmpty(areaService.findById(param.getProvinceId()), TenancyErrorCode.PROVINCE_NON_EXISTENT,
        param.getProvinceId().toString());
    ValidateUtils.isEmpty(areaService.findById(param.getCityId()), TenancyErrorCode.CITY_NON_EXISTENT,
        param.getCityId().toString());
    ValidateUtils.isEmpty(areaService.findById(param.getAreaId()), TenancyErrorCode.AREA_NON_EXISTENT,
        param.getAreaId().toString());

    house.setProvinceId(param.getProvinceId());
    house.setCityId(param.getCityId());
    house.setAreaId(param.getAreaId());
    house.setAddress(RSAUtils.encrypt(param.getAddress()));
    house.setPhone(param.getPhone());
    house.setOwner(param.getOwner());
    house.setTotalFloors(param.getTotalFloors());
    house.setNum(param.getNum());
    baseMapper.insert(house);

    houseConfigService.save(param.getHouseConfigSaveParams(), house);

    return house.getId();
  }

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public void update(HouseUpdateParam param) throws BaseException, IOException {
    House house = ValidateUtils.isEmpty(baseMapper.selectById(param.getId()), TenancyErrorCode.HOUSE_IS_NULL);

    if(house.getInitStatus()){
      throw new BaseException(TenancyErrorCode.HOUSE_IS_INIT_SUCCESS);
    }

    house.update("1");
    ValidateUtils.isEmpty(areaService.findById(param.getProvinceId()), TenancyErrorCode.PROVINCE_NON_EXISTENT,
        param.getProvinceId().toString());
    ValidateUtils.isEmpty(areaService.findById(param.getCityId()), TenancyErrorCode.CITY_NON_EXISTENT,
        param.getCityId().toString());
    ValidateUtils.isEmpty(areaService.findById(param.getAreaId()), TenancyErrorCode.AREA_NON_EXISTENT,
        param.getAreaId().toString());

    LambdaUpdateWrapper<House> updateQueryWrapper = new LambdaUpdateWrapper<House>();
    updateQueryWrapper.eq(House::getId, param.getId()).set(House::getProvinceId, param.getProvinceId())
        .set(House::getUpdateDate, house.getUpdateDate()).set(House::getUpdateId, house.getUpdateId())
        .set(House::getCityId, param.getCityId()).set(House::getAreaId, param.getAreaId())
        .set(House::getAddress, RSAUtils.encrypt(param.getAddress())).set(House::getPhone, param.getPhone())
        .set(House::getOwner, param.getOwner()).set(House::getTotalFloors, param.getTotalFloors())
        .set(House :: getNum,param.getNum());

    houseConfigService.save(param.getHouseConfigSaveParams(), house);

    baseMapper.updateById(house);
  }

  @Override
  public IPage<HouseVO> page(HousePageParam param) {
    QueryWrapper<House> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq(false, "a.province_id", param.getProvinceId());
    queryWrapper.eq(false, "a.city_id", param.getCityId());
    queryWrapper.eq(false, "a.area_id", param.getAreaId());
    queryWrapper.eq("a.del_flag", DelFlagEnum.NOT_DELETE.label());
    IPage<HouseVO> page = baseMapper.page(new Page<House>(param.getPageNo(), param.getPageSize()), queryWrapper);
    if(ValidateUtils.isEmpty(page)){
      return page;
    }
    List<HouseVO> records = page.getRecords();
    List<Long>    areaIds = new ArrayList<>();
    areaIds.addAll(LambdaUtils.toList(records,HOUSE -> ValidateUtils.isNotEmpty(HOUSE.getProvinceId()), House::getProvinceId));
    areaIds.addAll(LambdaUtils.toList(records,HOUSE -> ValidateUtils.isNotEmpty(HOUSE.getCityId()), House::getCityId));
    areaIds.addAll(LambdaUtils.toList(records,HOUSE -> ValidateUtils.isNotEmpty(HOUSE.getAreaId()), House::getAreaId));

    Map<Long,Area> areaMap = areaService.findMapByIds(areaIds);

    for (HouseVO HOUSE : records) {
      HOUSE.setAddress(RSAUtils.decrypt(HOUSE.getAddress()));
      Area area = areaMap.get(HOUSE.getProvinceId());
      HOUSE.setProvinceName(ValidateUtils.isNotEmpty(area) ? area.getName() : null);
      area = areaMap.get(HOUSE.getCityId());
      HOUSE.setCityName(ValidateUtils.isNotEmpty(area) ? area.getName() : null);
      area = areaMap.get(HOUSE.getAreaId());
      HOUSE.setAreaName(ValidateUtils.isNotEmpty(area) ? area.getName() : null);
    }
    page.setRecords(records);
    return page;
  }

  @Override
  public House get(HouseGetParam param) {
    House house = baseMapper.selectById(param.getId());
    if (ValidateUtils.isNotEmpty(house)) {
      house.setAddress(RSAUtils.decrypt(house.getAddress()));
    }
    return house;
  }

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public void init(HouseInitParam param) throws BaseException, IOException {
    House house = ValidateUtils.isEmpty(baseMapper.selectById(param.getId()), TenancyErrorCode.HOUSE_IS_NULL);

    if(house.getInitStatus()){
      throw new BaseException(TenancyErrorCode.HOUSE_IS_INIT_SUCCESS);
    }
    house.update("1");
    house.setInitStatus(true);
    HouseConfig houseConfig = houseConfigService.findMapByHouseId(house.getId()).get(HouseConfigTypeEnum.FLOOR_ROOM.toString());

    roomService.save(houseConfig,house);
    this.update(house,null);
  }
}
