package com.ninja.jmx.java;

import javax.management.Notification;
import javax.management.NotificationListener;

public class HelloListener implements NotificationListener 
{

	public void handleNotification(Notification notification, Object handback)
	{
		System.out.println("source is :"+notification.getSource());
		System.out.println("timestamp is:"+notification.getTimeStamp());
		if (handback instanceof Hello) {
			((Hello) handback).printHello(notification.getMessage());
		}
	}

}
