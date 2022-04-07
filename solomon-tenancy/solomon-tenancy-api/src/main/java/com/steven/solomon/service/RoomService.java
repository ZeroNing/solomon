package com.steven.solomon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.entity.Room;
import com.steven.solomon.param.RoomGetParam;
import com.steven.solomon.param.RoomPageParam;
import com.steven.solomon.param.RoomSaveParam;
import com.steven.solomon.param.RoomUpdateParam;
import com.steven.solomon.vo.RoomVO;

public interface RoomService {

  String save(RoomSaveParam param) throws BaseException, JsonProcessingException;

  void update(RoomUpdateParam param) throws BaseException, JsonProcessingException;

  IPage<RoomVO> page(RoomPageParam params);

  Room get(RoomGetParam param);
}
