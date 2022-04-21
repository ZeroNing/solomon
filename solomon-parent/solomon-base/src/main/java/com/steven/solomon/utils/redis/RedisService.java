package com.steven.solomon.utils.redis;

import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.constant.code.BaseExceptionCode;
import com.steven.solomon.utils.verification.ValidateUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.formula.functions.T;
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
  public boolean expire(String group,String key, Integer time) {
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
  public void del(String group,String... key) {
    if (ValidateUtils.isNotEmpty(key) && key.length > 0) {
      if (key.length == 1) {
        redisTemplate.delete(assembleKey(group, key[0]));
      } else {
        List<String> keys = new ArrayList<>();
        Arrays.asList(key).stream().forEach(a -> keys.add(assembleKey(group,a)));
        redisTemplate.delete(keys);
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
  public <T> T set(String group,String key, T value) throws BaseException {
    try {
      redisTemplate.boundValueOps(assembleKey(group, key)).set(value);
      return value;
    } catch (Exception e) {
      throw new BaseException(BaseExceptionCode.BASE_EXCEPTION_CODE);
    }
  }

  @Override
  public <T> T set(String group,String key, T value, Integer time) throws BaseException {
    try {
      if (time > 0) {
        redisTemplate.boundValueOps(assembleKey(group, key)).set(value, time, TimeUnit.SECONDS);
      } else {
        set(group,key, value);
      }
      return value;
    } catch (Exception e) {
      throw new BaseException(BaseExceptionCode.BASE_EXCEPTION_CODE);
    }
  }

  @Override
  public boolean lockSet(String group,String key, Object value, Integer time) {
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
