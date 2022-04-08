package com.steven.solomon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.code.TenancyErrorCode;
import com.steven.solomon.entity.House;
import com.steven.solomon.entity.HouseConfig;
import com.steven.solomon.enums.HouseConfigTypeEnum;
import com.steven.solomon.mapper.HouseConfigMapper;
import com.steven.solomon.param.HouseConfigSaveOrUpdateParam;
import com.steven.solomon.param.HouseConfigSaveParam;
import com.steven.solomon.param.HouseConfigUpdateParam;
import com.steven.solomon.service.HouseConfigService;
import com.steven.solomon.service.HouseService;
import com.steven.solomon.utils.json.JackJsonUtils;
import com.steven.solomon.utils.lambda.LambdaUtils;
import com.steven.solomon.utils.verification.ValidateUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@DubboService
public class HouseConfigServiceImpl extends ServiceImpl<HouseConfigMapper, HouseConfig> implements HouseConfigService {

  @Resource
  private HouseService houseService;

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public void save(List<HouseConfigSaveOrUpdateParam> houseConfigSaveOrUpdateParams, House house) throws BaseException, IOException {
    if(ValidateUtils.isEmpty(houseConfigSaveOrUpdateParams)){
      return;
    }
    Map<HouseConfigTypeEnum,List<HouseConfigSaveOrUpdateParam>> saveMap = LambdaUtils.groupBy(
        houseConfigSaveOrUpdateParams, HouseConfigSaveOrUpdateParam::getType);
    Map<String,List<HouseConfig>>                               map     = LambdaUtils.groupBy(findByHouseId(house.getId()),HouseConfig :: getType);

    List<HouseConfig> saveConfigList = new ArrayList<>();
    Set<String>       delConfigs     = new HashSet<>();
    HouseConfig       houseConfig    = null;

    for(Entry<HouseConfigTypeEnum, List<HouseConfigSaveOrUpdateParam>> entry : saveMap.entrySet()){
      List<Map<String,Object>> jsonList = new ArrayList<>();
      for(HouseConfigSaveOrUpdateParam param : entry.getValue()){
        ValidateUtils.isEmpty(param.getType(),TenancyErrorCode.HOUSE_CONFIG_TYPE_IS_NULL);
        Object obj = param.getDate();
        Map<String,Object> objectMap = JackJsonUtils.convertValue(obj,Map.class);
        objectMap.putAll(JackJsonUtils.convertValue(param,Map.class));
        objectMap.remove("date");
        jsonList.add(objectMap);
      }

      houseConfig    = new HouseConfig();
      houseConfig.create("1");
      houseConfig.setHouseId(house.getId());
      houseConfig.setType(entry.getKey().toString());
      houseConfig.setJson(JackJsonUtils.formatJsonByFilter(jsonList));
      saveConfigList.add(houseConfig);
      List<HouseConfig> config = map.get(entry.getKey().toString());
      if(ValidateUtils.isNotEmpty(config)){
        delConfigs.addAll(LambdaUtils.toList(config,HouseConfig::getId));
      }
    }

    if(ValidateUtils.isNotEmpty(delConfigs)){
      this.baseMapper.deleteBatchIds(delConfigs);
    }
    this.saveBatch(saveConfigList);

  }

  @Override
  public List<HouseConfig> findByHouseId(String houseId) {
    LambdaQueryWrapper<HouseConfig> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(true, HouseConfig::getHouseId, houseId);
    return super.baseMapper.selectList(queryWrapper);
  }

  @Override
  public Map<String, HouseConfig> findMapByHouseId(String houseId) {
    List<HouseConfig> list = findByHouseId(houseId);
    return ValidateUtils.isEmpty(list) ? new HashMap<>(1) : LambdaUtils.toMap(list, HouseConfig::getType);
  }

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public void save(HouseConfigSaveParam param) throws BaseException, JsonProcessingException {
    House house = ValidateUtils.isEmpty(houseService.get(param.getId()),TenancyErrorCode.HOUSE_IS_NULL);

    Map<HouseConfigTypeEnum,List<HouseConfigSaveParam.HouseConfigParam>> paramsMap = LambdaUtils.groupBy(
        param.getParams(), HouseConfigSaveParam.HouseConfigParam::getType);

    if(paramsMap.containsKey(HouseConfigTypeEnum.FLOOR_ROOM)){
      throw new BaseException(TenancyErrorCode.FLOOR_ROOM_NOT_SAVE_OR_UPDATE);
    }

    Map<String,List<HouseConfig>> map = LambdaUtils.groupBy(findByHouseId(house.getId()),HouseConfig :: getType);
    Set<String>       delConfigs     = new HashSet<>();

    List<HouseConfig> saveConfigList = new ArrayList<>();
    HouseConfig       houseConfig    = null;
    for(Entry<HouseConfigTypeEnum, List<HouseConfigSaveParam.HouseConfigParam>> entry : paramsMap.entrySet()){
      List<Map<String,Object>> jsonList = new ArrayList<>();
      for(HouseConfigSaveParam.HouseConfigParam configParam : entry.getValue()){
        Object obj = configParam.getDate();
        Map<String,Object> objectMap = JackJsonUtils.convertValue(obj,Map.class);
        objectMap.putAll(JackJsonUtils.convertValue(param,Map.class));
        objectMap.remove("date");
        jsonList.add(objectMap);
      }

      houseConfig    = new HouseConfig();
      houseConfig.create("1");
      houseConfig.setHouseId(house.getId());
      houseConfig.setType(entry.getKey().toString());
      houseConfig.setJson(JackJsonUtils.formatJsonByFilter(jsonList));
      saveConfigList.add(houseConfig);
      List<HouseConfig> config = map.get(entry.getKey().toString());
      if(ValidateUtils.isNotEmpty(config)){
        delConfigs.addAll(LambdaUtils.toList(config,HouseConfig::getId));
      }
    }

    if(ValidateUtils.isNotEmpty(delConfigs)){
      this.baseMapper.deleteBatchIds(delConfigs);
    }

    this.saveBatch(saveConfigList);
  }

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public void update(HouseConfigUpdateParam param) throws BaseException, JsonProcessingException {
    HouseConfig config = ValidateUtils.isEmpty(this.get(param.getId()),TenancyErrorCode.ROOM_CONFIG_IS_NULL);

    if(config.getType().equals(HouseConfigTypeEnum.FLOOR_ROOM.toString())){
      throw new BaseException(TenancyErrorCode.FLOOR_ROOM_NOT_SAVE_OR_UPDATE);
    }

    List<HouseConfig> list = this.findByTypeAndHouseId(param.getType(),param.getId());
    if(ValidateUtils.isNotEmpty(list)){
      List<String> deleteIds = LambdaUtils.toList(list,houseConfig -> !houseConfig.getId().equals(param.getId()),HouseConfig::getId);
      if(ValidateUtils.isNotEmpty(deleteIds)){
        this.removeBatchByIds(deleteIds);
      }
    }

    config.update("1");
    config.setType(param.getType().toString());
    config.setJson(JackJsonUtils.formatJsonByFilter(param.getDate()));
    this.update(config,null);
  }

  @Override
  public HouseConfig get(String id) {
    return this.getById(id);
  }

  @Override
  public List<HouseConfig> findByTypeAndHouseId(HouseConfigTypeEnum type, String houseId) {
    LambdaQueryWrapper<HouseConfig> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(true,HouseConfig::getHouseId,houseId);
    queryWrapper.eq(true,HouseConfig::getType,type.toString());
    return this.baseMapper.selectList(queryWrapper);
  }

}
