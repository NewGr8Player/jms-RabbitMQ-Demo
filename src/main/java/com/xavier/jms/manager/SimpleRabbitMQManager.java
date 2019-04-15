package com.xavier.jms.manager;

import com.xavier.jms.common.ExchangeClassTypeEnum;
import com.xavier.jms.common.ExcuteCommand;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * A Simple RabbitMQ manager
 *
 * @author NewGr8Player
 */
@Component
public class SimpleRabbitMQManager extends AbstractMQManager {

	@Autowired
	private RabbitAdmin rabbitAdmin;
	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * Construct a new queue, given a name, durability flag, and auto-delete flag, and arguments.
	 *
	 * @param queueName  the name of the queue - must not be null; set to "" to have the broker generate the name.
	 * @param durable    true if we are declaring a durable queue (the queue will survive a server restart)
	 * @param exclusive  true if we are declaring an exclusive queue (the queue will only be used by the declarer's connection)
	 * @param autoDelete true if the server should delete the queue when it is no longer in use
	 * @param arguments  the arguments used to declare the queue
	 * @return the queue name if successful, null if not successful and
	 * {@link org.springframework.amqp.rabbit.core.RabbitAdmin#setIgnoreDeclarationExceptions(boolean) ignoreDeclarationExceptions} is
	 * true.
	 */
	public String declareQueue(final String queueName, final boolean durable, final boolean exclusive, final boolean autoDelete, final Map<String, Object> arguments) {
		return rabbitAdmin.declareQueue(new Queue(queueName, durable, exclusive, autoDelete, arguments));
	}

	/**
	 * Delete a queue, without regard for whether it is in use or has messages on it
	 *
	 * @param queueName the name of the queue
	 * @return a deletion-confirm method to indicate the queue was successfully deleted
	 */
	public boolean deleteQueue(final String queueName) {
		return rabbitAdmin.deleteQueue(queueName);
	}

	/**
	 * Purge a queue and optionally don't wait for the purge to occur
	 *
	 * @param queueName the name of the queue
	 * @param noWait    true if don't wait for the purge to occur
	 */
	public void purgeQueue(final String queueName, final boolean noWait) {
		rabbitAdmin.purgeQueue(queueName, noWait);
	}

	/**
	 * Declare a new Exchange, given a name, durability flag, auto-delete flag.
	 *
	 * @param name              the name of the exchange.
	 * @param durable           true if we are declaring a durable exchange (the exchange will survive a server restart)
	 * @param autoDelete        true if the server should delete the exchange when it is no longer in use
	 * @param exchangeClassTypeEnum AbstractExchange class implements class type
	 * @return a declare-confirm method to indicate the exchange was successfully declared
	 */
	public boolean declareExchange(final String name, final boolean durable, final boolean autoDelete, Map<String, Object> arguments, final ExchangeClassTypeEnum exchangeClassTypeEnum) {
		Class cls = exchangeClassTypeEnum.getClazz();
		try {
			Constructor con = cls.getConstructor(String.class, boolean.class, boolean.class, Map.class);
			AbstractExchange exchange = (AbstractExchange) con.newInstance(name, durable, autoDelete, arguments);
			rabbitAdmin.declareExchange(exchange);
			return true;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			//TODO 反射失败，没有此方法
			return false;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			//TODO 反射失败，没有调用权限
			return false;
		} catch (InstantiationException e) {
			e.printStackTrace();
			//TODO 反射失败，实例化失败
			return false;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			//TODO 反射失败，调用目标失败
			return false;
		}

	}

	//TODO 新增declareExchange的子方法，缺省exchangeClassType，创建方法名对应的exchange

	/**
	 * Delete an exchange, without regard for whether it is in use or not
	 *
	 * @param exchangeName the name of the exchange
	 * @return a deletion-confirm method to indicate the exchange was successfully deleted
	 */
	public boolean deleteExchange(final String exchangeName) {
		return rabbitAdmin.deleteExchange(exchangeName);
	}

	/**
	 * Declare a binding
	 *
	 * @param destination     name of destination
	 * @param destinationType Binding.DestinationType enum
	 * @param exchange        name of exchange
	 * @param routingKey      key of routing
	 * @param arguments       the arguments used to declare the binding
	 */
	public void declareBinding(final String destination, final Binding.DestinationType destinationType, final String exchange, final String routingKey, final Map<String, Object> arguments) {
		rabbitAdmin.declareBinding(new Binding(destination, destinationType, exchange, routingKey, arguments));
	}

	/**
	 * Declare a binding
	 *
	 * @param binding binding object
	 */
	public void declareBinding(final Binding binding) {
		rabbitAdmin.declareBinding(binding);
	}

	/**
	 * Remove a binding from the broker (this operation is not available remotely)
	 *
	 * @param binding binding object
	 */
	public void removeBinding(final Binding binding) {
		rabbitAdmin.removeBinding(binding);
	}

	/**
	 * Send a message to a specific exchange with a specific routing key.
	 *
	 * @param exchange        the name of the exchange
	 * @param routingKey      the routing key
	 * @param message         a message to send
	 * @param correlationData data to correlate publisher confirms.
	 */
	public void sendMessage(final String exchange, final String routingKey, final Message message, final CorrelationData correlationData) {
		try {
			rabbitTemplate.send(exchange, routingKey, message, correlationData);
		} catch (AmqpException e) {
			e.printStackTrace();
			//TODO 消息发送异常
		}

	}

	/**
	 * Convert a Java object to an Amqp {@link Message} and send it to a specific exchange with a specific routing key.
	 *
	 * @param exchange             the name of the exchange
	 * @param routingKey           the routing key
	 * @param message              a message to send
	 * @param messagePostProcessor a processor to apply to the message before it is sent
	 * @param correlationData      data to correlate publisher confirms.
	 */
	public void convertAndSendMessage(final String exchange, final String routingKey, final Object message, @Nullable final MessagePostProcessor messagePostProcessor, final CorrelationData correlationData) {
		rabbitTemplate.convertAndSend(exchange, routingKey, message, messagePostProcessor, correlationData);
	}

	/**
	 * Convert a Java object to an Amqp {@link Message} and send it to a specific exchange with a specific routing key.
	 *
	 * @param exchange        the name of the exchange
	 * @param routingKey      the routing key
	 * @param message         a message to send
	 * @param correlationData data to correlate publisher confirms.
	 */
	public void convertAndSendMessage(String exchange, String routingKey, final Object message, @Nullable final CorrelationData correlationData) {
		rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
	}

	/**
	 * Receive message from speicifc queue
	 *
	 * @param queueName     the name of the queue
	 * @param timeoutMillis timeout,unit ms
	 * @return {@link Message}
	 */
	public Message receiveMessage(final String queueName, final long timeoutMillis) {
		if (timeoutMillis < 0L) {
			throw new IllegalArgumentException("Parameter timeoutMillis must be greater than 0.");
		}
		return rabbitTemplate.receive(queueName, timeoutMillis);
	}

	/**
	 * Receive message from speicifc queue and convert it to java object
	 *
	 * @param queueName     the name of the queue
	 * @param timeoutMillis timeout,unit ms
	 * @return java object,use TYPE-CAST to get object
	 */
	public Object receiveAndConvertMessage(final String queueName, final long timeoutMillis) {
		if (timeoutMillis < 0L) {
			throw new IllegalArgumentException("Parameter timeoutMillis must be greater than 0.");
		}
		return rabbitTemplate.receiveAndConvert(queueName, timeoutMillis);
	}

	@Override
	public void startup() {
		super.startup();
	}

	@Override
	public void shutdown() {
		super.shutdown();
	}

	@Override
	public boolean startStatus() {
		//TODO 判断一下启动状态
		ExcuteCommand.excute(statusCommand);
		return true;
	}
}
