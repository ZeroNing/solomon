package com.steven.solomon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.steven.solomon.entity.House;
import com.steven.solomon.entity.HouseConfig;
import com.steven.solomon.entity.HouseConfigFloorRoom;
import com.steven.solomon.entity.Room;
import com.steven.solomon.mapper.RoomMapper;
import com.steven.solomon.param.HouseConfigSaveParam;
import com.steven.solomon.service.RoomService;
import com.steven.solomon.utils.json.JackJsonUtils;
import com.steven.solomon.utils.lambda.LambdaUtils;
import com.steven.solomon.utils.verification.ValidateUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@DubboService
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public void save(HouseConfig houseConfig, House house) throws IOException {
    //获取总楼层数
    Integer totalFloors = house.getTotalFloors();
    //获取每层房间数量
    Integer roomNum     = house.getNum();
    //获取房屋id
    String houseId = house.getId();

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
        room.setType(ValidateUtils.isNotEmpty(houseConfigFloorRoom) ? houseConfigFloorRoom.getRoomType() : null);
        room.setRoomNum(num+"0" + roomStartNum);
        room.setHouseId(houseId);
        saveList.add(room);
      }
    }
    this.saveBatch(saveList);
  }
}
