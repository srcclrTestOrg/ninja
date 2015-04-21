package com.ninja.jmx.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ninja.jmx.java.Hello;

public class JmxTestClient
{

	public static void getMBeanServer()
	{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		Hello service = context.getBean(Hello.class);

		String name = service.getName();
		System.out.println("Name = " + name);

	}

	public static void main(String[] args)
	{
		JmxTestClient.getMBeanServer();
	}
}