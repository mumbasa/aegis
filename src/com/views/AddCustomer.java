package com.views;

import java.sql.SQLException;

import com.form.CustomerForm;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class AddCustomer extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AddCustomer() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated constructor stub
		TabSheet tabSheet = new TabSheet();
		CustomerForm customerForm = new CustomerForm(tabSheet);
		Label head = new Label("ADD CUSTOMER");
		head.setStyleName("Heading");
		tabSheet.addTab(customerForm, "Customer BIODATA");
		tabSheet.setStyleName("paddings");
		//this.setMargin(true);
		this.addComponents(head,tabSheet);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		UI.getCurrent().getPage().setTitle("Add Customer");
	}

}
