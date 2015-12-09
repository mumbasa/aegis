package com.views;

import java.sql.SQLException;
import java.util.ArrayList;

import com.component.CustomerDetails;
import com.component.CustomersCombo;
import com.component.PolicyDetails;
import com.data.Policy;
import com.data.PolicyOwner;
import com.utility.DBUtility;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class SearchCustomer extends VerticalLayout implements ValueChangeListener,
		View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	HorizontalLayout down = new HorizontalLayout();

	CustomersCombo searchTerm = new CustomersCombo("SEARCH CUSTOMER");
	TabSheet sheet = new TabSheet();

	public SearchCustomer() {
		// TODO Auto-generated constructor stub
		FormLayout thin = new FormLayout();
		thin.setStyleName("pads");
		thin.addComponent(searchTerm);
		Label head = new Label("CUSTOMER NUMBER");
		head.setStyleName("Heading");
		searchTerm.addValueChangeListener(this);
		searchTerm.setFilteringMode(FilteringMode.CONTAINS);
	thin.setMargin(false);
	//down.setStyleName("paddings");
		
		//this.setMargin(true);
		this.addComponents(head,thin,down);
		down.setSizeFull();
	}

	
	public SearchCustomer(String customerNumber) {
		// TODO Auto-generated constructor stub
		FormLayout thin = new FormLayout();
		thin.setStyleName("pads");
		thin.addComponent(searchTerm);
		Label head = new Label("CUSTOMER NUMBER");
		head.setStyleName("Heading");
		searchTerm.setValue(customerNumber);
		searchTerm.addValueChangeListener(this);
		searchTerm.setFilteringMode(FilteringMode.CONTAINS);
	thin.setMargin(false);
	//down.setStyleName("paddings");
	getDetails(customerNumber);
		//this.setMargin(true);
		this.addComponents(head,thin,down);
		down.setSizeFull();
	}

	
	

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		UI.getCurrent().getPage().setTitle("Search Customer");
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		try {
			PolicyOwner staff = DBUtility.getCustomerDetail(searchTerm.getValue().toString());

			if (staff != null) {
				Notification.show("Good " + searchTerm.getValue());
				down.removeAllComponents();
				sheet.removeAllComponents();
				CustomerDetails d = new CustomerDetails(staff);
				sheet.addTab(d, "Customer Details");
				sheet.addStyleName("paddings");
				ArrayList<Policy> policies =  DBUtility.getCustomerPolicyDetails(staff.getCustomerNumber());
				for (int i =0;i<policies.size();i++){
					
					sheet.addTab(new PolicyDetails(policies.get(i)),"Policy "+(i+1)+" Details");
				}

				down.addComponent(sheet);
			} else {

				Notification.show("No Result =>" + searchTerm.getValue());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}

	}

	
	public void getDetails(String customerNumber){
		
		try {
			PolicyOwner staff = DBUtility.getCustomerDetail(customerNumber);

			if (staff != null) {
				Notification.show("Good " + searchTerm.getValue());
				down.removeAllComponents();
				sheet.removeAllComponents();
				CustomerDetails d = new CustomerDetails(staff);
				sheet.addTab(d, "Customer Details");
				sheet.addStyleName("paddings");
				ArrayList<Policy> policies =  DBUtility.getCustomerPolicyDetails(staff.getCustomerNumber());
				for (int i =0;i<policies.size();i++){
					
					sheet.addTab(new PolicyDetails(policies.get(i)),"Policy "+(i+1)+" Details");
				}

				down.addComponent(sheet);
			} else {

				Notification.show("bad" + searchTerm.getValue());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
