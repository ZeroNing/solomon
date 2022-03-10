package com.steven.solomon.model.rabbitMQ;


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

  public InitRabbitBinding(RabbitMq rabbitMq, boolean isInitDlxMap, boolean isAddDlxPrefix) {
    // 队列名
    queue = this.getName(rabbitMq.queues(), isAddDlxPrefix);
    // 交换机名
    exchange = this.getName(rabbitMq.exchange(), isAddDlxPrefix);
    // 路由
    routingKey = this.getName(rabbitMq.routingKey(), isAddDlxPrefix);
    args       = this.initArgs(rabbitMq, isInitDlxMap);
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
    String DLX_PREFIX = "dlx_";
    if (ValidateUtils.isEmpty(name)) {
      return name;
    }
    return isAddDlxPrefix ? DLX_PREFIX + name : name;
  }

  /**
   * 绑定死信队列参数
   *
   * @param rabbitMq     MQ注解
   * @param isInitDlxMap 是否初始化死信队列参数
   * @return 死信队列参数
   */
  private Map<String, Object> initArgs(RabbitMq rabbitMq, boolean isInitDlxMap) {
    String  DLX_PREFIX = "dlx_";
    boolean dlx        = rabbitMq.isDlx() || rabbitMq.isTtl();

    if (!dlx || !isInitDlxMap) {
      return null;
    }
    Map<String, Object> args = new HashMap<>(3);
    args.put(BaseRabbitMqCode.DLX_EXCHANGE_KEY, DLX_PREFIX + rabbitMq.exchange());

    if (ValidateUtils.isNotEmpty(rabbitMq.routingKey())) {
      args.put(BaseRabbitMqCode.DLX_ROUTING_KEY, DLX_PREFIX + rabbitMq.routingKey());
    }

    if (rabbitMq.isTtl()) {
      args.put(BaseRabbitMqCode.DLX_TTL, rabbitMq.delay());
    }
    return args;
  }
}
