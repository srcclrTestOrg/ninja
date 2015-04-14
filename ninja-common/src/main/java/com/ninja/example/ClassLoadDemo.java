/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ninja.example;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */
public class ClassLoadDemo
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println(java.lang.String.class.getClassLoader());
	}

}
