package com.steven.solomon.service;

import com.steven.solomon.logger.LoggerUtils;
import org.slf4j.Logger;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;

public abstract class AbstractRabbitCallBack {

  private Logger logger = LoggerUtils.logger(AbstractRabbitCallBack.class);

  /**
   * 保存mq消费成功或失败后方法
   */
  public abstract void saveRabbitCallBack(CorrelationData correlationData, boolean ack, String cause);
  /**
   * 保存mq消息丢失方法
   */
  public abstract void saveReturnedMessage(ReturnedMessage returned);

  public void confirm(CorrelationData correlationData, boolean ack, String cause){
    if (!ack) {
      logger.info("RabbitMQConfig:消息发送失败:correlationData({}),ack(false),cause({})", correlationData,cause);
    } else {
      logger.info("RabbitMQConfig:消息发送成功::correlationData({}),ack(true),cause({})", correlationData,cause);
    }
    this.saveRabbitCallBack(correlationData,ack,cause);
  }

  public void confirm(ReturnedMessage returned){
    logger.info("RabbitMQConfig:消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",
            returned.getExchange(), returned.getRoutingKey(), returned.getReplyCode(), returned.getReplyText(), returned.getMessage());
    this.saveReturnedMessage(returned);
  }
}
