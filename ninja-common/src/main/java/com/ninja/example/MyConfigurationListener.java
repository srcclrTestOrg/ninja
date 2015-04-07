/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ninja.example;

import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */
public class MyConfigurationListener implements ConfigurationListener
{

	public void configurationChanged(ConfigurationEvent event)
	{
		 if (!event.isBeforeUpdate())
	        {
	            // only display events after the modification was done
	            System.out.println("Received event!");
	            System.out.println("Type = " + event.getType());
	            if (event.getPropertyName() != null)
	            {
	                System.out.println("Property name = " + event.getPropertyName());
	            }
	            if (event.getPropertyValue() != null)
	            {
	                System.out.println("Property value = " + event.getPropertyValue());
	            }
	            
	            System.out.println("Received event end!");
	        }
		
	}

}
