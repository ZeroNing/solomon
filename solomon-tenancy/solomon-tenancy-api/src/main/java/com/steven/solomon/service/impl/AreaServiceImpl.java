package com.steven.solomon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.steven.solomon.entity.Area;
import com.steven.solomon.mapper.AreaMapper;
import com.steven.solomon.service.AreaService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@DubboService
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class AreaServiceImpl extends ServiceImpl<AreaMapper,Area> implements AreaService {

  @Override
  public Area findById(Long id) {
    return baseMapper.selectById(id);
  }
}
