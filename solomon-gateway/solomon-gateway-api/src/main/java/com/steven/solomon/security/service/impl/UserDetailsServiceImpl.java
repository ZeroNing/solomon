package com.steven.solomon.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.steven.solomon.mapper.UserMapper;
import com.steven.solomon.security.test.User;
import javax.annotation.Resource;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

  @Resource
  private UserMapper userMapper;

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_name",username);
    return Mono.just(userMapper.selectOne(queryWrapper));
  }
}
