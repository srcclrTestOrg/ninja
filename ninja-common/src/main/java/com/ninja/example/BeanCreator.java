/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ninja.example;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.beanutils.BeanDeclaration;
import org.apache.commons.configuration.beanutils.BeanHelper;
import org.apache.commons.configuration.beanutils.XMLBeanDeclaration;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */
public class BeanCreator
{
	public static void main(String[] args) throws ConfigurationException
	{
		XMLConfiguration config = new XMLConfiguration("windowconfig.xml");
		BeanDeclaration decl = new XMLBeanDeclaration(config, "gui.windowManager");
		DefaultWindowManager wm = (DefaultWindowManager) BeanHelper.createBean(decl);
		System.out.println(wm.getDefaultHeight());
	}
}
