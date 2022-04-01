package com.steven.solomon.rabbitMQ.service.impl;

import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder.DestinationConfigurer;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.stereotype.Service;

@Service("topicMQService")
public class TopicMQService extends AbstractMQService {

	@Override
	public AbstractExchange initExchange(String exchangeName) {
		return new TopicExchange(exchangeName);
	}

	@Override
	public Binding initBinding(DestinationConfigurer bindConfigurer,AbstractExchange exchange,String routingKey) {
		return bindConfigurer.to((TopicExchange) exchange).with(routingKey);
	}
}
