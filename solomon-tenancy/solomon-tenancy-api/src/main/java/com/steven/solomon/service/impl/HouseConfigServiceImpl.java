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
import com.steven.solomon.utils.enums.EnumUtils;
import com.steven.solomon.utils.json.JackJsonUtils;
import com.steven.solomon.utils.lambda.LambdaUtils;
import com.steven.solomon.utils.verification.ValidateUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@DubboService
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class HouseConfigServiceImpl extends ServiceImpl<HouseConfigMapper, HouseConfig> implements HouseConfigService {

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public void save(List<HouseConfigSaveParam> houseConfigSaveParams, House house) throws BaseException, JsonProcessingException {
    if(ValidateUtils.isNotEmpty(houseConfigSaveParams)){
      return;
    }
    Map<String,List<HouseConfig>> map = findMapByRoomId(house.getId());

    List<HouseConfig> saveConfigList = new ArrayList<>();
    Set<String>       delConfigs     = new HashSet<>();
    HouseConfig       houseConfig    = new HouseConfig();
    for(HouseConfigSaveParam houseConfigSaveParam : houseConfigSaveParams){
      HouseConfigTypeEnum typeEnum = ValidateUtils.isEmpty(EnumUtils.codeOf(HouseConfigTypeEnum.class,
          houseConfigSaveParam.getType()),TenancyErrorCode.ROOM_CONFIG_TYPE_IS_NULL);
      houseConfig.setRoomId(house.getId());
      houseConfig.setType(typeEnum.toString());
      houseConfig.setJson(JackJsonUtils.formatJsonByFilter(houseConfigSaveParam));
      saveConfigList.add(houseConfig);
      List<HouseConfig> list = map.get(houseConfigSaveParam.getType());
      if(ValidateUtils.isNotEmpty(list)){
        delConfigs.addAll(LambdaUtils.toList(list, HouseConfig:: getId));
      }
    }
    this.baseMapper.deleteBatchIds(delConfigs);
    this.saveBatch(saveConfigList);

  }

  @Override
  public List<HouseConfig> findByRoomId(String roomId) {
    LambdaQueryWrapper<HouseConfig> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(true, HouseConfig::getRoomId,roomId);
    return super.baseMapper.selectList(queryWrapper);
  }

  @Override
  public Map<String, List<HouseConfig>> findMapByRoomId(String roomId) {
    List<HouseConfig> list = findByRoomId(roomId);
    return ValidateUtils.isEmpty(list) ? new HashMap<>(1) : LambdaUtils.groupBy(list, HouseConfig::getType);
  }


}
