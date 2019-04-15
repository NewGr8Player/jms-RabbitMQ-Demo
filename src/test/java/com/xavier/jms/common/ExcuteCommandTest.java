package com.xavier.jms.common;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExcuteCommandTest {

	@Test
	public void excute() {
		ExcuteCommand
				.excute("D:/Develop/RabbitMQ/rabbitmq_server-3.7.14/sbin/rabbitmqctl.bat status")
				.forEach(System.out::println);
	}
}