package com.steven.solomon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.entity.House;
import com.steven.solomon.entity.HouseConfig;
import com.steven.solomon.param.HouseConfigSaveOrUpdateParam;
import com.steven.solomon.param.HouseConfigSaveParam;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface HouseConfigService {

  void save(List<HouseConfigSaveOrUpdateParam> houseConfigSaveOrUpdateParams, House house) throws BaseException, IOException;

  List<HouseConfig> findByHouseId(String houseId);

  Map<String,HouseConfig> findMapByHouseId(String houseId);

  void save(HouseConfigSaveParam param) throws BaseException, JsonProcessingException;
}
