package com.steven.solomon.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.steven.solomon.pojo.entity.House;
import com.steven.solomon.pojo.vo.HouseVO;
import org.apache.ibatis.annotations.Param;

public interface HouseMapper extends BaseMapper<House> {

  IPage<HouseVO> page(@Param("page") Page page, @Param("ew") QueryWrapper queryWrapper);

  HouseVO get(@Param("ew") LambdaQueryWrapper queryWrapper);
}
