package com.steven.solomon.service;

import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.param.RoomSaveParam;

public interface RoomService {

  String save(RoomSaveParam param) throws BaseException;
}
