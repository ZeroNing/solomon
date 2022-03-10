package com.steven.solomon.config.rabbitMQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	private Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);

	@Autowired
	private CachingConnectionFactory connectionFactory;

	/**
	 * 接受数据自动的转换为Json
	 */
	@Bean("messageConverter")
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean("rabbitTemplate")
	public RabbitTemplate rabbitTemplate() {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter());
		// 开启发送确认
		connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
		// 开启发送失败退回
		connectionFactory.setPublisherReturns(true);
		rabbitTemplate.setMandatory(true);
		rabbitTemplate.setMessageConverter(messageConverter());
		RabbitCallBack rabbitCallBack = new RabbitCallBack();
		rabbitTemplate.setConfirmCallback(rabbitCallBack);
		rabbitTemplate.setReturnsCallback(rabbitCallBack);
		return rabbitTemplate;
	}

	@Bean("rabbitAdmin")
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
		logger.info("RabbitAdmin启动了。。。");
		// 设置启动spring容器时自动加载这个类(这个参数现在默认已经是true，可以不用设置)
		rabbitAdmin.setAutoStartup(true);
		return rabbitAdmin;
	}

}
