package com.component;

import java.text.DecimalFormat;

import com.form.StatementForm;
import com.utility.DBUtility;
import com.utility.PaymentDBUtil;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ClaimsApprovalWindow extends Window implements ClickListener,
		ValueChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TextField field = new TextField("Approved Amount");
	double accumulated = 0.0;
	Button approve = new Button("Approve Claim");
	double percentage = 0.0;
	double amountWanted = 0.0;
	String policyNumber;
	private int claimID;
	public ClaimsApprovalWindow(String policyNumber, double amountWanted,int claimID) {
		// TODO Auto-generated constructor stub
		this.policyNumber=policyNumber;
		this.claimID=claimID;
		HorizontalLayout l = StatementForm.getStatementSlip(policyNumber);
		this.amountWanted = amountWanted;
		l.setEnabled(false);

		TextField fileAmount = new TextField("Amount Wanted");
		TextField fileAmountPercentage = new TextField("Percent of Accumulated");
		field.addValueChangeListener(this);
		approve.setIcon(FontAwesome.THUMBS_UP);
		Button disApprove = new Button("Disapprove Claim");
		disApprove.setIcon(FontAwesome.THUMBS_DOWN);
		HorizontalLayout holder = new HorizontalLayout(approve, disApprove);
		holder.setSpacing(true);
		approve.addClickListener(this);
		accumulated = StatementForm.getAccumulated();
		percentage = amountWanted / accumulated * 100;

		DecimalFormat df = new DecimalFormat("0.00");
		approve.setEnabled(false);
		disApprove.addClickListener(this);
		fileAmountPercentage.setValue(df.format(percentage) + "%");
		fileAmount.setValue("" + this.amountWanted);
		FormLayout form = new FormLayout(fileAmount, fileAmountPercentage,
				field, holder);
		VerticalLayout layout = new VerticalLayout(l, form);
		layout.setMargin(true);
		this.setContent(layout);
		this.setWidth("900px");
		this.setHeight("500px");
		this.center();
		this.setModal(true);
		this.setStyleName("profilewin");
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		System.out.println("Changed Amount "+event.getProperty().getValue().toString() +"---->"+amountWanted);
		double change = Double.parseDouble(event.getProperty().getValue().toString());
		if (change > amountWanted) {
		
			approve.setEnabled(false);
		} 
		else {
			approve.setEnabled(true);
		}
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		System.out.println(event.getButton().getCaption());
		
		
		if(event.getButton().getCaption().equals("Approve Claim")){
			double amount =Double.parseDouble(field.getValue());
			if(DBUtility.claimApproval(claimID, policyNumber, amount,"A")==1){
				Notification.show("Policy claimed Approved");
			if(PaymentDBUtil.insertApprovedClaimsStatement(policyNumber,Double.parseDouble(field.getValue()))==1){
				Notification.show("Approved");
				
			}
			this.close();
			UI.getCurrent().getNavigator().navigateTo("Claims Processing");
			}
		}
		
		else{
			if(DBUtility.claimApproval(claimID, policyNumber, 0.0,"NA")==1){
				Notification.show("Policy Not Approved");
				this.close();
				}{
					Notification.show("Policy Not Approved",Type.ERROR_MESSAGE);
				}
		
		}
		
		
	}

}
