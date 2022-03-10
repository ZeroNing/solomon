package com.steven.solomon.servic;

import com.rabbitmq.client.Channel;
import com.steven.solomon.base.code.BaseCode;
import com.steven.solomon.utils.logger.LoggerUtils;
import org.slf4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter;

/**
 * 死信队列消费器
 */
public abstract class AbstractDlxConsumer extends MessagingMessageListenerAdapter {

	private Logger logger = LoggerUtils.logger(getClass());

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		MessageProperties messageProperties = message.getMessageProperties();
		long              deliveryTag       = messageProperties.getDeliveryTag();
		String            json              = null;
		try {
			// 消费者内容
			json = new String(message.getBody(), BaseCode.UTF8);
			logger.info("AbstractConsumer:死信队列消费者消息: {}", json);
			// 消费者消费消息
			this.handleMessage(json);
		} catch (Exception e) {
			// 消费失败次数不等于空并且失败次数大于某个次数，不处理直接return，并记录到数据库
			logger.info("AbstractConsumer:死信队列消费报错 异常为:{}", e.getMessage());
			//将消费失败的记录保存到数据库或者不处理也可以
			this.saveFailMessage(message, e);
		} finally {
			// 手动确认消息
			channel.basicAck(deliveryTag,false);
		}
	}

	/**
	 * 消费方法
	 *
	 * @param json
	 */
	public abstract void handleMessage(String json);

	/**
	 * 保存消费失败的消息
	 *
	 * @param message
	 * @param e
	 */
	public abstract void saveFailMessage(Message message, Exception e);

}
