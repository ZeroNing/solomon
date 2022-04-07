package com.steven.solomon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.steven.solomon.entity.Area;
import com.steven.solomon.mapper.AreaMapper;
import com.steven.solomon.service.AreaService;
import com.steven.solomon.utils.lambda.LambdaUtils;
import com.steven.solomon.utils.verification.ValidateUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  @Override
  public List<Area> findByIds(List<Long> ids) {
    if(ValidateUtils.isEmpty(ids)){
      return null;
    }
    LambdaQueryWrapper<Area> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.in(Area::getId,ids);
    return super.baseMapper.selectList(queryWrapper);
  }

  @Override
  public Map<Long, Area> findMapByIds(List<Long> ids) {
    if(ValidateUtils.isEmpty(ids)){
      return new HashMap<>(1);
    }
    ids.remove(null);
    List<Area> list = findByIds(ids);
    return ValidateUtils.isEmpty(list) ? new HashMap<>(1) : LambdaUtils.toMap(list,Area :: getId);
  }
}
