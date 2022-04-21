package com.steven.solomon.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.steven.solomon.pojo.Room;
import com.steven.solomon.vo.RoomVO;
import org.apache.ibatis.annotations.Param;

public interface RoomMapper extends BaseMapper<Room> {

    IPage<RoomVO> page(@Param("page") Page page, @Param("ew") QueryWrapper queryWrapper);
}
