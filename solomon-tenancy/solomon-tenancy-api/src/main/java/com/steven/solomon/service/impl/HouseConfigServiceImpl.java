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
import com.steven.solomon.param.HouseConfigSaveParam;
import com.steven.solomon.service.HouseConfigService;
import com.steven.solomon.utils.json.JackJsonUtils;
import com.steven.solomon.utils.lambda.LambdaUtils;
import com.steven.solomon.utils.verification.ValidateUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@DubboService
public class HouseConfigServiceImpl extends ServiceImpl<HouseConfigMapper, HouseConfig> implements HouseConfigService {

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public void save(List<HouseConfigSaveParam> houseConfigSaveParams, House house) throws BaseException, IOException {
    if(ValidateUtils.isEmpty(houseConfigSaveParams)){
      return;
    }
    Map<HouseConfigTypeEnum,List<HouseConfigSaveParam>> saveMap = LambdaUtils.groupBy(houseConfigSaveParams,HouseConfigSaveParam::getType);
    Map<String,List<HouseConfig>> map = LambdaUtils.groupBy(findByHouseId(house.getId()),HouseConfig :: getType);

    List<HouseConfig> saveConfigList = new ArrayList<>();
    Set<String>       delConfigs     = new HashSet<>();
    HouseConfig       houseConfig    = null;

    for(Entry<HouseConfigTypeEnum, List<HouseConfigSaveParam>> entry : saveMap.entrySet()){
      List<Map<String,Object>> jsonList = new ArrayList<>();
      for(HouseConfigSaveParam param : entry.getValue()){
        ValidateUtils.isEmpty(param.getType(),TenancyErrorCode.HOUSE_CONFIG_TYPE_IS_NULL);
        List<Object> objects = new ArrayList<>();
        Object obj = param.getDate();
        Map<String,Object> objectMap = JackJsonUtils.convertValue(obj,Map.class);
        objectMap.putAll(JackJsonUtils.convertValue(param,Map.class));
        objects.add(JackJsonUtils.conversionClass(JackJsonUtils.formatJsonByFilter(map),Object.class));
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

}
