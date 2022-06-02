package com.steven.solomon.service;

import com.steven.solomon.config.RabbitCallBack;
import com.steven.solomon.logger.LoggerUtils;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.connection.CorrelationData;

public abstract class AbstractRabbitCallBack {

  private Logger logger = LoggerUtils.logger(AbstractRabbitCallBack.class);

  public abstract void saveRabbitCallBack(CorrelationData correlationData, boolean ack, String cause);

  public void confirm(CorrelationData correlationData, boolean ack, String cause){
    if (!ack) {
      logger.info("RabbitMQConfig:消息发送失败:correlationData({}),ack(false),cause({})", correlationData,cause);
    } else {
      logger.info("RabbitMQConfig:消息发送成功::correlationData({}),ack(true),cause({})", correlationData,cause);
    }
    this.saveRabbitCallBack(correlationData,ack,cause);
  }
}
