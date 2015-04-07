/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ninja.configuration;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */
public class Configurations
{

	/**
	 * @param args
	 * @throws ConfigurationException 
	 */
	public static void main(String[] args) throws ConfigurationException
	{
		ConfigurationBuilder builder = null;
		try
		{
			builder = new ConfigurationBuilder("config.xml");
		}
		catch (ConfigurationException e)
		{
			e.printStackTrace();
		}
		Configuration config = builder.getConfiguration();
		System.out.println(config.getString("LOCALE"));
		System.out.println(config.getString("colors.background"));
		System.out.println(config.getString("application.title"));
		System.out.println(config.getString("java.home"));
		System.out.println(config.getString("data"));
		System.out.println(config.getString("sign"));
		System.out.println(config.getString("service.env"));
		System.out.println(config.getString("mock.otp"));
	}

}
