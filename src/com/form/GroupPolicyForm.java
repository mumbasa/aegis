package com.form;

import tm.kod.widgets.numberfield.NumberField;

import com.component.AgebandPolicy;
import com.component.AgentsCombo;
import com.component.CurrenciesCombo;
import com.component.DOBDateField;
import com.component.FilterableGrid;
import com.component.GroupMembersGrid;
import com.component.PaymentFrequency;
import com.component.PaymentTypeCombo;
import com.component.PolicyTypesCombo;
import com.component.RegionCombo;
import com.data.Group;
import com.data.GroupMember;
import com.data.GroupPolicy;
import com.group.components.Decimalfield;
import com.group.components.OccupationClassRateGrid;
import com.utility.GRPDBUtility;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;

public class GroupPolicyForm extends FormLayout implements ClickListener {

	private static final long serialVersionUID = 1L;
	TabSheet tabSheet;
	GroupPolicy policy;
	Group group;
	NumberField staffs = new NumberField("Number of Staff");
	RegionCombo region = new RegionCombo("Region");
	Decimalfield multiplier = new Decimalfield("Multiplier of Salary");
	NumberField institution = new NumberField(
			"Level of Institution Contribution");
	Decimalfield amount = new Decimalfield("Benefit Amount");
	CurrenciesCombo currency = new CurrenciesCombo("Currency");
	PaymentFrequency frequency = new PaymentFrequency("Frequency");
	PaymentTypeCombo mode = new PaymentTypeCombo("Payment Type");
	DOBDateField date = new DOBDateField("Effective Date");
	PolicyTypesCombo type = new PolicyTypesCombo(true);
	Button button = new Button("Add Policy");
	int stat = 0;
	AgentsCombo agents = new AgentsCombo("Agents");
	BeanItemContainer<GroupMember> members;

	public GroupPolicyForm(TabSheet tabSheet, Group group) {
		// TODO Auto-generated constructor stub
		this.group = group;
		this.tabSheet = tabSheet;
		button.addClickListener(this);
		this.addComponents(staffs, type, currency, amount, mode, institution,
				agents, multiplier, frequency, date, button);

		this.setSpacing(true);
	}
	
	
	
	
	
	
	
	
	public GroupPolicyForm(TabSheet tabSheet, GroupPolicy policy) {
		// TODO Auto-generated constructor stub
		this.policy =policy;
		this.tabSheet = tabSheet;
		button.addClickListener(this);
		staffs.setValue(policy.getStaffPopulation()+"");
		mode.setValue(policy.getPaymentTypeID());
		currency.setValue(policy.getCurrency());
		institution.setValue(policy.getInstitutionalContribution()+"");
		multiplier.setValue(policy.getMultiplier()+"");
		amount.setValue(policy.getBenefits()+"");
		agents.setValue(policy.getAgentId());
		frequency.setValue(Integer.parseInt(policy.getPaymentFrequency()));
		type.setValue(policy.getPolicyType());
	
		FilterableGrid grid = GRPDBUtility.groupAgebandGeneration(GRPDBUtility.getGroupPolicyMembers(policy.getGroupPolicyNumber()));
		tabSheet.addTab(grid, "Policy Summary");
		AgebandPolicy grid2 = new AgebandPolicy(policy);
		tabSheet.addTab(grid2, "Age Band Rate");
	
		OccupationClassRateGrid occs = new OccupationClassRateGrid(policy);
		tabSheet.addTab(occs, "Occupation Class Summary");
		this.addComponents(staffs, type, currency, amount, mode, institution,
				agents, multiplier, frequency, date);
		this.setEnabled(false);
		this.setSpacing(true);
	}

	

	public GroupPolicyForm(GroupPolicy policy) {
		// TODO Auto-generated constructor stub
		this.policy =policy;
		
		button.addClickListener(this);
		staffs.setValue(policy.getStaffPopulation()+"");
		mode.setValue(policy.getPaymentTypeID());
		currency.setValue(policy.getCurrency());
		institution.setValue(policy.getInstitutionalContribution()+"");
		multiplier.setValue(policy.getMultiplier()+"");
		amount.setValue(policy.getBenefits()+"");
		agents.setValue(policy.getAgentId());
		frequency.setValue(Integer.parseInt(policy.getPaymentFrequency()));
		type.setValue(policy.getPolicyType());
	
		this.addComponents(staffs, type, currency, amount, mode, institution,
				agents, multiplier, frequency, date);
		this.setEnabled(false);
		this.setSpacing(true);
	
	}
	
	
	
	
	
	
	
	public GroupPolicyForm(Group group,TabSheet tabSheet) {
		// TODO Auto-generated constructor stub
		stat = 1;
		this.group = group;
		this.tabSheet = tabSheet;
		button.addClickListener(this);

		this.addComponents(staffs, type, currency, amount, mode, institution,
				agents, multiplier, frequency, date, button);

		this.setSpacing(true);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		GroupPolicy policy = new GroupPolicy();
		policy.setCurrency(currency.getValue().toString());
		policy.setPolicyType(type.getValue().toString());
		policy.setPaymentFrequency(frequency.getValue().toString());
		policy.setPaymentTypeID((int) mode.getValue());
		policy.setDateRegistered(date.getDateValue());
		policy.setBenefits((Double.parseDouble(amount.getValue())));
		policy.setInstitutionalContribution(Double.parseDouble(institution.getValue()));
		policy.setMulitiplier(Double.parseDouble(multiplier.getValue()));
		policy.setAgentId(agents.getValue() == null ? "" : agents.getValue().toString());
		policy.setStaffPopulation(Integer.parseInt(staffs.getValue()));
		policy.setCurrency(currency.getValue().toString());
		if (stat == 0) {
			if ((int)mode.getValue()==1){
				
				
			}
		if (GRPDBUtility.insertGroup(group, policy) == 1) {
					GroupPolicyStaffUploadForm form = new GroupPolicyStaffUploadForm(
						tabSheet, policy);
				this.tabSheet.addTab(form, "Upload Staff");
				this.tabSheet.setSelectedTab(form);
	
			
		
		} 
		else {
			Notification.show("Camron", Type.ERROR_MESSAGE);
		}
		}
		else {
			if (GRPDBUtility.insertGroupKnown(group, policy) == 1) {
	
				GroupMembersGrid members = new GroupMembersGrid(group, tabSheet,policy);
				this.tabSheet.addTab(members, "Add Staff");
				this.tabSheet.setSelectedTab(members);
			}else{
				Notification.show("Known not known", Type.ERROR_MESSAGE);
			}
			}
		} 
	
}
