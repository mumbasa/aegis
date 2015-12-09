package com.component;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class CusNotification {

	public static Notification getSuccess(String caption){
		
		Notification s = new Notification(caption, Type.WARNING_MESSAGE);
		s.setHtmlContentAllowed(true);
		s.setDelayMsec(-1);
		s.setStyleName("success");
		s.show(Page.getCurrent());
		return s;
		
	}
}
