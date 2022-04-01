package com.steven.solomon.rabbitMQ.init;

import com.steven.solomon.base.excetion.AbstractExceptionHandler;
import com.steven.solomon.rabbitMQ.config.RabbitMQListenerConfig;
import com.steven.solomon.utils.spring.SpringUtil;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Order(2)
public class RabbitMQInitConfig implements CommandLineRunner {

    @Autowired
    private RabbitAdmin admin;

    @Autowired
    private CachingConnectionFactory rabbitConnectionFactory;

    /**
     * 所有的队列监听容器MAP
     */
    public final static Map<String, AbstractMessageListenerContainer> allQueueContainerMap = new ConcurrentHashMap<>();

    @Override
    public void run(String... args) throws Exception {
        /**
         * 初始化mq
         */
        new RabbitMQListenerConfig().init(admin, rabbitConnectionFactory);
        /**
         * 将自定义异常处理器初始化完成
         */
        AbstractExceptionHandler.exceptionHandlerMap = SpringUtil.getBeansOfType(AbstractExceptionHandler.class);
    }
}
