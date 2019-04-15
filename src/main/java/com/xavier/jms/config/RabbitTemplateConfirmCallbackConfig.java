package com.xavier.jms.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * A callback for publisher confirmations.
 *
 * @author NewGr8Player
 */
@Slf4j
@Component
public class RabbitTemplateConfirmCallbackConfig implements RabbitTemplate.ConfirmCallback {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@PostConstruct
	public void init() {
		rabbitTemplate.setConfirmCallback(this);
	}

	/**
	 * Confirmation callback.
	 *
	 * @param correlationData correlation data for the callback.
	 * @param ack             true for ack, false for nack
	 * @param cause           An optional cause, for nack, when available, otherwise null.
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		if (ack) {
			log.info("CorrelationData:{},Ack.", correlationData);
		} else {
			log.error("CorrelationData:{},Nack:{}.", correlationData, cause);
		}

	}
}
