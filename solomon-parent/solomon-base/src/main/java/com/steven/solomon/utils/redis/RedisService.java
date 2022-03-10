package com.steven.solomon.utils.redis;

import com.steven.solomon.utils.verification.ValidateUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
  public boolean expire(String key, long time) {
    try {
      if (time > 0) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public long getExpire(String key) {
    return redisTemplate.getExpire(key, TimeUnit.SECONDS);
  }

  @Override
  public boolean hasKey(String key) {
    try {
      return redisTemplate.hasKey(key);
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
  public Object get(String key) {
    return ValidateUtils.isEmpty(key) ? null : redisTemplate.boundValueOps(key).get();
  }

  @Override
  public boolean set(String key, Object value) {
    try {
      redisTemplate.boundValueOps(key).set(value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean set(String key, Object value, long time) {
    try {
      if (time > 0) {
        redisTemplate.boundValueOps(key).set(value, time, TimeUnit.SECONDS);
      } else {
        set(key, value);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean lockSet(String key, Object value, long time) {
    Boolean success = redisTemplate.opsForValue().setIfAbsent(key, value);
    redisTemplate.expire(key, time, TimeUnit.SECONDS);
    return ValidateUtils.isNotEmpty(success) ? success : false;
  }

  @Override
  public void deleteLock(String key) {
    redisTemplate.opsForValue().getOperations().delete(key);
  }

  @Override
  public long incr(String key, long delta, long seconds) {
    if (delta < 0) {
      throw new RuntimeException("递增因子必须大于0");
    }
    Long value = Long.valueOf(redisTemplate.boundValueOps(key).increment(delta));
    expire(key, seconds);
    return value;
  }

  @Override
  public long decr(String key, long delta) {
    if (delta < 0) {
      throw new RuntimeException("递减因子必须大于0");
    }
    return Long.valueOf(redisTemplate.boundValueOps(key).increment(-delta));
  }

  // ================================Map=================================

  @Override
  public Object hget(String key, String item) {
    return redisTemplate.boundHashOps(key).get(item);
  }

  @Override
  public Map<Object, Object> hmget(String key) {
    return redisTemplate.boundHashOps(key).entries();
  }

  @Override
  public boolean hmset(String key, Map<Object, Object> map) {
    try {
      redisTemplate.boundHashOps(key).putAll(map);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean hmset(String key, Map<Object, Object> map, long time) {
    try {
      redisTemplate.boundHashOps(key).putAll(map);
      if (time > 0) {
        expire(key, time);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean hset(String key, String item, Object value) {
    try {
      redisTemplate.boundHashOps(key).put(item, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean hset(String key, String item, Object value, long time) {
    try {
      redisTemplate.boundHashOps(key).put(item, value);
      if (time > 0) {
        expire(key, time);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public void hdel(String key, Object... item) {
    redisTemplate.boundHashOps(key).delete(key, item);
  }

  @Override
  public boolean hHasKey(String key, String item) {
    return redisTemplate.boundHashOps(key).hasKey(item);
  }

  @Override
  public double hincr(String key, String item, double by) {
    return redisTemplate.boundHashOps(key).increment(item, by);
  }

  @Override
  public double hdecr(String key, String item, double by) {
    return redisTemplate.boundHashOps(key).increment(item, -by);
  }

  // ============================set=============================

  @Override
  public Set<Object> sGet(String key) {
    try {
      return redisTemplate.opsForSet().members(key);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public boolean sHasKey(String key, Object value) {
    try {
      return redisTemplate.opsForSet().isMember(key, value);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public long sSet(String key, Object... values) {
    try {
      return redisTemplate.opsForSet().add(key, values);
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  @Override
  public long sSetAndTime(String key, long time, Object... values) {
    try {
      Long count = redisTemplate.opsForSet().add(key, values);
      if (time > 0) {
        expire(key, time);
      }
      return count;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  @Override
  public long sGetSetSize(String key) {
    try {
      return redisTemplate.opsForSet().size(key);
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  @Override
  public long setRemove(String key, Object... values) {
    try {
      Long count = redisTemplate.opsForSet().remove(key, values);
      return count;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  // ===============================list=================================

  @Override
  public List<Object> lGet(String key, long start, long end) {
    try {
      return redisTemplate.opsForList().range(key, start, end);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public long lGetListSize(String key) {
    try {
      return redisTemplate.opsForList().size(key);
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  @Override
  public Object lGetIndex(String key, long index) {
    try {
      return redisTemplate.opsForList().index(key, index);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public boolean lSet(String key, Object value) {
    try {
      redisTemplate.opsForList().rightPush(key, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean lSet(String key, Object value, long time) {
    try {
      redisTemplate.opsForList().rightPush(key, value);
      if (time > 0) {
        expire(key, time);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean lSet(String key, List<Object> value) {
    try {
      redisTemplate.opsForList().rightPushAll(key, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean lSet(String key, List<Object> value, long time) {
    try {
      redisTemplate.opsForList().rightPushAll(key, value);
      if (time > 0) {
        expire(key, time);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean lUpdateIndex(String key, long index, Object value) {
    try {

      redisTemplate.opsForList().set(key, index, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public long lRemove(String key, long count, Object value) {
    try {
      Long remove = redisTemplate.opsForList().remove(key, count, value);
      return remove;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

}
