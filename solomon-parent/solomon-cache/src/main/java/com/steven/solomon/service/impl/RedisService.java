package com.steven.solomon.service.impl;

import com.steven.solomon.service.AbsICacheService;
import com.steven.solomon.template.DynamicRedisTemplate;
import com.steven.solomon.verification.ValidateUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Redis工具类
 *
 * @author ZENG.XIAO.YAN
 * @date 2018年6月7日
 */
@Service("redisService")
public class RedisService extends AbsICacheService {

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  // =============================common============================

  @Override
  public boolean expire(String group, String key, Integer time) {
    try {
      if (time > 0) {
        redisTemplate.expire(assembleKey(group, key), time, TimeUnit.SECONDS);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public long getExpire(String group, String key) {
    return redisTemplate.getExpire(assembleKey(group, key), TimeUnit.SECONDS);
  }

  @Override
  public boolean hasKey(String group, String key) {
    try {
      return redisTemplate.hasKey(assembleKey(group, key));
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public void del(String group, String... key) {

    if (!ValidateUtils.isEmpty(key) && key.length > 0) {
      if (key.length == 1) {
        redisTemplate.delete(assembleKey(group, key[0]));
      } else {
        List<String> keys = new ArrayList<>();
        Arrays.asList(key).stream().forEach(a -> keys.add(assembleKey(group, a)));
        redisTemplate.delete(keys);
      }
    }
  }

  // ============================String=============================

  @Override
  public Object get(String group, String key) {
    String k = assembleKey(group, key);
    return ValidateUtils.isEmpty(k) ? null : redisTemplate.boundValueOps(k).get();
  }

  @Override
  public <T> T set(String group, String key, T value) {
    redisTemplate.boundValueOps(assembleKey(group, key)).set(value);
    return value;
  }

  @Override
  public <T> T set(String group, String key, T value, Integer time) {
    if (time > 0) {
      redisTemplate.boundValueOps(assembleKey(group, key)).set(value, time, TimeUnit.SECONDS);
    } else {
      set(group, key, value);
    }
    return value;
  }

  @Override
  public boolean lockSet(String group, String key, Object value, Integer time) {
    Boolean success = redisTemplate.opsForValue().setIfAbsent(assembleKey(group, key), value);
    redisTemplate.expire(key, time, TimeUnit.SECONDS);
    return !ValidateUtils.isEmpty(success) ? success : false;
  }

  @Override
  public void deleteLock(String group, String key) {
    redisTemplate.opsForValue().getOperations().delete(assembleKey(group, key));
  }

}
