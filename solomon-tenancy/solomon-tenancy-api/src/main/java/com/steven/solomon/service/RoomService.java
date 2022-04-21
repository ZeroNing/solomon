package com.steven.solomon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.steven.solomon.exception.BaseException;
import com.steven.solomon.pojo.House;
import com.steven.solomon.pojo.HouseConfig;
import com.steven.solomon.pojo.Room;
import com.steven.solomon.param.RoomPageParam;
import com.steven.solomon.param.RoomUpdateParam;
import com.steven.solomon.vo.RoomVO;

import java.io.IOException;
import java.util.List;

public interface RoomService {

  void save(HouseConfig houseConfig, House house) throws IOException;

  void update(RoomUpdateParam params) throws BaseException;

  IPage<RoomVO> page(RoomPageParam params);

  List<Room> findByHouseId(String houseId);
}
