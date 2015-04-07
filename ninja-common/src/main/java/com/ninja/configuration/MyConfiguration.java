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
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.configuration.tree.ConfigurationNode;

import com.ninja.example.MyConfigurationListener;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */
public class MyConfiguration extends HierarchicalConfiguration
{
	
	public MyConfiguration(String fileName) {
		MyFileConfiguration myFileConfiguration = new MyFileConfiguration();
		myFileConfiguration.setFileName(fileName);
		myFileConfiguration.addConfigurationListener(new MyConfigurationListener());
		myFileConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
		myFileConfiguration.addProperty("keysdfs", "valuesdfsdfdsf");
		try
		{
			myFileConfiguration.load();
		}
		catch (ConfigurationException e)
		{
			e.printStackTrace();
		}
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
		ConfigurationNode root = getRootNode();
		while(keys.hasMoreElements()) {
			String key = (String)keys.nextElement();
			String val = props.getProperty(key);
			ConfigurationNode node = createNode(key);
			node.setValue(val);
			root.addChild(node);
		}
	}
	
	public static void main(String[] args)
	{
		MyConfiguration myConfiguration = new MyConfiguration("application.properties");
		String s  = myConfiguration.getString("LOCALE");
		System.out.println(s);
	}
	
	
	class MyFileConfiguration extends AbstractFileConfiguration {

		public void load(Reader in) throws ConfigurationException
		{
			MyConfiguration.this.load(in);
		}

		public void save(Writer out) throws ConfigurationException
		{
			
		}
		
	}
}
