package com.steven.solomon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.code.TenancyErrorCode;
import com.steven.solomon.entity.Room;
import com.steven.solomon.mapper.RoomMapper;
import com.steven.solomon.param.RoomSaveParam;
import com.steven.solomon.service.AreaService;
import com.steven.solomon.service.RoomService;
import com.steven.solomon.utils.verification.ValidateUtils;
import javax.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    room.setAddress(param.getAddress());
    room.setPhone(param.getPhone());
    room.setOwner(param.getOwner());
    room.setTotalFloors(param.getTotalFloors());
    baseMapper.insert(room);

    return room.getId();
  }
}
