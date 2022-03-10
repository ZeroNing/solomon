package com.steven.solomon.utils.rabbit;

import com.steven.solomon.base.code.error.BaseExceptionCode;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.base.model.BaseMq;
import com.steven.solomon.config.init.RabbitMQInitConfig;
import com.steven.solomon.model.rabbitMQ.MessageQueueDatail;
import com.steven.solomon.model.rabbitMQ.RabbitMqModel;
import com.steven.solomon.utils.logger.LoggerUtils;
import com.steven.solomon.utils.verification.ValidateUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class RabbitMqService implements MqService {

  private Logger logger = LoggerUtils.logger(RabbitMqService.class);

  @Autowired
  private RabbitTemplate rabbitTemplate;

  /**
   * queue2ContainerAllMap初始化标识
   */
//  private volatile boolean hasInit = false;



  public Collection<AbstractMessageListenerContainer> getAllQueueContainerList(){
    return RabbitMQInitConfig.allQueueContainerMap.values();
  }

  private boolean convertAndSend(BaseMq baseMq) {
    RabbitMqModel rabbitMQModel = (RabbitMqModel) baseMq;
    if (ValidateUtils.isEmpty(rabbitMQModel) || ValidateUtils.isEmpty(rabbitMQModel.getExchange())) {
      return false;
    }
    rabbitTemplate.convertAndSend(rabbitMQModel.getExchange(), rabbitMQModel.getRoutingKey(), rabbitMQModel,new CorrelationData(
        UUID.randomUUID().toString()));
    return true;
  }

  public List<MessageQueueDatail> statAllMessageQueueDetail() {
    List<MessageQueueDatail> queueDetailList = new ArrayList<>();
    RabbitMQInitConfig.allQueueContainerMap.entrySet().forEach(entry -> {
      String                           queueName = entry.getKey();
      AbstractMessageListenerContainer container = entry.getValue();
      MessageQueueDatail               deatil    = new MessageQueueDatail(queueName, container);
      queueDetailList.add(deatil);
    });

    return queueDetailList;
  }

  public AbstractMessageListenerContainer findContainerByQueueName(String queueName) {
    String                         key       = StringUtils.trim(queueName);
    AbstractMessageListenerContainer container = RabbitMQInitConfig.allQueueContainerMap.get(key);
    return container;
  }

  @Override
  public void send(BaseMq mq) throws BaseException {
    if (!convertAndSend(mq)) {
      throw new BaseException(BaseExceptionCode.BASE_EXCEPTION_CODE);
    }
  }

  @Override
  public boolean resetQueueConcurrentConsumers(String queueName, int concurrentConsumers) {
    Assert.state(concurrentConsumers > 0, "参数 'concurrentConsumers' 必须大于0.");
    DirectMessageListenerContainer container = (DirectMessageListenerContainer)findContainerByQueueName(queueName);
    if (container.isActive() && container.isRunning()) {
      container.setConsumersPerQueue(concurrentConsumers);
      return true;
    }
    return false;
  }

  @Override
  public boolean restartMessageListener(String queueName) {
    if(ValidateUtils.isEmpty(queueName)){
      logger.info("restartMessageListener 重启队列失败,传入队列名为空!");
      return false;
    }
    DirectMessageListenerContainer container = (DirectMessageListenerContainer)findContainerByQueueName(queueName);
    Assert.state(!container.isRunning(), String.format("消息队列%s对应的监听容器正在运行！", queueName));
    container.start();
    return true;
  }
  @Override
  public boolean stopMessageListener(String queueName) {
    if(ValidateUtils.isEmpty(queueName)){
      logger.info("restartMessageListener 停止队列失败,传入队列名为空!");
      return false;
    }
    DirectMessageListenerContainer container = (DirectMessageListenerContainer)findContainerByQueueName(queueName);
    Assert.state(container.isRunning(), String.format("消息队列%s对应的监听容器未运行！", queueName));
    container.stop();
    return true;
  }

//  private Map<String, AbstractMessageListenerContainer> getQueue2ContainerAllMap() {
//    if (!hasInit) {
//      synchronized (allQueueContainerMap) {
//        if (!hasInit) {
//          Collection<MessageListenerContainer> listenerContainers = SpringUtil.getBean(RabbitListenerEndpointRegistry.class).getListenerContainers();
//          listenerContainers.forEach(container -> {
//                    	DirectMessageListenerContainer simpleContainer = (DirectMessageListenerContainer) container;
//                        Arrays.stream(simpleContainer.getQueueNames()).forEach(queueName ->
//                        allQueueContainerMap.putIfAbsent(StringUtils.trim(queueName), simpleContainer));
//                    });
//          hasInit = true;
//        }
//      }
//    }
//    return allQueueContainerMap;
//  }

}
