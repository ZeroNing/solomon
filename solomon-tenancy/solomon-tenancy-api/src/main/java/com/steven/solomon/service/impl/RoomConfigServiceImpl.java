package com.steven.solomon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.code.TenancyErrorCode;
import com.steven.solomon.entity.Room;
import com.steven.solomon.entity.RoomConfig;
import com.steven.solomon.enums.RoomConfigTypeEnum;
import com.steven.solomon.mapper.RoomConfigMapper;
import com.steven.solomon.param.RoomConfigSaveParam;
import com.steven.solomon.service.RoomConfigService;
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
public class RoomConfigServiceImpl extends ServiceImpl<RoomConfigMapper,RoomConfig> implements RoomConfigService {

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public void save(List<RoomConfigSaveParam> roomConfigSaveParams, Room room) throws BaseException, JsonProcessingException {
    if(ValidateUtils.isNotEmpty(roomConfigSaveParams)){
      return;
    }
    Map<String,List<RoomConfig>> map = findMapByRoomId(room.getId());

    List<RoomConfig> saveConfigList = new ArrayList<>();
    Set<String>      delConfigs     = new HashSet<>();
    RoomConfig       roomConfig     = new RoomConfig();
    for(RoomConfigSaveParam roomConfigSaveParam: roomConfigSaveParams){
      RoomConfigTypeEnum typeEnum = ValidateUtils.isEmpty(EnumUtils.codeOf(RoomConfigTypeEnum.class,roomConfigSaveParam.getType()),TenancyErrorCode.ROOM_CONFIG_TYPE_IS_NULL);
      roomConfig.setRoomId(room.getId());
      roomConfig.setType(typeEnum.toString());
      roomConfig.setJson(JackJsonUtils.formatJsonByFilter(roomConfigSaveParam));
      saveConfigList.add(roomConfig);
      List<RoomConfig> list = map.get(roomConfigSaveParam.getType());
      if(ValidateUtils.isNotEmpty(list)){
        delConfigs.addAll(LambdaUtils.toList(list,RoomConfig :: getId));
      }
    }
    this.baseMapper.deleteBatchIds(delConfigs);
    this.saveBatch(saveConfigList);

  }

  @Override
  public List<RoomConfig> findByRoomId(String roomId) {
    LambdaQueryWrapper<RoomConfig> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(true,RoomConfig::getRoomId,roomId);
    return super.baseMapper.selectList(queryWrapper);
  }

  @Override
  public Map<String, List<RoomConfig>> findMapByRoomId(String roomId) {
    List<RoomConfig> list = findByRoomId(roomId);
    return ValidateUtils.isEmpty(list) ? new HashMap<>(1) : LambdaUtils.groupBy(list,RoomConfig::getType);
  }


}
