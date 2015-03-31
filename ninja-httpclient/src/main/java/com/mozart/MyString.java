/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mozart;

import java.nio.charset.Charset;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */
public class MyString
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		String s = "ABCD";
		System.out.println(s.toCharArray());
		
		char ctr = 'A';
		System.out.println((byte)ctr);
		
		String hanzi = "汉";
		byte[] hanzibytes = hanzi.getBytes();
		for (byte by : hanzibytes) {
			System.out.print(by);
		}
		System.out.println();
		System.out.println(Charset.isSupported("utf-8"));
	}

}
