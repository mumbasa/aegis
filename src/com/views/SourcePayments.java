package com.views;

import java.sql.SQLException;

import com.component.ChargesUpload;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class SourcePayments extends VerticalLayout implements View{

	private static final long serialVersionUID = 1L;
public SourcePayments() throws ClassNotFoundException, SQLException {
	// TODO Auto-generated constructor stub
	CssLayout body = new CssLayout();
	ChargesUpload file = new ChargesUpload("Bank Payments",body,"Source"); 
	FormLayout form = new FormLayout(file);
Label head = new Label("ADD SOURCE PAYMENTS LIST");
head.setStyleName("Heading");
this.addComponents(head,form,body);
body.setSizeFull();

}
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
