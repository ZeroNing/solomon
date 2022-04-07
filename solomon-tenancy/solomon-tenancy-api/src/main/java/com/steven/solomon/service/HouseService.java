package com.steven.solomon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.entity.House;
import com.steven.solomon.param.HouseGetParam;
import com.steven.solomon.param.HouseInitParam;
import com.steven.solomon.param.HousePageParam;
import com.steven.solomon.param.HouseSaveParam;
import com.steven.solomon.param.HouseUpdateParam;
import com.steven.solomon.vo.HouseVO;
import java.io.IOException;

public interface HouseService {

  String save(HouseSaveParam param) throws BaseException, JsonProcessingException;

  void update(HouseUpdateParam param) throws BaseException, JsonProcessingException;

  IPage<HouseVO> page(HousePageParam params);

  House get(HouseGetParam param);

  void init(HouseInitParam param) throws BaseException, IOException;
}
