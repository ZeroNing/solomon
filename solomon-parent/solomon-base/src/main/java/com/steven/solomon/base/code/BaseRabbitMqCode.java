package com.steven.solomon.base.code;

public interface BaseRabbitMqCode {

  String DLX_ROUTING_KEY = "x-dead-letter-routing-key";

  String DLX_EXCHANGE_KEY = "x-dead-letter-exchange";

  String DLX_TTL = "x-message-ttl";
}
