package com.steven.solomon.config.redis;

import com.steven.solomon.utils.serializer.BaseRedisSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 初始化redi（默认用的是用JdkSerializationRedisSerializer进行序列化的）
	 * @param factory
	 * @return
	 */
	@Bean("redisTemplate")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		logger.info("初始化redis start");
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		// 注入数据源
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

}
