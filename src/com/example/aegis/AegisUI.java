package com.example.aegis;

import javax.servlet.annotation.WebServlet;

import com.component.MainDiv;
import com.data.Staff;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.views.Login;
//@PreserveOnRefresh
@SuppressWarnings("serial")
@Theme("aegis")

public class AegisUI extends UI {
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = AegisUI.class, widgetset = "com.example.aegis.widgetset.AegisWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		
Staff h = (Staff) VaadinSession.getCurrent().getAttribute("staff");
		if (h == null) {
			setContent(new Login(this));

		} else if (h != null) {

			setContent(new MainDiv());

		}

	

	}

}