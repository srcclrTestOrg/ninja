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
		String[] strs = new String[]{"1","2"};
		System.out.println(java.lang.String.class.getClassLoader());
		System.out.println(System.getProperty("java.class.path"));
		System.out.println(Thread.currentThread().getContextClassLoader());
		/**Array  classloader is associated with the element of the array**/
		System.out.println(strs.getClass().getClassLoader());
	}

}
