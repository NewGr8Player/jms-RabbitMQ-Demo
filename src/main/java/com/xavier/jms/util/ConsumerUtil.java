package com.xavier.jms.util;

import com.xavier.jms.manager.SimpleRabbitMQManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class ConsumerUtil {
	@Autowired
	private SimpleRabbitMQManager simpleRabbitMQManager;

	private static SimpleRabbitMQManager manager;

	@PostConstruct
	public void init() {
		manager = this.simpleRabbitMQManager;
	}

	//TODO 消费者通用方法
}
