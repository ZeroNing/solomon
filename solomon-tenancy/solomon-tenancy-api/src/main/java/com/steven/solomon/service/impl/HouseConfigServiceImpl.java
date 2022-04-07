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
    if(LambdaUtils.toSet(houseConfigSaveParams,HouseConfigSaveParam::getType).size() != houseConfigSaveParams.size()){
      throw new BaseException(TenancyErrorCode.HOUSE_CONFIG_TYPE_NOT_REPEAT);
    }
    Map<String,HouseConfig> map = findMapByHouseId(house.getId());

    List<HouseConfig> saveConfigList = new ArrayList<>();
    Set<String>       delConfigs     = new HashSet<>();
    HouseConfig       houseConfig    = null;
    for(HouseConfigSaveParam houseConfigSaveParam : houseConfigSaveParams){
      HouseConfigTypeEnum typeEnum = ValidateUtils.isEmpty(EnumUtils.codeOf(HouseConfigTypeEnum.class,
          houseConfigSaveParam.getType()),TenancyErrorCode.HOUSE_CONFIG_TYPE_IS_NULL);
      houseConfig    = new HouseConfig();
      houseConfig.setHouseId(house.getId());
      houseConfig.setType(typeEnum.toString());
      houseConfig.setJson(JackJsonUtils.formatJsonByFilter(houseConfigSaveParam));
      saveConfigList.add(houseConfig);
      HouseConfig config = map.get(houseConfigSaveParam.getType());
      if(ValidateUtils.isNotEmpty(config)){
        delConfigs.add(config.getId());
      }
    }
    this.baseMapper.deleteBatchIds(delConfigs);
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
