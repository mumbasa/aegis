package com.views;

import java.sql.SQLException;

import com.component.PolicyDetails;
import com.data.Policy;
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
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class PolicyRequestApproval extends VerticalLayout implements ClickListener,
		View, ValueChangeListener, ItemClickListener {

	/**
	 * 
	 */
	Grid policiesForApproval;
	CssLayout lay = new CssLayout();
	Button approve = new Button("Approve Policy");
	Staff staffs = (Staff) UI.getCurrent().getSession().getAttribute("staff");

	public PolicyRequestApproval() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated constructor stub
		FormLayout thin = new FormLayout();
	
		policiesForApproval = new Grid();
		//claims.addItemClickListener(this);
		BeanItemContainer<Policy> policies = DBUtility.getUnApporvedPolicyDetails();
		policiesForApproval.setCaption("Result size "+policies.size());
		policiesForApproval.setContainerDataSource(policies);
		policiesForApproval.removeColumn("bank");
		policiesForApproval.removeColumn("customer");
		policiesForApproval.removeColumn("dateRegistered");
		policiesForApproval.removeColumn("frequency_id");
		policiesForApproval.removeColumn("paymentFrequency");
		policiesForApproval.removeColumn("paymentType");
		policiesForApproval.removeColumn("source");
		policiesForApproval.removeColumn("region_id");
		policiesForApproval.removeColumn("staffID");
		
		policiesForApproval.removeColumn("payment_id");
		policiesForApproval.removeColumn("policyType");
		policiesForApproval.removeColumn("dateActivated");
		policiesForApproval.removeColumn("status");
	
		policiesForApproval.setSizeFull();
		policiesForApproval.setSelectionMode(SelectionMode.MULTI);
		lay.setSizeFull();
		lay.addComponents(policiesForApproval,approve);
		approve.addClickListener(this);
		thin.setStyleName("pads");

		Label head = new Label("POLICY REQUESTS APRROVAL");
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
			UI.getCurrent().getNavigator().navigateTo("Policy Approval");		
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


	@Override
	public void itemClick(ItemClickEvent event) {
		// TODO Auto-generated method stub
	
		Policy policy =(Policy)event.getItemId();
		PolicyDetails d = new PolicyDetails(policy);

		Window window = new Window();
		window.setContent(d);
		window.center();
		window.setWidth("900px");
		window.setModal(true);
		UI.getCurrent().addWindow(window);
		
	

	}

}
