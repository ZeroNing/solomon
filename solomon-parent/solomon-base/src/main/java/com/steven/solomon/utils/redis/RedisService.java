package com.steven.solomon.utils.redis;

import com.steven.solomon.utils.verification.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author ZENG.XIAO.YAN
 * @date 2018年6月7日
 */
@Service("redisService")
public class RedisService implements ICaheService {

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  // =============================common============================

  @Override
  public boolean expire(String group,String key, long time) {
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
  public long getExpire(String group,String key) {
    return redisTemplate.getExpire(assembleKey(group, key), TimeUnit.SECONDS);
  }

  @Override
  public boolean hasKey(String group,String key) {
    try {
      return redisTemplate.hasKey(assembleKey(group, key));
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public void del(String... key) {
    if (ValidateUtils.isNotEmpty(key) && key.length > 0) {
      if (key.length == 1) {
        redisTemplate.delete(key[0]);
      } else {
        redisTemplate.delete(Arrays.asList(key));
      }
    }
  }

  // ============================String=============================

  @Override
  public Object get(String group,String key) {
    String k = assembleKey(group, key);
    return ValidateUtils.isEmpty(k) ? null : redisTemplate.boundValueOps(k).get();
  }

  @Override
  public boolean set(String group,String key, Object value) {
    try {
      redisTemplate.boundValueOps(assembleKey(group, key)).set(value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean set(String group,String key, Object value, long time) {
    try {
      if (time > 0) {
        redisTemplate.boundValueOps(key).set(value, time, TimeUnit.SECONDS);
      } else {
        set(group,key, value);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean lockSet(String group,String key, Object value, long time) {
    Boolean success = redisTemplate.opsForValue().setIfAbsent(assembleKey(group, key), value);
    redisTemplate.expire(key, time, TimeUnit.SECONDS);
    return ValidateUtils.isNotEmpty(success) ? success : false;
  }

  @Override
  public void deleteLock(String group,String key) {
    redisTemplate.opsForValue().getOperations().delete(assembleKey(group, key));
  }

  private String assembleKey(String group,String key){
    StringBuilder sb = new StringBuilder();
    if(ValidateUtils.isNotEmpty(group)){
      sb.append(group).append(":");
    }
    return sb.append(key).toString();
  }
}
