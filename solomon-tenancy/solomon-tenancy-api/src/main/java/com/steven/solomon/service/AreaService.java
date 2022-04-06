package com.steven.solomon.service;

import com.steven.solomon.entity.Area;
import java.util.List;

public interface AreaService {

  Area findById(Long id);

  List<Area> findByIds(List<Long> ids);
}
