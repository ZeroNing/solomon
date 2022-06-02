package com.steven.solomon.config;

import com.steven.solomon.logger.LoggerUtils;
import com.steven.solomon.service.AbstractRabbitCallBack;
import com.steven.solomon.verification.ValidateUtils;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnsCallback;

public class RabbitCallBack implements ReturnsCallback, ConfirmCallback {

  private Logger logger = LoggerUtils.logger(RabbitCallBack.class);

  private static List<AbstractRabbitCallBack> rabbitCallBackList;

  public RabbitCallBack(List<AbstractRabbitCallBack> rabbitCallBackList){
    RabbitCallBack.rabbitCallBackList = rabbitCallBackList;
  }

  public RabbitCallBack(){
  }

  @Override
  public void confirm(CorrelationData correlationData, boolean ack, String cause) {
    if(ValidateUtils.isNotEmpty(RabbitCallBack.rabbitCallBackList)){
      for(AbstractRabbitCallBack abstractRabbitCallBack : RabbitCallBack.rabbitCallBackList){
        abstractRabbitCallBack.confirm(correlationData,ack,cause);
      }
    }
  }

  @Override
  public void returnedMessage(ReturnedMessage returned) {
    logger.info("RabbitMQConfig:消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",
        returned.getExchange(), returned.getRoutingKey(), returned.getReplyCode(), returned.getReplyText(), returned.getMessage());
  }
}