package com.views;

import java.sql.SQLException;

import com.component.CustomersCombo;
import com.data.Group;
import com.form.GroupForm;
import com.form.GroupPolicyForm;
import com.utility.GRPDBUtility;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ExistingGroup extends VerticalLayout implements View,
		ValueChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TabSheet tabSheet = new TabSheet();
	CustomersCombo customers = new CustomersCombo(1,"Existing Groups");
	VerticalLayout lay = new VerticalLayout();

	public ExistingGroup() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated constructor stub
		FormLayout thin = new FormLayout();
		thin.addComponent(customers);
		thin.setStyleName("pads");
		Label head = new Label("ADD NEW GROUP POLICY");
		head.setStyleName("Heading");
		customers.addValueChangeListener(this);
		thin.setMargin(false);
		// lay.setStyleName("paddings");

		// this.setMargin(true);
		this.addComponents(head, thin, lay);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		UI.getCurrent().getPage().setTitle("Add New Policy -- Existing");
		// TODO Auto-generated method stub

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		Group owner;

		try {
			lay.removeAllComponents();
			tabSheet.removeAllComponents();
			owner = GRPDBUtility.getGroupDetail(customers.getValue().toString());
			GroupForm cus = new GroupForm(owner,tabSheet);
			GroupPolicyForm  grid = new GroupPolicyForm(owner,tabSheet);
			tabSheet.addTab(cus, "Customer BIODATA");
			tabSheet.addTab(grid, "Create Policy");
			lay.addComponent(tabSheet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
