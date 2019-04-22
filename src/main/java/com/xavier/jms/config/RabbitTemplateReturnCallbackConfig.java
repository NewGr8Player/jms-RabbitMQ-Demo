package com.xavier.jms.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * A callback for returned messages.
 *
 * @author NewGr8Player
 */
@Slf4j
@Component
public class RabbitTemplateReturnCallbackConfig implements RabbitTemplate.ReturnCallback {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@PostConstruct
	public void init() {
		rabbitTemplate.setReturnCallback(this);
	}

	/**
	 * Returned message callback.
	 *
	 * @param message    the returned message
	 * @param replyCode  the reply code
	 * @param replyText  the reply text
	 * @param exchange   the exchange
	 * @param routingKey the routing key
	 */
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		log.info("Exchange:{},RoutingKey:{},MessageBody:{},ReplyCode{},ReplyText：", exchange, routingKey, message, replyCode, replyText);
	}
}
