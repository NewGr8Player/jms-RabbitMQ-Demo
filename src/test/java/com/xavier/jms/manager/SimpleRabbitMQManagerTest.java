package com.xavier.jms.manager;

import com.xavier.jms.JmsApplicationTests;
import com.xavier.jms.common.ExchangeClassTypeEnum;
import org.junit.Test;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class SimpleRabbitMQManagerTest extends JmsApplicationTests {

	@Autowired
	private SimpleRabbitMQManager simpleRabbitMQManager;

	@Test
	public void declareQueue() {
		final String queueName = "test-queue";
		final boolean durable = false;
		final boolean exclusive = false;
		final boolean autoDelete = false;
		final Map<String, Object> arguments = new HashMap<>();
		simpleRabbitMQManager.declareQueue(queueName, durable, exclusive, autoDelete, arguments);
	}

	@Test
	public void deleteQueue() {
	}

	@Test
	public void purgeQueue() {
	}

	@Test
	public void declareExchange() {
		final String topicName = "test-topic";
		simpleRabbitMQManager.declareExchange(
				topicName
				, true
				, false
				, null
				, ExchangeClassTypeEnum.TopicExchangeType
		);
	}

	@Test
	public void deleteExchange() {
	}

	@Test
	public void declareBinding() {
		final String queueName = "test-queue";
		final String topicName = "test-topic";
		simpleRabbitMQManager.declareBinding(
				BindingBuilder
						.bind(new Queue(queueName))
						.to(new TopicExchange(topicName))
						.with("key.#")
		);
	}

	@Test
	public void declareBinding1() {
	}

	@Test
	public void removeBinding() {
	}

	@Test
	public void sendMessage() {
	}

	@Test
	public void convertAndSendMessage() {
		simpleRabbitMQManager.convertAndSendMessage(
				"test.topic"
				, "key.#"
				, "测试中文"
				, null
		);
	}

	@Test
	public void convertAndSendMessage1() {
	}

	@Test
	public void receiveMessage() {
	}

	@Test
	public void receiveAndConvertMessage() {
		final String queueName = "test-queue";
		System.out.println(
				(String) simpleRabbitMQManager.receiveAndConvertMessage(
						queueName
						, 1000L
				)
		);
	}

	@Test
	public void convertAndSendThenreceiveAndConvertMessage(){
		final String queueName = "test-queue";
		final String topicName = "test-topic";

		simpleRabbitMQManager.convertAndSendMessage(
				topicName
				, "key.#"
				, "测试中文"
				, null
		);

		System.out.println(
				(String) simpleRabbitMQManager.receiveAndConvertMessage(
						queueName
						, 1000L
				)
		);
	}

	@Test
	public void commandTest(){
		System.out.println(simpleRabbitMQManager.getStartupCommand());
		System.out.println(simpleRabbitMQManager.getShutdownCommand());
		System.out.println(simpleRabbitMQManager.getStatusCommand());
	}

	@Test
	public void startup() {
		simpleRabbitMQManager.startup();
	}

	@Test
	public void shutdown() {
		simpleRabbitMQManager.shutdown();
	}

	@Test
	public void startStatus() {
		System.out.println(simpleRabbitMQManager.startStatus());
	}

}