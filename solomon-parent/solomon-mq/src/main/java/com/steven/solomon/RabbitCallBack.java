package com.steven.solomon;

import com.steven.solomon.logger.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnsCallback;

public class RabbitCallBack implements ReturnsCallback, ConfirmCallback {

  private Logger logger = LoggerUtils.logger(RabbitCallBack.class);

  @Override
  public void confirm(CorrelationData correlationData, boolean ack, String cause) {
    if (!ack) {
      logger.info("RabbitMQConfig:消息发送失败:correlationData({}),ack(false),cause({})", correlationData,cause);
    } else {
      logger.info("RabbitMQConfig:消息发送成功::correlationData({}),ack(true),cause({})", correlationData,cause);
    }
  }

  @Override
  public void returnedMessage(ReturnedMessage returned) {
    logger.info("RabbitMQConfig:消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",
        returned.getExchange(), returned.getRoutingKey(), returned.getReplyCode(), returned.getReplyText(), returned.getMessage());
  }
}
