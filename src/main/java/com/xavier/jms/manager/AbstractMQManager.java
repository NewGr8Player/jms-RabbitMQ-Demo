package com.xavier.jms.manager;

import com.xavier.jms.common.ExcuteCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * AbstractMQManager
 *
 * @author NewGr8Player
 */
@Slf4j
@Getter
@Setter
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
@ConfigurationProperties(prefix = "spring.rabbitmq.manager")
public abstract class AbstractMQManager {

	/**
	 * The start up command.
	 */
	protected String startupCommand = "";

	/**
	 * The shutdown command.
	 */
	protected String shutdownCommand = "";

	/**
	 * The command whitch to get current status.
	 */
	protected String statusCommand = "";

	// TODO Abstract MQ method.

	/**
	 * MQ startup.
	 */
	public void startup() {
		if (StringUtils.isNotBlank(startupCommand) && !startStatus()) {
			log.info("Excute command:{}.", startupCommand);
			ExcuteCommand.excute(startupCommand);
		} else {
			log.error("Command error:StartupCommand is blank.");
			throw new IllegalArgumentException("Param 'startupCommand' init false.");
		}
	}

	/**
	 * MQ shutdown.
	 */
	public void shutdown() {
		if (StringUtils.isNotBlank(shutdownCommand) && startStatus()) {
			log.info("Excute command:{}.", shutdownCommand);
			ExcuteCommand.excute(shutdownCommand);
		} else {
			log.error("Command error:ShutdownCommand is blank.");
			throw new IllegalArgumentException("Param 'startupCommand' init false.");
		}
	}

	/**
	 * If mq is running.
	 *
	 * @return true if MQ is running,otherwise fasle
	 */
	public abstract boolean startStatus();
}
