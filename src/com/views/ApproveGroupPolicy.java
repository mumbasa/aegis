package com.views;

import java.sql.SQLException;

import com.component.CustomersCombo;
import com.data.Group;
import com.data.GroupPolicy;
import com.form.GroupForm;
import com.group.components.GroupPolicyDetails;
import com.utility.GRPDBUtility;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class ApproveGroupPolicy extends VerticalLayout implements ValueChangeListener,
		View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	HorizontalLayout down = new HorizontalLayout();
	VerticalLayout lay = new VerticalLayout();
	CustomersCombo searchTerm = new CustomersCombo(1,"Exiting Groups");
	TabSheet sheet = new TabSheet();

	public ApproveGroupPolicy() {
		// TODO Auto-generated constructor stub
		FormLayout thin = new FormLayout();
		thin.setStyleName("pads");
		thin.addComponent(searchTerm);
		Label head = new Label("GROUP NUMBER");
		head.setStyleName("Heading");
		searchTerm.addValueChangeListener(this);
		searchTerm.setFilteringMode(FilteringMode.CONTAINS);
		thin.setMargin(false);
	//down.setStyleName("paddings");
		
		//this.setMargin(true);
		this.addComponents(head,thin,lay);
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

		Group owner;
		try {
			lay.removeAllComponents();
			sheet.removeAllComponents();
			owner = GRPDBUtility.getGroupDetail(searchTerm.getValue().toString());
			GroupPolicy policy = GRPDBUtility.getPolicyDetail(searchTerm.getValue().toString());
			GroupForm cus = new GroupForm(owner,sheet);
	
			sheet.addTab(cus, "Customer BIODATA");
			GroupPolicyDetails details = new GroupPolicyDetails(policy);
			
			sheet.addTab(details, policy.getGroupPolicyNumber()+"details");
		
			lay.addComponent(sheet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public void getDetails(String customerNumber){
		
	}
}
