package com.views;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import com.component.MainDiv;
import com.data.Staff;
import com.utility.DBUtility;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class Login extends VerticalLayout implements View, ClickListener {

	private static final long serialVersionUID = 1L;
	private TextField username = new TextField("Username");

	private PasswordField pass = new PasswordField("Password");

	public Login(UI ui) {
		// TODO Auto-generated constructor stub
		super();
	
		CssLayout wel = new CssLayout();
		wel.setStyleName("wellogin");
		Label te = new Label("Welcome AEGIS FrontDesk");
	
		wel.addComponent(te);
		NativeButton enter = new NativeButton("Login");
		enter.setStyleName("loginbut");
		HorizontalLayout logform = new HorizontalLayout();
		username.setIcon(FontAwesome.USER);
		logform.setSpacing(true);
		VerticalLayout holder = new VerticalLayout();
		holder.addComponents(wel, logform);
		this.setSizeFull();

		logform.setStyleName("logform");
		holder.setComponentAlignment(logform, Alignment.MIDDLE_CENTER);
		holder.setComponentAlignment(wel, Alignment.MIDDLE_CENTER);
		pass.setIcon(FontAwesome.LOCK);
		logform.addComponents(username, pass, enter);
		logform.setComponentAlignment(enter, Alignment.BOTTOM_CENTER);
		Panel pane = new Panel(holder);

		holder.setSpacing(true);
		pane.setStyleName("loginpanel");
		this.addComponent(pane);
		this.setComponentAlignment(pane, Alignment.MIDDLE_CENTER);
		this.setStyleName("login");
		enter.addClickListener(this);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		UI.getCurrent().getPage().setTitle("Login Page");

	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		try {
			if (DBUtility
					.authenticateUser(username.getValue(), pass.getValue())) {
				Staff staff = DBUtility.getStaffDetail(username.getValue());
				if (staff.getLastName() != null) {
					// seeting session
					UI.getCurrent().getSession().setAttribute("staff", staff);
					UI.getCurrent().setContent(new MainDiv());
					
				}
				System.out.println("this is " + staff.getFirstName());
			} else {
				Notification.show("Authentication Failed");

			}

		} catch (NoSuchAlgorithmException | InvalidKeySpecException
				| SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Notification.show("Authentication Failed");

		}

	}

}
