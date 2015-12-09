package com.views;

import java.sql.SQLException;

import com.component.FilterableGrid;
import com.data.Policy;
import com.data.PolicyOwner;
import com.data.Staff;
import com.utility.DBUtility;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


public class AllCustomers extends VerticalLayout implements ClickListener,
		View, ValueChangeListener, ItemClickListener {

	/**
	 * 
	 */
	FilterableGrid policiesForApproval;
	CssLayout lay = new CssLayout();
	Button approve = new Button("Approve Policy");
	Staff staffs = (Staff) UI.getCurrent().getSession().getAttribute("staff");

	public AllCustomers() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated constructor stub
		FormLayout thin = new FormLayout();
	
		
		//claims.addItemClickListener(this);
		BeanItemContainer<PolicyOwner> policies = DBUtility.AllgetCustomerDetails();
		
		policiesForApproval = new FilterableGrid(policies);
		policiesForApproval.setCaption("Result size "+policies.size());
		policiesForApproval.removeColumn("signature");
		policiesForApproval.removeColumn("picture");
		policiesForApproval.removeColumn("children");
		policiesForApproval.removeColumn("policyOwnerId");
		policiesForApproval.removeColumn("date");
		policiesForApproval.removeColumn("occupationId");
		policiesForApproval.removeColumn("region");
		policiesForApproval.removeColumn("password");
		policiesForApproval.removeColumn("maritalStatusId");
		policiesForApproval.removeColumn("placeOfBirth");
		policiesForApproval.setSizeFull();
		policiesForApproval.setSelectionMode(SelectionMode.SINGLE);
		policiesForApproval.addItemClickListener(this);
		lay.setSizeFull();
		lay.addComponents(policiesForApproval);
		approve.addClickListener(this);
		thin.setStyleName("pads");

		Label head = new Label("ALL CUSTOMERS");
		head.setStyleName("Heading");
	
		thin.setMargin(false);
		lay.setStyleName("paddings");

		// this.setMargin(true);
		this.addComponents(head, thin, lay);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		int stat=0;
		for (Object id :policiesForApproval.getSelectedRows()){
			Policy pay =(Policy) id;
			stat=DBUtility.PolicyApproval(staffs.getStaffNumber(), pay.getPolicyNumber());
		}
		
		if (stat==1){
			Notification.show("Policies Approved");
			UI.getCurrent().getNavigator().navigateTo("Customer Approval");		
		}
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-(generated method stub
		UI.getCurrent().getPage().setTitle("All Customers");
	
	}


	@Override
	public void itemClick(ItemClickEvent event) {
		// TODO Auto-generated method stub
	String customerNumber = event.getItem().getItemProperty("customerNumber").getValue().toString();
		UI.getCurrent().getNavigator().getDisplay().showView(new SearchCustomer(customerNumber));

		
	

	}

}
