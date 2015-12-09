package com.views;

import java.sql.SQLException;

import com.data.Policy;
import com.data.Staff;
import com.utility.DBUtility;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class CustomerPolicyRequestApproval extends VerticalLayout implements ClickListener,
		View, ValueChangeListener {

	/**
	 * 
	 */
	Grid policesToBeApproved;
	//DateField searchTerm = new DateField("Date");
	CssLayout lay = new CssLayout();
	Button approve = new Button("Approve Policy");
	Staff staffs = (Staff) UI.getCurrent().getSession().getAttribute("staff");

	public CustomerPolicyRequestApproval() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated constructor stub
		FormLayout thin = new FormLayout();
	
		BeanItemContainer<Policy> policies = DBUtility.getUnApporvedPolicyDetails();
		
	//	if (policies.getItemIds().size()>0){
			policesToBeApproved = new Grid();
		policesToBeApproved.setContainerDataSource(policies);
		policesToBeApproved.removeColumn("bank");
		policesToBeApproved.removeColumn("customer");
		policesToBeApproved.removeColumn("dateRegistered");
		policesToBeApproved.removeColumn("frequency_id");
		policesToBeApproved.removeColumn("paymentFrequency");
		policesToBeApproved.removeColumn("paymentType");
		policesToBeApproved.removeColumn("source");
		policesToBeApproved.removeColumn("region_id");
		policesToBeApproved.removeColumn("staffID");
		
		policesToBeApproved.removeColumn("payment_id");
		policesToBeApproved.removeColumn("policyType");
		policesToBeApproved.removeColumn("dateActivated");
		policesToBeApproved.removeColumn("status");
	
		policesToBeApproved.setSizeFull();
		policesToBeApproved.setSelectionMode(SelectionMode.MULTI);
		lay.setSizeFull();
		lay.addComponents(policesToBeApproved,approve);
	//	}else{
		lay.setVisible(false);
			lay.setSizeFull();
	//	}
		approve.addClickListener(this);
		thin.setStyleName("pads");

		Label head = new Label("CUSTOMER POLICY REQUESTS APRROVAL");
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
		for (Object id :policesToBeApproved.getSelectedRows()){
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

	
	}


	
}
