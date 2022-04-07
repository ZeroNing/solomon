package com.steven.solomon.mq;

import com.steven.solomon.rabbitMQ.service.AbstractConsumer;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

@Component
public class TestDix extends AbstractConsumer<String> {
    @Override
    public void handleMessage(String body) {

    }

    @Override
    public void saveFailMessage(Message message, Exception e) {

    }
}
