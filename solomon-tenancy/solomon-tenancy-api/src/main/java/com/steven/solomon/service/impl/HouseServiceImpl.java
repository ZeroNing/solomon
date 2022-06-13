package com.steven.solomon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.steven.solomon.code.TenancyErrorCode;
import com.steven.solomon.enums.DelFlagEnum;
import com.steven.solomon.exception.BaseException;
import com.steven.solomon.lambda.LambdaUtils;
import com.steven.solomon.mapper.HouseMapper;
import com.steven.solomon.pojo.entity.Area;
import com.steven.solomon.pojo.entity.House;
import com.steven.solomon.pojo.entity.HouseConfig;
import com.steven.solomon.pojo.enums.HouseConfigTypeEnum;
import com.steven.solomon.pojo.param.HouseGetParam;
import com.steven.solomon.pojo.param.HouseInitParam;
import com.steven.solomon.pojo.param.HousePageParam;
import com.steven.solomon.pojo.param.HouseSaveParam;
import com.steven.solomon.pojo.param.HouseUpdateParam;
import com.steven.solomon.pojo.vo.HouseVO;
import com.steven.solomon.rsa.RSAUtils;
import com.steven.solomon.service.AreaService;
import com.steven.solomon.service.HouseConfigService;
import com.steven.solomon.service.HouseService;
import com.steven.solomon.service.RoomService;
import com.steven.solomon.verification.ValidateUtils;
import org.apache.commons.lang.StringUtils;
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
    house.setPhone(RSAUtils.encrypt(param.getPhone()));
    house.setOwner(RSAUtils.encrypt(param.getOwner()));
    house.setTotalFloors(param.getTotalFloors());
    house.setNum(param.getNum());
    baseMapper.insert(house);

    houseConfigService.save(param.getHouseConfigSaveOrUpdateParams(), house);

    return house.getId();
  }

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public void update(HouseUpdateParam param) throws BaseException, IOException {
    HouseVO house = ValidateUtils.isEmpty(this.get(param.getId()), TenancyErrorCode.HOUSE_IS_NULL);

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
        .set(House::getAddress, RSAUtils.encrypt(param.getAddress())).set(House::getPhone, RSAUtils.encrypt(param.getPhone()))
        .set(House::getOwner, RSAUtils.encrypt(param.getOwner())).set(House::getTotalFloors, param.getTotalFloors())
        .set(House :: getNum,param.getNum());

    houseConfigService.save(param.getHouseConfigSaveOrUpdateParams(), house);

    baseMapper.update(null,updateQueryWrapper);
  }

  @Override
  public IPage<HouseVO> page(HousePageParam param) {
    QueryWrapper<House> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq(ValidateUtils.isNotEmpty(param.getProvinceId()), "a.province_id", param.getProvinceId());
    queryWrapper.eq(ValidateUtils.isNotEmpty(param.getCityId()), "a.city_id", param.getCityId());
    queryWrapper.eq(ValidateUtils.isNotEmpty(param.getAreaId()), "a.area_id", param.getAreaId());
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

    for (HouseVO houseVO : records) {
      houseVO.setAddress(RSAUtils.decrypt(houseVO.getAddress()));
      String owner = RSAUtils.decrypt(houseVO.getOwner());
      String phone = RSAUtils.decrypt(houseVO.getPhone());
      houseVO.setPhone(phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
      houseVO.setOwner(StringUtils.rightPad(StringUtils.left(owner, 1), StringUtils.length(owner), "*"));

      Area area = areaMap.get(houseVO.getProvinceId());
      houseVO.setProvinceName(ValidateUtils.isNotEmpty(area) ? area.getName() : null);
      area = areaMap.get(houseVO.getCityId());
      houseVO.setCityName(ValidateUtils.isNotEmpty(area) ? area.getName() : null);
      area = areaMap.get(houseVO.getAreaId());
      houseVO.setAreaName(ValidateUtils.isNotEmpty(area) ? area.getName() : null);
    }
    page.setRecords(records);
    return page;
  }

  @Override
  public HouseVO get(HouseGetParam param) {
    LambdaQueryWrapper<House> queryWrapper = new LambdaQueryWrapper<House>();
    queryWrapper.eq(House::getDelFlag, DelFlagEnum.NOT_DELETE.Value());
    queryWrapper.eq(House::getId,param.getId());
    HouseVO house = this.baseMapper.get(queryWrapper);
    if (ValidateUtils.isNotEmpty(house)) {
      house.setAddress(RSAUtils.decrypt(house.getAddress()));
      String owner = RSAUtils.decrypt(house.getOwner());
      String phone = RSAUtils.decrypt(house.getPhone());
      house.setPhone(phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
      house.setOwner(StringUtils.rightPad(StringUtils.left(owner, 1), StringUtils.length(owner), "*"));

      List<Long>    areaIds = new ArrayList<>();
      areaIds.add(house.getAreaId());
      areaIds.add(house.getCityId());
      areaIds.add(house.getProvinceId());
      areaIds.remove(null);

      Map<Long,Area> areaMap = areaService.findMapByIds(areaIds);
      Area area = areaMap.get(house.getProvinceId());
      house.setProvinceName(ValidateUtils.isNotEmpty(area) ? area.getName() : null);
      area = areaMap.get(house.getCityId());
      house.setCityName(ValidateUtils.isNotEmpty(area) ? area.getName() : null);
      area = areaMap.get(house.getAreaId());
      house.setAreaName(ValidateUtils.isNotEmpty(area) ? area.getName() : null);
    }
    return house;
  }

  @Override
  public HouseVO get(String id) {
    HouseGetParam param = new HouseGetParam();
    param.setId(id);
    return this.get(param);
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
