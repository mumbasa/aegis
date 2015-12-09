package com.group.components;

import java.text.DecimalFormat;
import com.data.GroupMember;
import com.data.GroupPolicy;
import com.utility.GRPDBUtility;
import com.vaadin.ui.Grid;

public class OccupationClassRateGrid extends Grid {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GroupPolicy policy;
	int[] counts = new int[GRPDBUtility.OCCUPATION_CLASSES.length];
	double[] percentage = new double[GRPDBUtility.OCCUPATION_CLASSES.length];
	DecimalFormat formatter = new DecimalFormat("#0.00");
	int totalcount = 0;
	private double occupationMultiplier = 0;

	public OccupationClassRateGrid(GroupPolicy policy) {
		this.policy = policy;
		this.addColumn("Occupation Class", String.class);
		this.addColumn("Count", Integer.class);
		this.addColumn("Percentage", Double.class);
		this.setSizeFull();
		generateData();

	}

	public void generateData() {
		for (GroupMember m : policy.getMembers()) {
			// setting the count of each member of the class;
			// substract 1 to get match the array with the id
			counts[m.getOccupationClassId() - 1]++;
			totalcount++;
		}

		for (int i = 0; i < GRPDBUtility.OCCUPATION_CLASSES.length; i++) {
			percentage[i] = (counts[i] * 1.0 / totalcount);
			// adding the product of class occupation rate by the percentage to
			// the total
			occupationMultiplier += (percentage[i] * GRPDBUtility.OCCUPATION_CLASSES_RATES[i]);
			//setOccupationMultiplier(getOccupationMultiplier()+ (percentage[i] * GRPDBUtility.OCCUPATION_CLASSES_RATES[i]));
			this.addRow(new Object[] { GRPDBUtility.OCCUPATION_CLASSES[i],counts[i], percentage[i] });
			
		
			
		}
		double news = Double.parseDouble(formatter.format(occupationMultiplier));
		setOccupationMultiplier(news);
		policy.setOccupationAdjustment(news);
		this.addRow(new Object[] { "Total",totalcount, news});
	}
	
	
	public static double getOccupationClassMultiplier(GroupPolicy policy) {
		int[] counts = new int[GRPDBUtility.OCCUPATION_CLASSES.length];
		double[] percentage = new double[GRPDBUtility.OCCUPATION_CLASSES.length];
		DecimalFormat formatter = new DecimalFormat("#0.00");
		int totalcount = 0;
		double occupationMultiplier = 0;
		for (GroupMember m : policy.getMembers()) {
			// setting the count of each member of the class;
			// substract 1 to get match the array with the id
			counts[m.getOccupationClassId() - 1]++;
			totalcount++;
		}

		for (int i = 0; i < GRPDBUtility.OCCUPATION_CLASSES.length; i++) {
			percentage[i] = (counts[i] * 1.0 / totalcount);
			// adding the product of class occupation rate by the percentage to
			// the total
			occupationMultiplier += (percentage[i] * GRPDBUtility.OCCUPATION_CLASSES_RATES[i]);
			
		}
		double news = Double.parseDouble(formatter.format(occupationMultiplier));
		
		return news;
	}
	
	
	
	
	

	public double getOccupationMultiplier() {
		return occupationMultiplier;
	}

	public void setOccupationMultiplier(double occupationMultiplier) {
		this.occupationMultiplier = occupationMultiplier;
	}

}
