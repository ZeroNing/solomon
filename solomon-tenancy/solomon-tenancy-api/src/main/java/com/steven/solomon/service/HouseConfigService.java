package com.steven.solomon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.steven.solomon.exception.BaseException;
import com.steven.solomon.pojo.entity.House;
import com.steven.solomon.pojo.entity.HouseConfig;
import com.steven.solomon.pojo.enums.HouseConfigTypeEnum;
import com.steven.solomon.pojo.param.HouseConfigSaveOrUpdateParam;
import com.steven.solomon.pojo.param.HouseConfigSaveParam;
import com.steven.solomon.pojo.param.HouseConfigUpdateParam;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface HouseConfigService {

  void save(List<HouseConfigSaveOrUpdateParam> houseConfigSaveOrUpdateParams, House house) throws BaseException, IOException;

  List<HouseConfig> findByHouseId(String houseId);

  Map<String,HouseConfig> findMapByHouseId(String houseId);

  void save(HouseConfigSaveParam param) throws BaseException, JsonProcessingException;

  void update(HouseConfigUpdateParam param) throws BaseException, JsonProcessingException;

  HouseConfig get(String id);

  List<HouseConfig> findByTypeAndHouseId(HouseConfigTypeEnum type,String houseId);

  List<Map<String,Object>> findTypeEnumList();
}
