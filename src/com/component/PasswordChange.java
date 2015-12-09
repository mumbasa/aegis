package com.component;

import java.sql.SQLException;

import com.data.Staff;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class PasswordChange extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Staff staff = (Staff)UI.getCurrent().getSession().getAttribute("staff");	

	
	public PasswordChange() {
		// TODO Auto-generated constructor stub
	this.center();
	this.setCaption("Password Change");
	this.setWidth("500px");
	this.setHeight("260px");
	this.setModal(true);
	this.setStyleName("profilewin");
	try {
		PasswordSetRole role = new PasswordSetRole(staff,this);
		role.setMargin(true);
		this.setContent(role);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
}
