package com.views;

import java.sql.SQLException;

import com.form.ClaimsForm;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class ClaimsView extends CssLayout implements View{

	private static final long serialVersionUID = 1L;
public ClaimsView() throws ClassNotFoundException, SQLException {
	// TODO Auto-generated constructor stub
	Label head = new Label("POLICY CLAIMS APPLICATION");
	head.setStyleName("Heading");
	this.setWidth("100%");
ClaimsForm form = new ClaimsForm();

this.addComponents(head,form);

}
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		UI.getCurrent().getPage().setTitle("Claims Filing");	
	}

}
