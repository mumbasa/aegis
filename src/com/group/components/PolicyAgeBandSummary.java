package com.group.components;

import java.util.ArrayList;

import com.data.GroupMember;
import com.data.GroupPolicy;
import com.utility.DBUtility;
import com.utility.GRPDBUtility;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class PolicyAgeBandSummary extends VerticalLayout implements ClickListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	double[] maleAgeBandSum = new double[GRPDBUtility.AGE_BANDS.length];
	double[] femaleAgeBandSum = new double[GRPDBUtility.AGE_BANDS.length];
	double[] totalAgeBandSum = new double[GRPDBUtility.AGE_BANDS.length];
	GroupPolicy policy;
	ArrayList<double[]> data;
	TabSheet sheet;
	Grid grid = new Grid();
	private double totalSalary;
	private double totalSalaryFactored;
	Button button = new Button("Continue");
	public PolicyAgeBandSummary(GroupPolicy policy,TabSheet sheet) {
		// TODO Auto-generated constructor stub
		this.sheet=sheet;
		this.policy=policy;
		data = GRPDBUtility.getPolicyAgeBandRate(policy.getGroupPolicyNumber());
		this.setSizeFull();
		grid.addColumn("AgeBand",String.class);
		grid.addColumn("MaleRate",Double.class);
		grid.addColumn("FemaleRate",Double.class);
		grid.addColumn("MaleSumAmount",Double.class);
		grid.addColumn("FemaleSumAmount",Double.class);
		grid.addColumn("MaleTotalClaim",Double.class);
		grid.addColumn("FemalTotalClaim",Double.class);
		grid.addColumn("TotalExpectedClaim",Double.class);
		grid.setSizeFull();
		policy.setOccupationClassCharges(data);
		generateRows();
		this.addComponents(grid,button);
		button.addClickListener(this);
		this.setSpacing(true);
	}
	
	public PolicyAgeBandSummary(GroupPolicy policy) {
		// TODO Auto-generated constructor stub
	
		this.policy=policy;
		data = policy.getAgebandCharges();
		this.setSizeFull();
		grid.addColumn("AgeBand",String.class);
		grid.addColumn("MaleRate",Double.class);
		grid.addColumn("FemaleRate",Double.class);
		grid.addColumn("MaleSumAmount",Double.class);
		grid.addColumn("FemaleSumAmount",Double.class);
		grid.addColumn("MaleTotalClaim",Double.class);
		grid.addColumn("FemalTotalClaim",Double.class);
		grid.addColumn("TotalExpectedClaim",Double.class);
		grid.setSizeFull();
		generateRows();
		this.addComponents(grid);
		this.setSpacing(true);
	}
	
	
	
	public void generateRows(){
		//getting the adjusted rate for the genders 
		double[] maleAgeBandRate = data.get(0);
		double[] femaleAgeBandRate = data.get(1);
		
		for (GroupMember member : policy.getMembers()){
			int age = DBUtility.getAge(member.getDob());
			if (member.getGender().equalsIgnoreCase("M")){
				//setting the total salary for each age band male
				maleAgeBandSum[GRPDBUtility.getAgeBand(age)]+=member.getSalary();
			}else{
				//setting the total salary for each age band female
				femaleAgeBandSum[GRPDBUtility.getAgeBand(age)]+=member.getSalary();
			}
			}
		
		for (int i =0;i<GRPDBUtility.AGE_BANDS.length;i++){
		//setting the adjusted claim rate with ageband rate	
		double maleAmount =maleAgeBandSum[i]*maleAgeBandRate[i];
		//adding to get total
		totalAgeBandSum[i]+=maleAmount;
		double femaleAmount =femaleAgeBandSum[i]*femaleAgeBandRate[i];
		totalAgeBandSum[i]+=femaleAmount;
	
		grid.addRow(new Object[]{GRPDBUtility.AGE_BANDS[i],maleAgeBandRate[i],femaleAgeBandRate[i],maleAgeBandSum[i],femaleAgeBandSum[i],maleAmount,femaleAmount,maleAmount+femaleAmount});
		}
		grid.addRow(new Object[]{"Total",maleAgeBandRate[5],femaleAgeBandRate[5],GRPDBUtility.sumArray(maleAgeBandSum),GRPDBUtility.sumArray(femaleAgeBandSum),0.0,0.0,GRPDBUtility.sumArray(totalAgeBandSum)});
		//setTotalSalary(GRPDBUtility.sumArray(maleAgeBandSum)+GRPDBUtility.sumArray(femaleAgeBandSum));
	
		policy.setTotalSalary(GRPDBUtility.sumArray(maleAgeBandSum)+GRPDBUtility.sumArray(femaleAgeBandSum));
		policy.setInitialExpectedMonthlyClaims(GRPDBUtility.sumArray(totalAgeBandSum));
		policy.setExpectedClaims();
		GRPDBUtility.getPolicyCharges(policy);
		policy.setProfitPlusExpense();
		policy.setCommissionPlusTax();
		policy.setMonthlypremium();
		policy.setPremiumPer1000();
	}
	
	
	public static double getTotalfactored(ArrayList<GroupMember> members,ArrayList<double[]> data){
		double[] maleAgeBandSum = new double[GRPDBUtility.AGE_BANDS.length];
		double[] femaleAgeBandSum = new double[GRPDBUtility.AGE_BANDS.length];
		double[] totalAgeBandSum = new double[GRPDBUtility.AGE_BANDS.length];
		double[] maleAgeBandRate = data.get(0);
		double[] femaleAgeBandRate = data.get(1);
		for (GroupMember member : members){
			int age = DBUtility.getAge(member.getDob());
			if (member.getGender().equalsIgnoreCase("M")){
				System.out.println(member.getSalary());
				maleAgeBandSum[GRPDBUtility.getAgeBand(age)]+=member.getSalary();
			}else{
		
				femaleAgeBandSum[GRPDBUtility.getAgeBand(age)]+=member.getSalary();
			}
			}
		for (int i =0;i<GRPDBUtility.AGE_BANDS.length;i++){
		double maleAmount =maleAgeBandSum[i]*maleAgeBandRate[i];
		totalAgeBandSum[i]+=maleAmount;
		double femaleAmount =femaleAgeBandSum[i]*femaleAgeBandRate[i];
		totalAgeBandSum[i]+=femaleAmount;

		}
		return(GRPDBUtility.sumArray(totalAgeBandSum));
	
	}
	
	

	public double getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(double totalSalary) {
		this.totalSalary = totalSalary;
	}

	public double getTotalSalaryFactored() {
		return totalSalaryFactored;
	}

	public void setTotalSalaryFactored(double totalSalaryFactored) {
		this.totalSalaryFactored = totalSalaryFactored;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		PremiumBreakDown breakDown = new PremiumBreakDown(policy);
		sheet.addTab(breakDown, "Policy Summary");
		sheet.setSelectedTab(breakDown);
	}
}
