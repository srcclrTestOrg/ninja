package com.ninja.jmx.java;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class Jack extends NotificationBroadcasterSupport implements JackMBean
{
	private int seq = 0;
	
	public void sayHi()
	{
		Notification notification = new Notification("jack.hi",this, ++seq, System.currentTimeMillis(),  "JACK");
		sendNotification(notification);
	}

}
