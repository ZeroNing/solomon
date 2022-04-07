package com.steven.solomon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.entity.House;
import com.steven.solomon.entity.HouseConfig;
import com.steven.solomon.param.RoomPageParam;
import com.steven.solomon.param.RoomUpdateParam;
import com.steven.solomon.vo.RoomVO;

import java.io.IOException;

public interface RoomService {

  void save(HouseConfig houseConfig, House house) throws IOException;

  void update(RoomUpdateParam params) throws BaseException;

  IPage<RoomVO> page(RoomPageParam params);
}
