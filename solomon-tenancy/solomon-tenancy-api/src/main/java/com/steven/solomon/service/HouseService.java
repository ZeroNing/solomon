package com.steven.solomon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.steven.solomon.exception.BaseException;
import com.steven.solomon.param.HouseGetParam;
import com.steven.solomon.param.HouseInitParam;
import com.steven.solomon.param.HousePageParam;
import com.steven.solomon.param.HouseSaveParam;
import com.steven.solomon.param.HouseUpdateParam;
import com.steven.solomon.vo.HouseVO;
import java.io.IOException;

public interface HouseService {

  String save(HouseSaveParam param) throws BaseException, IOException;

  void update(HouseUpdateParam param) throws BaseException, IOException;

  IPage<HouseVO> page(HousePageParam params);

  HouseVO get(HouseGetParam param);

  HouseVO get(String id);

  void init(HouseInitParam param) throws BaseException, IOException;
}
