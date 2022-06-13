package com.steven.solomon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.steven.solomon.code.TenancyErrorCode;
import com.steven.solomon.enums.DelFlagEnum;
import com.steven.solomon.exception.BaseException;
import com.steven.solomon.json.JackJsonUtils;
import com.steven.solomon.lambda.LambdaUtils;
import com.steven.solomon.mapper.RoomMapper;
import com.steven.solomon.pojo.entity.House;
import com.steven.solomon.pojo.entity.HouseConfig;
import com.steven.solomon.pojo.entity.HouseConfigFloorRoom;
import com.steven.solomon.pojo.entity.Room;
import com.steven.solomon.pojo.param.RoomPageParam;
import com.steven.solomon.pojo.param.RoomUpdateParam;
import com.steven.solomon.pojo.vo.RoomVO;
import com.steven.solomon.service.RoomService;
import com.steven.solomon.verification.ValidateUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@DubboService
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public void save(HouseConfig houseConfig, House house) throws IOException {
    //获取房屋id
    String houseId = house.getId();
    List<Room> roomList = this.findByHouseId(houseId);
    if(ValidateUtils.isNotEmpty(roomList)){
      this.baseMapper.deleteBatchIds(LambdaUtils.toList(roomList,Room :: getId));
    }

    //获取总楼层数
    Integer totalFloors = house.getTotalFloors();
    //获取每层房间数量
    Integer roomNum     = house.getNum();

    Map<Integer, HouseConfigFloorRoom> map = ValidateUtils.isNotEmpty(houseConfig) ? LambdaUtils
        .toMap(JackJsonUtils.conversionClassList(houseConfig.getJson(), HouseConfigFloorRoom.class),
            HouseConfigFloorRoom::getFloor) : new HashMap<>(1);

    List<Room> saveList = new ArrayList<>();
    Room       room     = null;
    for (int num = 1; num <= totalFloors; num++) {
      HouseConfigFloorRoom houseConfigFloorRoom = map.get(num);
      int roomEndNum = ValidateUtils.isNotEmpty(houseConfigFloorRoom) ? houseConfigFloorRoom.getNum() : roomNum;
      for (int roomStartNum = 1; roomStartNum <= roomEndNum; roomStartNum++) {
        room = new Room();
        room.create("1");
        room.setType(ValidateUtils.isNotEmpty(houseConfigFloorRoom) ? houseConfigFloorRoom.getRoomType() : null);
        room.setRoomNum(num+"0" + roomStartNum);
        room.setHouseId(houseId);
        saveList.add(room);
      }
    }
    this.saveBatch(saveList);
  }

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public void update(RoomUpdateParam params) throws BaseException {
    Room room = ValidateUtils.isEmpty(this.getById(params.getId()), TenancyErrorCode.ROOM_IS_NULL);
    room.update("1");
    this.updateById(room);
  }

  @Override
  public IPage<RoomVO> page(RoomPageParam params) {
    QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("a.house_id",params.getId());
    queryWrapper.eq("a.del_flag", DelFlagEnum.NOT_DELETE.label());
    return this.baseMapper.page(new Page<House>(params.getPageNo(), params.getPageSize()),queryWrapper);
  }

  @Override
  public List<Room> findByHouseId(String houseId) {
    LambdaQueryWrapper<Room> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(true,Room::getHouseId,houseId);
    return this.baseMapper.selectList(queryWrapper);
  }

}
