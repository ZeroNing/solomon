package com.steven.solomon.context;

import com.steven.solomon.logger.LoggerUtils;
import com.steven.solomon.profile.TenantRedisProperties;
import com.steven.solomon.serializer.BaseRedisSerializer;
import com.steven.solomon.spring.SpringUtil;
import com.steven.solomon.template.DynamicRedisTemplate;
import com.steven.solomon.verification.ValidateUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@DependsOn("springUtil")
public class RedisContext {

  private Logger logger = LoggerUtils.logger(getClass());

  private static final ThreadLocal<RedisConnectionFactory> REDIS_FACTORY_THREAD_LOCAL = new ThreadLocal<>();

  private static final Map<String,RedisConnectionFactory> REDIS_FACTORY_MAP = new ConcurrentHashMap<>();

  public static void setMongoFactoryMap(Map<String, RedisConnectionFactory> redisFactoryMap){
    RedisContext.REDIS_FACTORY_MAP.putAll(redisFactoryMap);
  }

  public static Map<String,RedisConnectionFactory> getRedisFactoryMap(){
    return RedisContext.REDIS_FACTORY_MAP;
  }

  public static RedisConnectionFactory getRedisFactory() {
    return REDIS_FACTORY_THREAD_LOCAL.get();
  }

  public static void removeMongoDbFactory(){
    REDIS_FACTORY_THREAD_LOCAL.remove();
  }

  @PostConstruct
  public void afterPropertiesSet() {
    List<TenantRedisProperties> redisPropertiesList = new ArrayList<>();
    List<AbstractRedisClientProperties> abstractRedisClientPropertiesServices = new ArrayList<>(SpringUtil.getBeansOfType(
        AbstractRedisClientProperties.class).values());
    if(ValidateUtils.isEmpty(abstractRedisClientPropertiesServices)){
      logger.info("不存在需要的用到的多租户redis配置");
      return;
    }

    abstractRedisClientPropertiesServices.forEach(service ->{
      service.setRedisClient();
      redisPropertiesList.addAll(service.getMongoClientList());
    });

    redisPropertiesList.forEach(redisProperties -> {
      LettuceConnectionFactory   factory = new LettuceConnectionFactory(redisProperties);
      REDIS_FACTORY_MAP.put(redisProperties.getTenantCode(),factory);
    });
  }

  @Bean(name = "redisTemplate")
  public DynamicRedisTemplate dynamicMongoTemplate() {
    logger.info("初始化redis start");
    DynamicRedisTemplate<String, Object> redisTemplate = new DynamicRedisTemplate<String, Object>();
    // 注入数据源
    redisTemplate.setConnectionFactory(REDIS_FACTORY_MAP.values().iterator().next());
    // 使用Jackson2JsonRedisSerialize 替换默认序列化
    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    BaseRedisSerializer   baseRedisSerializer   = new BaseRedisSerializer();
    // key-value结构序列化数据结构
    redisTemplate.setKeySerializer(stringRedisSerializer);
    redisTemplate.setValueSerializer(baseRedisSerializer);
    // hash数据结构序列化方式,必须这样否则存hash 就是基于jdk序列化的
    redisTemplate.setHashKeySerializer(stringRedisSerializer);
    redisTemplate.setHashValueSerializer(baseRedisSerializer);
    // 启用默认序列化方式
    redisTemplate.setEnableDefaultSerializer(true);
    redisTemplate.setEnableTransactionSupport(true);

    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }

  @Bean(name = "redisFactory")
  public RedisConnectionFactory redisFactory() {
    return REDIS_FACTORY_MAP.values().iterator().next();
  }

}


