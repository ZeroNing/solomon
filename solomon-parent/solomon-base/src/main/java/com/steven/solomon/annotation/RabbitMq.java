package com.steven.solomon.annotation;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * rabbitmq标注注解
 * @author huangweihua
 */
@Target(value = { ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RabbitMq {

	@AliasFor(annotation = Component.class)
	String value() default "";

	/**
	 * 队列
	 */
	String[] queues();

	/**
	 * 交换器
	 */
	String exchange();

	/**
	 * 路由规则
	 */
	String routingKey() default "";

	/**
	 * 是否持久化
	 */
	boolean isPersistence() default true;

	/**
	 * 确认模式（只支持手动提交，自动提交代码暂时不支持）
	 */
	AcknowledgeMode mode() default AcknowledgeMode.MANUAL;

	/**
	 * 每个队列消费者数量
	 */
	int consumersPerQueue() default 1;

	/**
	 * 每次的接收的消息数量
	 */
	int prefetchCount() default 1;

	/**
	 * 交换类型（暂时不支持Headers、system，只支持DIRECT、TOPIC、FANOUT）
	 */
	String exchangeTypes() default ExchangeTypes.DIRECT;

	/**
	 * 消息最大存活时间
	 */
	long delay() default 0L;

	/**
	 * 死信队列Class
	 */
	Class dlxClazz() default void.class;
}
