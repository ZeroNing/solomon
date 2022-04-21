package com.steven.solomon.init;

import com.steven.solomon.config.RabbitMQListenerConfig;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
    }
}
