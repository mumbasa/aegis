package com.group.components;

import com.component.AgebandPolicy;
import com.data.GroupPolicy;
import com.utility.GRPDBUtility;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;

public class PolicyChargeRate extends FormLayout implements ClickListener{

	private static final long serialVersionUID = 1L;
	
	Decimalfield commission = new Decimalfield("Commission");
	Decimalfield premTax = new Decimalfield("Premium Tax");
	Decimalfield exp = new Decimalfield("Internal Expense");
	Decimalfield profit = new Decimalfield("Profit");
	TabSheet sheet;
	Button activate = new Button("Approve Policy");
	GroupPolicy policy;
	public PolicyChargeRate(GroupPolicy policy,TabSheet sheet) {
		// TODO Auto-generated constructor stub
	this.policy=policy;
	this.sheet=sheet;
	this.addComponents(profit,exp,premTax,commission,activate);
	activate.addClickListener(this);

	
	
	}
	
	
	public PolicyChargeRate(GroupPolicy policy) {
		// TODO Auto-generated constructor stub
	this.policy=policy;

	this.addComponents(profit,exp,premTax,commission);
	profit.setValue(policy.getProfit()+"");
	exp.setValue(policy.getInternalExpenses()+"");
	premTax.setValue(policy.getPremiumTax()+"");
	commission.setValue(""+policy.getCommission());
	activate.addClickListener(this);
	this.setEnabled(false);
	
	
	}
	
	
	
	
	
	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		policy.setCommission(Double.parseDouble(commission.getValue()));
		policy.setPremiumTax(Double.parseDouble(premTax.getValue()));
		policy.setInternalExpenses(Double.parseDouble(exp.getValue()));
		policy.setProfit(Double.parseDouble(profit.getValue()));
		if (GRPDBUtility.insertPolicyCharges(policy)==1){
			AgebandPolicy ageband = new AgebandPolicy(policy,sheet);
			sheet.addTab(ageband,"Set Policy Age Band");
			sheet.setSelectedTab(ageband);
		}else{
			Notification.show("Check the data again",Type.WARNING_MESSAGE);
		}
		
		
	}
}
