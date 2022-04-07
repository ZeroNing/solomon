package com.steven.solomon.service;

import com.steven.solomon.entity.House;
import com.steven.solomon.entity.HouseConfig;
import java.io.IOException;

public interface RoomService {

  void save(HouseConfig houseConfig, House house) throws IOException;
}
