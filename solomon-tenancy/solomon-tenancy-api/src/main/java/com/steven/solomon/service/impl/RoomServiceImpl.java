package com.steven.solomon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.code.TenancyErrorCode;
import com.steven.solomon.entity.Area;
import com.steven.solomon.entity.Room;
import com.steven.solomon.entity.TenantInfo;
import com.steven.solomon.mapper.RoomMapper;
import com.steven.solomon.param.RoomGetParam;
import com.steven.solomon.param.RoomPageParam;
import com.steven.solomon.param.RoomSaveParam;
import com.steven.solomon.param.RoomUpdateParam;
import com.steven.solomon.service.AreaService;
import com.steven.solomon.service.RoomService;
import com.steven.solomon.utils.lambda.LambdaUtils;
import com.steven.solomon.utils.rsa.RSAUtils;
import com.steven.solomon.utils.verification.ValidateUtils;
import com.steven.solomon.vo.RoomVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@DubboService
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

  @Resource(name = "areaServiceImpl")
  private AreaService areaService;

  @Override
  @Transactional(readOnly = false)
  public String save(RoomSaveParam param) throws BaseException {
    Room room = new Room();
    room.create("1");

    ValidateUtils.isEmpty(areaService.findById(param.getProvinceId()), TenancyErrorCode.PROVINCE_NON_EXISTENT,
        param.getProvinceId().toString());
    ValidateUtils.isEmpty(areaService.findById(param.getCityId()), TenancyErrorCode.CITY_NON_EXISTENT,
        param.getCityId().toString());
    ValidateUtils.isEmpty(areaService.findById(param.getAreaId()), TenancyErrorCode.AREA_NON_EXISTENT,
        param.getAreaId().toString());

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
    Room room = ValidateUtils.isEmpty(baseMapper.selectById(param.getId()), TenancyErrorCode.ROOM_IS_NULL);
    room.update("1");
    ValidateUtils.isEmpty(areaService.findById(param.getProvinceId()), TenancyErrorCode.PROVINCE_NON_EXISTENT,
        param.getProvinceId().toString());
    ValidateUtils.isEmpty(areaService.findById(param.getCityId()), TenancyErrorCode.CITY_NON_EXISTENT,
        param.getCityId().toString());
    ValidateUtils.isEmpty(areaService.findById(param.getAreaId()), TenancyErrorCode.AREA_NON_EXISTENT,
        param.getAreaId().toString());

    LambdaUpdateWrapper<Room> updateQueryWrapper = new LambdaUpdateWrapper<Room>();
    updateQueryWrapper.eq(Room::getId, param.getId()).set(Room::getProvinceId, param.getProvinceId())
        .set(Room::getUpdateDate, room.getUpdateDate()).set(Room::getUpdateId, room.getUpdateId())
        .set(Room::getCityId, param.getCityId()).set(Room::getAreaId, param.getAreaId())
        .set(Room::getAddress, RSAUtils.encrypt(param.getAddress())).set(Room::getPhone, param.getPhone())
        .set(Room::getOwner, param.getOwner()).set(Room::getTotalFloors, param.getTotalFloors());
    baseMapper.updateById(room);
  }

  @Override
  public IPage<RoomVO> page(RoomPageParam param) {
    QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq(false, "a.province_id", param.getProvinceId());
    queryWrapper.eq(false, "a.city_id", param.getCityId());
    queryWrapper.eq(false, "a.area_id", param.getAreaId());

    IPage<RoomVO> page = baseMapper.page(new Page<Room>(param.getPageNo(), param.getPageSize()), queryWrapper);
    if(ValidateUtils.isEmpty(page)){
      return page;
    }
    List<RoomVO> records = page.getRecords();
    List<Long> areaIds = new ArrayList<>();
    areaIds.addAll(LambdaUtils.toList(records,room -> ValidateUtils.isNotEmpty(room.getProvinceId()),Room::getProvinceId));
    areaIds.addAll(LambdaUtils.toList(records,room -> ValidateUtils.isNotEmpty(room.getCityId()),Room::getCityId));
    areaIds.addAll(LambdaUtils.toList(records,room -> ValidateUtils.isNotEmpty(room.getAreaId()),Room::getAreaId));
    areaIds.remove(null);
    Map<Long,Area> areaMap = LambdaUtils.toMap(areaService.findByIds(areaIds),Area :: getId);
    for (RoomVO room : records) {
      room.setAddress(RSAUtils.decrypt(room.getAddress()));
      Area area = areaMap.get(room.getProvinceId());
      if(ValidateUtils.isNotEmpty(area)){
        room.setProvinceName(area.getName());
      }
      area = areaMap.get(room.getCityId());
      if(ValidateUtils.isNotEmpty(area)){
        room.setCityName(area.getName());
      }
      area = areaMap.get(room.getAreaId());
      if(ValidateUtils.isNotEmpty(area)){
        room.setAreaName(area.getName());
      }
    }
    page.setRecords(records);
    return page;
  }

  @Override
  public Room get(RoomGetParam param) {
    Room room = baseMapper.selectById(param.getId());
    if (ValidateUtils.isNotEmpty(room)) {
      room.setAddress(RSAUtils.decrypt(room.getAddress()));
    }
    return room;
  }
}
