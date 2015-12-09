package com.component;

import java.sql.SQLException;

import com.data.PolicyOwner;
import com.form.CustomerForm;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class CustomerDetails extends VerticalLayout implements View,ValueChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	FormLayout layout = new FormLayout();
	
	public CustomerDetails(PolicyOwner staff) throws SQLException {
	
		
		
		
		try {
			this.addComponent(new CustomerForm(staff));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

		UI.getCurrent().getPage().setTitle("Staff Detail");

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
	
	}

}
