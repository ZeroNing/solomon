package com.steven.solomon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.entity.Room;
import com.steven.solomon.entity.RoomConfig;
import com.steven.solomon.param.RoomConfigSaveParam;
import java.util.List;
import java.util.Map;

public interface RoomConfigService {

  void save(List<RoomConfigSaveParam> roomConfigSaveParams, Room room) throws BaseException, JsonProcessingException;

  List<RoomConfig> findByRoomId(String roomId);

  Map<String,List<RoomConfig>> findMapByRoomId(String roomId);
}
