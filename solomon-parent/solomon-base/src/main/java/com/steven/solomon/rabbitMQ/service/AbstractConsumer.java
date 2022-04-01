package com.steven.solomon.rabbitMQ.service;

import com.rabbitmq.client.Channel;
import com.steven.solomon.annotation.RabbitMqRetry;
import com.steven.solomon.base.cache.CacheTime;
import com.steven.solomon.base.code.BaseICacheCode;
import com.steven.solomon.base.model.rabbitMQ.RabbitMqModel;
import com.steven.solomon.utils.json.JackJsonUtils;
import com.steven.solomon.utils.logger.LoggerUtils;
import com.steven.solomon.utils.redis.ICaheService;
import com.steven.solomon.utils.verification.ValidateUtils;
import org.slf4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * RabbitMq消费器
 */
public abstract class AbstractConsumer<T> extends MessageListenerAdapter {

  private final Logger logger = LoggerUtils.logger(getClass());

  @Resource(name = "redisService")
  private ICaheService iCaheService;

  private final int retryNumber = 1;

  @Override
  public void onMessage(Message message, Channel channel) throws Exception {
    MessageProperties messageProperties = message.getMessageProperties();
    long              deliveryTag       = messageProperties.getDeliveryTag();
    String correlationId     = messageProperties.getHeader("spring_returned_message_correlation");
    String key  = "rabbitMQ-correlationId-lock:" + correlationId;
    try {
      // 消费者内容
      String json= new String(message.getBody(), StandardCharsets.UTF_8);
      logger.info("AbstractConsumer:消费者消息: {}", json);
      RabbitMqModel rabbitMqModel = JackJsonUtils.conversionClass(json, RabbitMqModel.class);
      // 消费者消费消息
      this.handleMessage((T) rabbitMqModel.getBody());
      // 手动确认消息
      channel.basicAck(deliveryTag, false);
    } catch (Exception e) {
      // 消费失败次数不等于空并且失败次数大于某个次数,不处理直接return,并记录到数据库
      logger.info("AbstractConsumer:消费报错 异常为:{}", e.getMessage());
      //将消费失败的记录保存到数据库或者不处理也可以
      this.saveFailMessage(message, e);
      //保存重试失败次数达到retryNumber上线后拒绝此消息入队列并删除redis
      saveFailNumber(messageProperties, channel, deliveryTag,correlationId);
      throw e;
    } finally {
      iCaheService.del(key);
    }
  }

  /**
   * 记录失败次数并决定是否拒绝此消息
   */
  public void saveFailNumber(MessageProperties messageProperties, Channel channel, long deliveryTag,String correlationId) throws Exception {
    String key  = "rabbitMQ-correlationId-lock:" + correlationId;
    Integer lock = (Integer) iCaheService.get(BaseICacheCode.RABBIT_FAIL_GROUP,key);
    Integer actualLock = ValidateUtils.isEmpty(lock) ? 1 : lock + 1;
    logger.info("rabbitMQ 失败记录:消费者correlationId为:{},deliveryTag为:{},失败次数为:{}", correlationId, deliveryTag,actualLock);
    int retryNumber = getRetryNumber();
    if(retryNumber <= this.retryNumber || actualLock >= retryNumber){
      if(retryNumber <= this.retryNumber){
        logger.info("rabbitMQ 失败记录:因记录不需要重试因此直接拒绝此消息,消费者correlationId为:{},消费者设置重试次数为:{}", correlationId, retryNumber);
      } else {
        logger.info("rabbitMQ 失败记录:已满足重试次数,删除redis消息并且拒绝此消息,消费者correlationId为:{},重试次数为:{}", correlationId, actualLock);
        iCaheService.del(key);
      }
      channel.basicNack(messageProperties.getDeliveryTag(), false, false);
    } else {
      logger.info("rabbitMQ 失败记录:因记录重试次数还未达到重试上限，还将继续进行重试,消费者correlationId为:{},消费者设置重试次数为:{},现重试次数为:{}", correlationId, retryNumber,actualLock);
      iCaheService.set(BaseICacheCode.RABBIT_FAIL_GROUP,key, actualLock, CacheTime.CACHE_EXP_THIRTY_MINUTES);
    }
  }

  /**
   * 获取重试次数，默认为2
   *
   * @return
   */
  public int getRetryNumber() {
    RabbitMqRetry rabbitMqRetry = getClass().getAnnotation(RabbitMqRetry.class);
    return ValidateUtils.isEmpty(rabbitMqRetry) ? retryNumber : rabbitMqRetry.retryNumber();
  }

  /**
   * 消费方法
   * @param body 请求数据
   */
  public abstract void handleMessage(T body);

  /**
   * 保存消费失败的消息
   *
   * @param message mq所包含的信息
   * @param e 异常
   */
  public abstract void saveFailMessage(Message message, Exception e);
}