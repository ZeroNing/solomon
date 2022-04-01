package com.steven.solomon.rabbitMQ.service.impl;

import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder.DestinationConfigurer;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.stereotype.Service;

@Service("fanoutMQService")
public class FanoutMQService extends AbstractMQService {

	@Override
	public AbstractExchange initExchange(String exchangeName) {
		return new FanoutExchange(exchangeName);
	}

	@Override
	public Binding initBinding(DestinationConfigurer bindConfigurer,AbstractExchange exchange,String routingKey) {
		return bindConfigurer.to((FanoutExchange) exchange);
	}
}
