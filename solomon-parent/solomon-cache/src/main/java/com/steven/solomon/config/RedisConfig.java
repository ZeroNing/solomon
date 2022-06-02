package com.steven.solomon.config;

import com.steven.solomon.enums.CacheModeEnum;
import com.steven.solomon.logger.LoggerUtils;
import com.steven.solomon.manager.DynamicDefaultRedisCacheWriter;
import com.steven.solomon.manager.SpringRedisAutoManager;
import com.steven.solomon.profile.CacheProfile;
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
import javax.annotation.Resource;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Pool;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@DependsOn({"springUtil"})
public class RedisConfig extends CachingConfigurerSupport {

  private Logger logger = LoggerUtils.logger(getClass());

  @Resource
  private RedisProperties redisProperties;

  @Resource
  private CacheProfile cacheProfile;
  
  @PostConstruct
  public void afterPropertiesSet() {
    List<TenantRedisProperties> redisPropertiesList = new ArrayList<>();
    List<RedisClientPropertiesService> abstractRedisClientPropertiesServices = new ArrayList<>(
        SpringUtil.getBeansOfType(
                RedisClientPropertiesService.class).values());

    Map<String, RedisConnectionFactory> factoryMap = new ConcurrentHashMap<>();

    if((ValidateUtils.isNotEmpty(cacheProfile) && CacheModeEnum.NORMAL.toString().equalsIgnoreCase(cacheProfile.getMode())) || (CacheModeEnum.TENANT_PREFIX.toString().equalsIgnoreCase(cacheProfile.getMode()))){
      factoryMap.put("default", initConnectionFactory(redisProperties));
    }

    abstractRedisClientPropertiesServices.forEach(service -> {
      service.setRedisClient();
      redisPropertiesList.addAll(RedisTenantsHandler.getProperties());
    });

    redisPropertiesList.forEach(redisProperties -> {
      RedisConnectionFactory factory = initConnectionFactory(redisProperties);
      factoryMap.put(redisProperties.getTenantCode(), factory);
    });
    RedisTenantsHandler.setFactoryMap(factoryMap);
  }

  @Bean(name = "redisTemplate")
  public DynamicRedisTemplate dynamicMongoTemplate() {
    logger.info("初始化redis start");
    DynamicRedisTemplate<String, Object> redisTemplate = new DynamicRedisTemplate<String, Object>();
    // 注入数据源
    RedisConnectionFactory factory = RedisTenantsHandler.getFactoryMap().values().iterator().next();
    redisTemplate.setConnectionFactory(factory);
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
    return RedisTenantsHandler.getFactoryMap().values().iterator().next();
  }

  private LettuceConnectionFactory initConnectionFactory(RedisProperties redisProperties) {
    GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
    Pool                    pool                     = redisProperties.getLettuce().getPool();
    if(ValidateUtils.isNotEmpty(pool)){
      genericObjectPoolConfig.setMaxIdle(ValidateUtils.getOrDefault(pool.getMaxIdle(),0));
      genericObjectPoolConfig.setMinIdle(ValidateUtils.getOrDefault(pool.getMinIdle(),0));
      genericObjectPoolConfig.setMaxTotal(ValidateUtils.getOrDefault(pool.getMaxActive(),8));
      genericObjectPoolConfig.setMaxWaitMillis(ValidateUtils.getOrDefault(pool.getMaxWait().toMillis(),-1L));
      genericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(ValidateUtils.getOrDefault(pool.getTimeBetweenEvictionRuns().toMillis(),60L));
    }
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setDatabase(redisProperties.getDatabase());
    redisStandaloneConfiguration.setHostName(redisProperties.getHost());
    redisStandaloneConfiguration.setPort(redisProperties.getPort());
    redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));

    LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
//        .commandTimeout(ValidateUtils.getOrDefault(redisProperties.getTimeout(),Duration.ofMillis(60L)))
//        .shutdownTimeout(ValidateUtils.getOrDefault(redisProperties.getLettuce().getShutdownTimeout(),Duration.ofMillis(100)))
        .poolConfig(genericObjectPoolConfig)
        .build();
    LettuceConnectionFactory factory = new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
    factory.afterPropertiesSet();
    return factory;
  }

  @Bean
  public CacheManager cacheManager(){
    RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig().computePrefixWith((name -> name + ":"));
    SpringRedisAutoManager springRedisAutoManager = new SpringRedisAutoManager(DynamicDefaultRedisCacheWriter.nonLockingRedisCacheWriter(
        RedisTenantsHandler.getFactoryMap().values().iterator().next()), defaultCacheConfig);
    return springRedisAutoManager;
  }
}


