/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ninja.jmx.java;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import com.sun.jdmk.comm.HtmlAdaptorServer;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */
public class HelloAgent
{

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		MBeanServer server = MBeanServerFactory.createMBeanServer();
		ObjectName helloName = new ObjectName("hello:name=hello");
		Hello hello = new Hello();
		server.registerMBean(hello, helloName);
		
		ObjectName agentName = new ObjectName("HelloAgent:name=helloagent,port=8082");
		HtmlAdaptorServer adapter = new HtmlAdaptorServer();
		server.registerMBean(adapter, agentName);
		
		ObjectName jackName = new ObjectName("jack:name=jack");
		Jack jack = new Jack();
		server.registerMBean(jack, jackName);
		jack.addNotificationListener(new HelloListener(), null, hello);
		
		adapter.start();
		System.out.println("Agent server start...");
	}

}
