package com.steven.solomon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.steven.solomon.entity.RoomConfig;
import com.steven.solomon.mapper.RoomConfigMapper;
import com.steven.solomon.service.RoomConfigService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@DubboService
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class RoomConfigServiceImpl extends ServiceImpl<RoomConfigMapper,RoomConfig> implements RoomConfigService {

}
