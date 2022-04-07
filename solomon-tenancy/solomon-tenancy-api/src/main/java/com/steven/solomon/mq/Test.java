package com.steven.solomon.mq;

import com.steven.solomon.annotation.RabbitMq;
import com.steven.solomon.rabbitMQ.service.AbstractConsumer;
import org.springframework.amqp.core.Message;

@RabbitMq(queues = {"test111","test2222"},exchange = "test",routingKey = "test",dlxClazz = TestDix.class)
public class Test extends AbstractConsumer<String> {
    @Override
    public void handleMessage(String body) {

    }

    @Override
    public void saveFailMessage(Message message, Exception e) {

    }
}
