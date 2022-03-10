package com.steven.solomon.servic.impl;

import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder.DestinationConfigurer;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.stereotype.Service;

@Service("directMQService")
public class DirectMQService extends AbstractMQService {

	@Override
	public AbstractExchange initExchange(String exchangeName) {
		return new DirectExchange(exchangeName);
	}

	@Override
	public Binding initBinding(DestinationConfigurer bindConfigurer,AbstractExchange exchange,String routingKey) {
		return bindConfigurer.to((DirectExchange) exchange).with(routingKey);
	}

}
