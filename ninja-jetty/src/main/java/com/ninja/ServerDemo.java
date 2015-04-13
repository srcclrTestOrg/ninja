package com.ninja;



import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class ServerDemo
{
	public static void main(String[] args)
	{
		Server server = new Server(8080);
		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(HelloServlet.class, "/*");
		server.setHandler(handler);
		
		try
		{
			server.start();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
