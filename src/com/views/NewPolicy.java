package com.views;

import java.sql.SQLException;

import com.component.CustomersCombo;
import com.data.PolicyOwner;
import com.form.CustomerForm;
import com.form.PolicyForm;
import com.utility.DBUtility;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class NewPolicy extends VerticalLayout implements View,
		ValueChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TabSheet tabSheet = new TabSheet();
	CustomersCombo customers = new CustomersCombo("Customers");
	VerticalLayout lay = new VerticalLayout();

	public NewPolicy() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated constructor stub
		FormLayout thin = new FormLayout();
		thin.addComponent(customers);
		thin.setStyleName("pads");
		Label head = new Label("ADD NEW POLICY");
		head.setStyleName("Heading");
		customers.addValueChangeListener(this);
		thin.setMargin(false);
		// lay.setStyleName("paddings");

		// this.setMargin(true);
		this.addComponents(head, thin, lay);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		UI.getCurrent().getPage().setTitle("Add New Policy");
		// TODO Auto-generated method stub

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		PolicyOwner owner;

		try {
			lay.removeAllComponents();
			tabSheet.removeAllComponents();
			owner = DBUtility
					.getCustomerDetail(customers.getValue().toString());
			CustomerForm cus = new CustomerForm(owner);
			PolicyForm pol = new PolicyForm(tabSheet, owner);
			tabSheet.addTab(cus, "Customer BIODATA");
			tabSheet.addTab(pol, "Create Policy");
			lay.addComponent(tabSheet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
