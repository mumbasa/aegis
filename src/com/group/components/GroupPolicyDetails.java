package com.group.components;

import com.component.AgebandPolicy;
import com.component.FilterableGrid;
import com.component.GroupMembersGrid;
import com.data.GroupPolicy;
import com.form.GroupPolicyForm;
import com.utility.GRPDBUtility;
import com.vaadin.ui.TabSheet;

public class GroupPolicyDetails extends TabSheet {

	private static final long serialVersionUID = 1L;

	public GroupPolicyDetails(GroupPolicy p) {
		// TODO Auto-generated constructor stub

		this.setSizeFull();
		this.addTab(new GroupPolicyForm(p), p.getGroupPolicyNumber() + " Details");
		FilterableGrid grid = GRPDBUtility.groupAgebandGeneration(p.getMembers());
		PolicyChargeRate charges = new PolicyChargeRate(p);
		this.addTab(grid,"Age Band");
		GroupMembersGrid mes = new GroupMembersGrid(p);
		this.addTab(mes,"Beneficiaries");
		OccupationClassRateGrid rate = new OccupationClassRateGrid(p);
		this.addTab(rate,"Ageband Details");
		this.addTab(charges," Charges");
		AgebandPolicy ageband = new AgebandPolicy(p);
		this.addTab(ageband,"Ageband Adjustments");
		PolicyAgeBandSummary band = new PolicyAgeBandSummary(p);
		this.addTab(band,"AgeBandCharges Summary");
		PremiumBreakDown premiumBreak = new PremiumBreakDown(p);
		this.addTab(premiumBreak,"Premium BreakDown");
	}
	
	
	public GroupPolicyDetails(GroupPolicy p,int a) {
		// TODO Auto-generated constructor stub

		this.setSizeFull();
		this.addTab(new GroupPolicyForm(p), p.getGroupPolicyNumber() + " Details");
		FilterableGrid grid = GRPDBUtility.groupAgebandGeneration(p.getMembers());
		
		PolicyChargeRate charges = new PolicyChargeRate(p,this);
		this.addTab(grid,"Policy Age Band");
		GroupMembersGrid mes = new GroupMembersGrid(p);
		this.addTab(mes,"Policy Beneficiaries");
		OccupationClassRateGrid rate = new OccupationClassRateGrid(p);
		this.addTab(rate,"Policy Ageband Details");
		this.addTab(charges,"Set Charges");
		
	
	
	}

}
