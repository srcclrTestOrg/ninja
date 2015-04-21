/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ninja.jmx.java;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */
public interface HelloMBean
{
	public void setName(String Name);
	public String getName();
	public void printHello();
	public void printHello(String Name);
}
