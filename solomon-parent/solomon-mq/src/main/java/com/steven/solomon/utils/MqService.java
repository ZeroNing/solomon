package com.steven.solomon.utils;

import com.steven.solomon.pojo.BaseMq;

public interface MqService {

  /**
   * 发送消息
   * @param mq
   * @throws Exception
   */
  void send(BaseMq mq) throws Exception;

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