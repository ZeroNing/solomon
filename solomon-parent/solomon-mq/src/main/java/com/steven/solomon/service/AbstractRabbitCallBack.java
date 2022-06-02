package com.steven.solomon.service;

import org.springframework.amqp.rabbit.connection.CorrelationData;

public abstract class AbstractRabbitCallBack {

  public abstract void saveRabbitCallBack(CorrelationData correlationData, boolean ack, String cause);
}
