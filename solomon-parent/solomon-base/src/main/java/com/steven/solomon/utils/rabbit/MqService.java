package com.steven.solomon.utils.rabbit;

import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.base.model.BaseMq;

public interface MqService {

  void send(BaseMq mq) throws BaseException;

  boolean resetQueueConcurrentConsumers(String queueName, int concurrentConsumers);

  boolean restartMessageListener(String queueName);

  boolean stopMessageListener(String queueName);
}
