package com.steven.solomon.base.model.rabbitMQ;


import com.steven.solomon.annotation.RabbitMq;
import com.steven.solomon.base.code.BaseRabbitMqCode;
import com.steven.solomon.utils.verification.ValidateUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class InitRabbitBinding implements Serializable {

  String queue;

  String exchange;

  String routingKey;

  Map<String, Object> args;

  public InitRabbitBinding(RabbitMq rabbitMq,String queueName, boolean isInitDlxMap, boolean isAddDlxPrefix) {
    // 队列名
    this.queue = this.getName(queueName, isAddDlxPrefix);
    // 交换机名
    this.exchange = this.getName(rabbitMq.exchange(), isAddDlxPrefix);
    // 路由
    this.routingKey = this.getName(rabbitMq.routingKey(), isAddDlxPrefix);
    this.args       = this.initArgs(rabbitMq, isInitDlxMap);
  }

  public String getQueue() {
    return queue;
  }

  public void setQueue(String queue) {
    this.queue = queue;
  }

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  public String getRoutingKey() {
    return routingKey;
  }

  public void setRoutingKey(String routingKey) {
    this.routingKey = routingKey;
  }

  public Map<String, Object> getArgs() {
    return args;
  }

  public void setArgs(Map<String, Object> args) {
    this.args = args;
  }

  private String getName(String name, boolean isAddDlxPrefix) {
    if (ValidateUtils.isEmpty(name)) {
      return name;
    }
    return isAddDlxPrefix ? BaseRabbitMqCode.DLX_PREFIX + name : name;
  }

  /**
   * 绑定死信队列参数
   *
   * @param rabbitMq     MQ注解
   * @param isInitDlxMap 是否初始化死信队列参数
   * @return 死信队列参数
   */
  private Map<String, Object> initArgs(RabbitMq rabbitMq,boolean isInitDlxMap) {
    boolean dlx        = !void.class.equals(rabbitMq.dlxClazz()) || rabbitMq.delay() != 0L;

    if (!dlx || !isInitDlxMap) {
      return null;
    }
    Map<String, Object> args = new HashMap<>(3);
    args.put(BaseRabbitMqCode.DLX_EXCHANGE_KEY, BaseRabbitMqCode.DLX_PREFIX + rabbitMq.exchange());

    if (ValidateUtils.isNotEmpty(rabbitMq.routingKey())) {
      args.put(BaseRabbitMqCode.DLX_ROUTING_KEY, BaseRabbitMqCode.DLX_PREFIX + rabbitMq.routingKey());
    }

    if (rabbitMq.delay() != 0L) {
      args.put(BaseRabbitMqCode.DLX_TTL, rabbitMq.delay());
    }
    return args;
  }
}
