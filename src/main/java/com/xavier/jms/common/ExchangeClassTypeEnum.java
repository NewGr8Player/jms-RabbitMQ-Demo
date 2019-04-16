package com.xavier.jms.common;

import lombok.Getter;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.TopicExchange;

/**
 * AbstractExchange class implements class type
 *
 * @author NewGr8Player
 */
@Getter
public enum ExchangeClassTypeEnum {
	/**
	 * DirectExchange Class Type.
	 */
	DirectExchangeType(DirectExchange.class)
	/**
	 * TopicExchange Class Type.
	 */
	, TopicExchangeType(TopicExchange.class)
	/**
	 * FanoutExchange Class Type.
	 */
	, FanoutExchangeType(FanoutExchange.class)
	/**
	 * HeadersExchange Class Type.
	 *
	 * @deprecated Its structure is similar to DirectExchange,but low efficency.
	 */
	, @Deprecated HeadersExchangeType(HeadersExchange.class);

	ExchangeClassTypeEnum(Class clazz) {
		this.clazz = clazz;
	}

	private Class clazz;
}
