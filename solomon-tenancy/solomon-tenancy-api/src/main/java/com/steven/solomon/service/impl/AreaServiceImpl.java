package com.steven.solomon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.steven.solomon.lambda.LambdaUtils;
import com.steven.solomon.pojo.Area;
import com.steven.solomon.mapper.AreaMapper;
import com.steven.solomon.param.AreaListParam;
import com.steven.solomon.service.AreaService;
import com.steven.solomon.service.ICaheService;
import com.steven.solomon.verification.ValidateUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@DubboService
public class AreaServiceImpl extends ServiceImpl<AreaMapper,Area> implements AreaService {

  @Resource
  private ICaheService iCaheService;

  @Override
  @Cacheable(cacheNames ="area", key="#param.getAreaCode()")
  public List<Area> findByAreaCode(AreaListParam param) {
    LambdaQueryWrapper<Area> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Area::getParentCode,param.getAreaCode());
    return super.baseMapper.selectList(queryWrapper);
  }

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
