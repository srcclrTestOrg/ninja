/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ninja.configuration;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.tree.ConfigurationNode;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */
public class MyConfiguration2 extends AbstractFileConfiguration
{
	public MyConfiguration2(String fileName) {
		this.setFileName(fileName);
		try
		{
			this.load();
		}
		catch (ConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		MyConfiguration2 MyConfiguration2 = new MyConfiguration2("application.properties");
		System.out.println(MyConfiguration2.getString("LOCALE"));
	}

	public void load(Reader in) throws ConfigurationException
	{
		Properties props = new Properties();
		try
		{
			props.load(in);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		Enumeration<Object>  keys = props.keys();
		while(keys.hasMoreElements()) {
			String key = (String)keys.nextElement();
			String val = props.getProperty(key);
			this.addPropertyDirect(key, val);
		}
	}
	
	

	public void save(Writer out) throws ConfigurationException
	{
		// TODO Auto-generated method stub
		
	}

}
