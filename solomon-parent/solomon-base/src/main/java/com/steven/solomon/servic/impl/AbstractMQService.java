package com.steven.solomon.servic.impl;

import com.steven.solomon.annotation.RabbitMq;
import com.steven.solomon.model.rabbitMQ.InitRabbitBinding;
import com.steven.solomon.servic.BaseMQService;
import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.BindingBuilder.DestinationConfigurer;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

public abstract class AbstractMQService implements BaseMQService {


  @Override
  public Queue initBinding(RabbitMq rabbitMq, RabbitAdmin admin, boolean isInitDlxMap, boolean isAddDlxPrefix) {
    InitRabbitBinding initRabbitBinding = new InitRabbitBinding(rabbitMq, isInitDlxMap, isAddDlxPrefix);
    // 初始化队列
    Queue queue = new Queue(initRabbitBinding.getQueue(), rabbitMq.isPersistence(), false, false,initRabbitBinding.getArgs());
    // 绑定队列
    admin.declareQueue(queue);
    // 绑定交换机
    AbstractExchange exchange = initExchange(initRabbitBinding.getExchange());
    admin.declareExchange(exchange);
    // 绑定
    admin.declareBinding(this.initBinding(BindingBuilder.bind(queue),exchange,initRabbitBinding.getRoutingKey()));
    return queue;
  }

  /**
   * 初始化交换机
   */
  public abstract AbstractExchange initExchange(String exchange);

  /**
   * 初始化绑定
   */
  public abstract Binding initBinding(DestinationConfigurer destinationConfigurer,AbstractExchange exchange,String routingKey);
}
