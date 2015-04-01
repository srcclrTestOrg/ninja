/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ninja.example;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.interpol.ExprLookup;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */
public class ExprDemo
{
	private static final String TEST_FILE = "TEST_FILE";
	public static void main(String[] args) throws ConfigurationException
	{
		ExprLookup.Variables vars = new ExprLookup.Variables();
	     vars.add(new ExprLookup.Variable("String", org.apache.commons.lang.StringUtils.class));
	     //vars.add(new ExprLookup.Variable("Util", new Utility("Hello")));
	     vars.add(new ExprLookup.Variable("System", "Class:java.lang.System"));
	     XMLConfiguration config = new XMLConfiguration(TEST_FILE);
	     //config.setLogger(log);
	     ExprLookup lookup = new ExprLookup(vars);
	     lookup.setConfiguration(config);
	     String str = lookup.lookup("'$[element] ' + String.trimToEmpty('$[space.description]')");
	     System.out.println(str);
	}

}
