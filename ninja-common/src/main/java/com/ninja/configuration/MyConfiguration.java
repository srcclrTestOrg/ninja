/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ninja.configuration;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */
public class MyConfiguration extends HierarchicalConfiguration
{
	public static void main(String[] args)
	{
		MyConfiguration myConfiguration = new MyConfiguration();
		String s  = myConfiguration.getString("LOCALE");
		System.out.println(s);
	}
	
	
	class MyFileConfiguration extends AbstractFileConfiguration {

		public void load(Reader in) throws ConfigurationException
		{
			Properties props = new Properties();
			try
			{
				props.load(in);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void save(Writer out) throws ConfigurationException
		{
			
		}
		
	}
}
