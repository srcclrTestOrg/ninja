/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ninja.jmx.java;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */
public class Hello implements HelloMBean
{
	 private String name;
	 
	 public String getName() {
	  return name;
	 }
	 public void setName(String name) {
	  this.name = name;
	 }
	 
	 public void printHello() {
		 System.out.println("Hello" + name);
	 }
	 
	 public void printHello(String name) {
		 System.out.println("Hello" + name);
	 }

}
