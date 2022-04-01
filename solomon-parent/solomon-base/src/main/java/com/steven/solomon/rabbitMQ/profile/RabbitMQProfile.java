package com.steven.solomon.rabbitMQ.profile;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("common.rabbitmq")
@Component
public class RabbitMQProfile {
  /**
   * 不启用的队列名，用逗号隔开
   */
  String notEnabledQueue;

  public String getNotEnabledQueue() {
    return notEnabledQueue;
  }

  public void setNotEnabledQueue(String notEnabledQueue) {
    this.notEnabledQueue = notEnabledQueue;
  }
}
