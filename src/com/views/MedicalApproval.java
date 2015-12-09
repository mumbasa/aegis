package com.views;

import java.sql.SQLException;

import com.component.FilterableGrid;
import com.data.Health;
import com.data.Policy;
import com.data.PolicyHolder;
import com.data.Staff;
import com.form.Healthform;
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
import com.vaadin.ui.Window;

public class MedicalApproval extends VerticalLayout implements ClickListener,
		View, ValueChangeListener, ItemClickListener {

	/**
	 * 
	 */
	FilterableGrid policiesForApproval;
	CssLayout lay = new CssLayout();
	Button approve = new Button("Approve Policy");
	Staff staffs = (Staff) UI.getCurrent().getSession().getAttribute("staff");

	public MedicalApproval() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated constructor stub
		FormLayout thin = new FormLayout();
	
		
		//claims.addItemClickListener(this);
		BeanItemContainer<PolicyHolder> policies = DBUtility.getHolderMedicalVerificaiton();
		
		policiesForApproval = new FilterableGrid(policies);
		policiesForApproval.setCaption("Result size "+policies.size());
		policiesForApproval.removeColumn("beneficiaries");
		policiesForApproval.removeColumn("children");
		policiesForApproval.removeColumn("customerNumber");
		policiesForApproval.removeColumn("familySicknessName");
		policiesForApproval.removeColumn("admissionReason");
		policiesForApproval.removeColumn("drinkingFrequency");
		policiesForApproval.removeColumn("hasFamilySickness");
		policiesForApproval.removeColumn("isDrinker");
		policiesForApproval.removeColumn("isSmoker");
		policiesForApproval.removeColumn("telephone");
		policiesForApproval.removeColumn("smokingFrequency");
		policiesForApproval.removeColumn("address");
		policiesForApproval.removeColumn("trustee");
		policiesForApproval.removeColumn("signature");
		policiesForApproval.removeColumn("region");
		policiesForApproval.removeColumn("policy");
		policiesForApproval.removeColumn("placeOfBirth");
		policiesForApproval.removeColumn("hasBeenAdmitted");
		policiesForApproval.removeColumn("maritalStatus");
		policiesForApproval.setSizeFull();
		policiesForApproval.setSelectionMode(SelectionMode.SINGLE);
		policiesForApproval.addItemClickListener(this);
		lay.setSizeFull();
		lay.addComponents(policiesForApproval);
		approve.addClickListener(this);
		thin.setStyleName("pads");

		Label head = new Label("MEDICAL REPORT VERIFICATION");
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
		UI.getCurrent().getPage().setTitle("Medical Report Verification");
	
	}


	@Override
	public void itemClick(ItemClickEvent event) {
		// TODO Auto-generated method stub
		Health health = DBUtility.getHolderHealth (event.getItem().getItemProperty("policyNumber").getValue().toString());
		//integer to indicate medical for update
		Window window = new Window();
		Healthform form = new Healthform(health,1,window);
		form.setMargin(true);
	
		window.setContent(form);
		window.center();
		window.setWidth("900px");
		window.setModal(true);
		UI.getCurrent().addWindow(window);
		
	

	}

}
