package com.views;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import com.component.MainDiv;
import com.data.Staff;
import com.utility.DBUtility;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class CopyOfLogin extends GridLayout implements View, ClickListener {

	private static final long serialVersionUID = 1L;
	private TextField username = new TextField("Username");

	private PasswordField pass = new PasswordField("Password");

	public CopyOfLogin(UI ui) {
		// TODO Auto-generated constructor stub
		super();
		// this.ui =ui;

		this.setColumns(11);
		this.setRows(11);
		FormLayout logform = new FormLayout();
		this.setSizeFull();
		this.setStyleName("login");
		Button enter = new Button("Login");

		logform.addComponents(username, pass, enter);
		this.addComponent(logform, 4, 5);

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
			if (DBUtility.authenticateUser(username.getValue(), pass.getValue())) {
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
