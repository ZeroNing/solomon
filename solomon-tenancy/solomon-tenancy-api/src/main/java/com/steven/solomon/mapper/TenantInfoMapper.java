package com.steven.solomon.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.steven.solomon.pojo.TenantInfo;
import com.steven.solomon.vo.TenantInfoVO;
import org.apache.ibatis.annotations.Param;

public interface TenantInfoMapper extends BaseMapper<TenantInfo> {

  IPage<TenantInfoVO> page(@Param("page") Page page, @Param("ew") QueryWrapper queryWrapper);
}
