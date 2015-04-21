package com.ninja;



import java.lang.management.ManagementFactory;

import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Slf4jRequestLog;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.log.Log;

public class ServerDemo
{
	public static void main(String[] args)
	{
		Server server = new Server(8081);
		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(HelloServlet.class, "/*");
		
		RequestLogHandler requestLogHandler = new RequestLogHandler();
		
		HandlerCollection handlers = new HandlerCollection();
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		handlers.setHandlers(new Handler[]{contexts,handler,requestLogHandler});
		RequestLog request_log = new Slf4jRequestLog();
		
		requestLogHandler.setRequestLog(request_log);
		server.setHandler(handlers);
		
		// Setup JMX
		MBeanContainer mbContainer=new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
		server.addEventListener(mbContainer);
		server.addBean(mbContainer);
		
		//添加jmx
		server.addBean(new Derived());
		 
		// Add loggers MBean to server (will be picked up by MBeanContainer above)
		server.addBean(Log.getLog());
		
		try
		{
			server.start();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connector[] connectors = server.getConnectors();
		for (Connector c : connectors) 
			System.out.println(c.getClass());
	}
}
