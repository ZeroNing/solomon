package com.steven.solomon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.steven.solomon.exception.BaseException;
import com.steven.solomon.pojo.entity.House;
import com.steven.solomon.pojo.entity.HouseConfig;
import com.steven.solomon.pojo.entity.Room;
import com.steven.solomon.pojo.param.RoomPageParam;
import com.steven.solomon.pojo.param.RoomUpdateParam;

import com.steven.solomon.pojo.vo.RoomVO;
import java.io.IOException;
import java.util.List;

public interface RoomService {

  void save(HouseConfig houseConfig, House house) throws IOException;

  void update(RoomUpdateParam params) throws BaseException;

  IPage<RoomVO> page(RoomPageParam params);

  List<Room> findByHouseId(String houseId);
}
