package com.steven.solomon.service;

import com.steven.solomon.entity.Area;
import java.util.List;
import java.util.Map;

public interface AreaService {

  Area findById(Long id);

  List<Area> findByIds(List<Long> ids);

  /**
   * 根据地图id搜索Area key:主键 value:地图
   * @param ids
   * @return
   */
  Map<Long,Area> findMapByIds(List<Long> ids);
}
