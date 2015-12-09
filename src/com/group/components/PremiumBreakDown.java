package com.group.components;

import com.data.GroupPolicy;
import com.utility.GRPDBUtility;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

public class PremiumBreakDown extends FormLayout implements ClickListener{

	private static final long serialVersionUID = 1L;
	
	Decimalfield expectedClaims = new Decimalfield("Initial Expected Monthly Claims");
	Decimalfield occAdj = new Decimalfield("Occupation Adjustment");
	Decimalfield exClaims = new Decimalfield("Expected Claims");
	Decimalfield profitExp = new Decimalfield("Profit and Internal Expense");
	Decimalfield commTax = new Decimalfield("Commissions and Prm Tax");
	Decimalfield premium = new Decimalfield("Premium / Month");
	Decimalfield payRoll = new Decimalfield("Premium / Month");
	Decimalfield premiumFac = new Decimalfield("Premium / ¢1000 / Month");
	Button activate = new Button("Approve Policy");
	GroupPolicy policy;
	public PremiumBreakDown(GroupPolicy policy) {
		// TODO Auto-generated constructor stub
	this.policy=policy;
	this.addComponents(expectedClaims,occAdj,exClaims,profitExp,commTax,premium,payRoll,premiumFac);
	expectedClaims.setValue(""+this.policy.getInitialExpectedMonthlyClaims());
	occAdj.setValue(""+policy.getOccupationAdjustment());
	exClaims.setValue(""+policy.getExpectedClaims());
	profitExp.setValue(""+policy.getProfitPlusExpense());
	commTax.setValue(""+policy.getCommissionPlusTax());
	premium.setValue(policy.getMonthlypremium()+"");
	payRoll.setValue(policy.getTotalSalary()+"");
	premiumFac.setValue(policy.getPremiumPer1000()+"");
	this.setEnabled(false);
	}
	
	public PremiumBreakDown(GroupPolicy policy,int a) {
		// TODO Auto-generated constructor stub
	this.policy=policy;
	this.addComponents(expectedClaims,occAdj,exClaims,profitExp,commTax,premium,payRoll,premiumFac,activate);
	expectedClaims.setValue(""+this.policy.getInitialExpectedMonthlyClaims());
	occAdj.setValue(""+policy.getOccupationAdjustment());
	exClaims.setValue(""+policy.getExpectedClaims());
	profitExp.setValue(""+policy.getProfitPlusExpense());
	commTax.setValue(""+policy.getCommissionPlusTax());
	premium.setValue(policy.getMonthlypremium()+"");
	payRoll.setValue(policy.getTotalSalary()+"");
	premiumFac.setValue(policy.getPremiumPer1000()+"");
	activate.addClickListener(this);
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		if (GRPDBUtility.insertGroupPolicyPremium(policy)==1){
			Notification.show(policy.getGroupPolicyNumber()+" has been approved");
			UI.getCurrent().getNavigator().navigateTo("Dashboard");
			
		}else{
			Notification.show(policy.getGroupPolicyNumber()+" has been not approved try again",Type.WARNING_MESSAGE);
		}
	}
}
