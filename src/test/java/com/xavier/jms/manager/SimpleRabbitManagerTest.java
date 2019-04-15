package com.xavier.jms.manager;

import com.xavier.jms.JmsApplicationTests;
import com.xavier.jms.common.ExchangeClassTypeEnum;
import org.junit.Test;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class SimpleRabbitManagerTest extends JmsApplicationTests {

	@Autowired
	private SimpleRabbitManager simpleRabbitManager;

	@Test
	public void declareQueue() {
		final String queueName = "test-queue";
		final boolean durable = false;
		final boolean exclusive = false;
		final boolean autoDelete = false;
		final Map<String, Object> arguments = new HashMap<>();
		simpleRabbitManager.declareQueue(queueName, durable, exclusive, autoDelete, arguments);
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
		simpleRabbitManager.declareExchange(
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
		simpleRabbitManager.declareBinding(
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
		simpleRabbitManager.convertAndSendMessage(
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
				(String) simpleRabbitManager.receiveAndConvertMessage(
						queueName
						, 1000L
				)
		);
	}

	@Test
	public void convertAndSendThenreceiveAndConvertMessage(){
		final String queueName = "test-queue";
		final String topicName = "test-topic";

		simpleRabbitManager.convertAndSendMessage(
				topicName
				, "key.#"
				, "测试中文"
				, null
		);

		System.out.println(
				(String) simpleRabbitManager.receiveAndConvertMessage(
						queueName
						, 1000L
				)
		);
	}

	@Test
	public void commandTest(){
		System.out.println(simpleRabbitManager.getStartupCommand());
		System.out.println(simpleRabbitManager.getShutdownCommand());
		System.out.println(simpleRabbitManager.getStatusCommand());
	}

	@Test
	public void startup() {
		simpleRabbitManager.startup();
	}

	@Test
	public void shutdown() {
		simpleRabbitManager.shutdown();
	}

	@Test
	public void startStatus() {
		System.out.println(simpleRabbitManager.startStatus());
	}

}