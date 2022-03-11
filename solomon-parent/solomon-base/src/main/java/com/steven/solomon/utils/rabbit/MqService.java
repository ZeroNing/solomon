package com.steven.solomon.utils.rabbit;

import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.base.model.BaseMq;

public interface MqService {

  /**
   * 发送消息
   * @param mq
   * @throws BaseException
   */
  void send(BaseMq mq) throws BaseException;

  /**
   *重置队列并发使用者
   * @param queueName 队列满
   * @param concurrentConsumers 并发数
   */
  boolean resetQueueConcurrentConsumers(String queueName, int concurrentConsumers);

  /**
   * 重启消息监听者
   * @param queueName 队列名
   */
  boolean restartMessageListener(String queueName);

  /**
   * 停止消息监听者
   * @param queueName 队列名
   */
  boolean stopMessageListener(String queueName);
}
