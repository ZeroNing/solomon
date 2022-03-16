package com.steven.solomon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.code.TenancyErrorCode;
import com.steven.solomon.entity.Room;
import com.steven.solomon.mapper.RoomMapper;
import com.steven.solomon.param.RoomGetParam;
import com.steven.solomon.param.RoomPageParam;
import com.steven.solomon.param.RoomSaveParam;
import com.steven.solomon.param.RoomUpdateParam;
import com.steven.solomon.service.AreaService;
import com.steven.solomon.service.RoomService;
import com.steven.solomon.utils.rsa.RSAUtils;
import com.steven.solomon.utils.verification.ValidateUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@DubboService
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

  @Resource(name="areaServiceImpl")
  private AreaService areaService;

  @Override
  @Transactional(readOnly = false)
  public String save(RoomSaveParam param) throws BaseException {
    Room room = new Room();
    room.create("1");

    ValidateUtils.isEmpty(areaService.findById(param.getProvinceId()),TenancyErrorCode.PROVINCE_NON_EXISTENT,param.getProvinceId().toString());
    ValidateUtils.isEmpty(areaService.findById(param.getCityId()),TenancyErrorCode.CITY_NON_EXISTENT,param.getCityId().toString());
    ValidateUtils.isEmpty(areaService.findById(param.getAreaId()),TenancyErrorCode.AREA_NON_EXISTENT,param.getAreaId().toString());

    room.setProvinceId(param.getProvinceId());
    room.setCityId(param.getCityId());
    room.setAreaId(param.getAreaId());
    room.setAddress(RSAUtils.encrypt(param.getAddress()));
    room.setPhone(param.getPhone());
    room.setOwner(param.getOwner());
    room.setTotalFloors(param.getTotalFloors());
    baseMapper.insert(room);

    return room.getId();
  }

  @Override
  @Transactional(readOnly = false)
  public void update(RoomUpdateParam param) throws BaseException {
    Room room = ValidateUtils.isEmpty(baseMapper.selectById(param.getId()),TenancyErrorCode.ROOM_IS_NULL);

    ValidateUtils.isEmpty(areaService.findById(param.getProvinceId()),TenancyErrorCode.PROVINCE_NON_EXISTENT,param.getProvinceId().toString());
    ValidateUtils.isEmpty(areaService.findById(param.getCityId()),TenancyErrorCode.CITY_NON_EXISTENT,param.getCityId().toString());
    ValidateUtils.isEmpty(areaService.findById(param.getAreaId()),TenancyErrorCode.AREA_NON_EXISTENT,param.getAreaId().toString());

    room.setProvinceId(param.getProvinceId());
    room.setCityId(param.getCityId());
    room.setAreaId(param.getAreaId());
    room.setAddress(RSAUtils.encrypt(param.getAddress()));
    room.setPhone(param.getPhone());
    room.setOwner(param.getOwner());
    room.setTotalFloors(param.getTotalFloors());
    baseMapper.updateById(room);
  }

  @Override
  public IPage<Room> page(RoomPageParam param){
    LambdaQueryWrapper<Room> queryWrapper = new LambdaQueryWrapper<>();
    if(ValidateUtils.isNotEmpty(param.getProvinceId())){
      queryWrapper.eq(Room :: getProvinceId,param.getProvinceId());
    }
    if(ValidateUtils.isNotEmpty(param.getCityId())){
      queryWrapper.eq(Room :: getCityId,param.getCityId());
    }

    if(ValidateUtils.isNotEmpty(param.getAreaId())){
      queryWrapper.eq(Room :: getAreaId,param.getAreaId());
    }
    IPage<Room> page = baseMapper.selectPage(new Page<Room>(param.getPageNo(),param.getPageSize()),queryWrapper);
    for (Room room : page.getRecords()) {
      room.setAddress(RSAUtils.decrypt(room.getAddress()));
    }
    return page;
  }

  @Override
  public Room get(RoomGetParam param) {
    Room room = baseMapper.selectById(param.getId());
    if(ValidateUtils.isNotEmpty(room)){
      room.setAddress(RSAUtils.decrypt(room.getAddress()));
    }
    return room;
  }
}
