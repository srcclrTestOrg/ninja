/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ninja;

import com.google.common.base.Optional;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */
public class OptionalDemo
{
	
	public static void main(String[] args)
	{
		  Optional<Integer> possible = Optional.of(5);//创建一个整数Optional对象
		   System.out.println(possible.isPresent());// returns true
		   System.out.println(possible.get()); // returns 5
		   
			  Optional<Integer> possibleNull = Optional.of(null);//创建一个整数Optional对象
			   System.out.println(possibleNull.isPresent());// returns true
			   System.out.println(possibleNull.get()); // returns 5
	}

}
