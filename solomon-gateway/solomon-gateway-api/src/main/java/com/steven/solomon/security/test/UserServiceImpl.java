package com.steven.solomon.security.test;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.steven.solomon.mapper.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@Service
@DubboService
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
