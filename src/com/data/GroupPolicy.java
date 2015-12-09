package com.data;

import java.io.Serializable;
import java.util.ArrayList;

import com.utility.GRPDBUtility;

/**
 *
 * @author Bryanic
 */
public class GroupPolicy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int insuranceType;
	
	private String groupNumber;
	private ClientBank bank;
	private String groupPolicyNumber;
	private String dateRegistered;
	private String maturityDate;
	private double institutionalContribution;
	private double memberContribution;
	private String policyType;
	private String dateActivated;
	private String paymentFrequency;
	private String status;
	private String paymentType;
	private int paymentTypeID;
	private String agentId;
	private String currency;
	private int frequency_id;
	private int payment_id;
	private double benefits;
	private ArrayList<GroupMember> members;
	private ArrayList<double[]> agebandCharges;
	private double multiplier;
	
	private double occupationAdjustment;
	private double initialExpectedMonthlyClaims;
	private double expectedClaims;
	private double totalSalary;
	private double commission;
	private double profit;
	private double premiumTax;
	private double InternalExpenses;
	
	private double profitPlusExpense;
	private double commissionPlusTax;
	private double monthlypremium;
	private double premiumPer1000;
	
	private ArrayList<double[]> occupationClassCharges;
	
	private int staffPopulation;
	public int getInsuranceType() {
		return insuranceType;
	}
	public void setInsuranceType(int insuranceType) {
		this.insuranceType = insuranceType;
	}
	public ClientBank getBank() {
		return bank;
	}
	public void setBank(ClientBank bank) {
		this.bank = bank;
	}

	public String getGroupPolicyNumber() {
		return groupPolicyNumber;
	}
	public void setGroupPolicyNumber(String groupPolicyNumber) {
		this.groupPolicyNumber = groupPolicyNumber;
	}
	public String getDateRegistered() {
		return dateRegistered;
	}
	public void setDateRegistered(String dateRegistered) {
		this.dateRegistered = dateRegistered;
	}
	
	public double getMultiplier() {
		return multiplier;
	}
	public void setMulitiplier(double m) {
		this.multiplier = m;
	}
	public String getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public String getDateActivated() {
		return dateActivated;
	}
	public void setDateActivated(String dateActivated) {
		this.dateActivated = dateActivated;
	}
	public String getPaymentFrequency() {
		return paymentFrequency;
	}
	public void setPaymentFrequency(String paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public int getFrequency_id() {
		return frequency_id;
	}
	public void setFrequency_id(int frequency_id) {
		this.frequency_id = frequency_id;
	}
	public int getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(int payment_id) {
		this.payment_id = payment_id;
	}
	public double getBenefits() {
		return benefits;
	}
	public void setBenefits(double benefits) {
		this.benefits = benefits;
	}
	public int getPaymentTypeID() {
		return paymentTypeID;
	}
	public void setPaymentTypeID(int paymentTypeID) {
		this.paymentTypeID = paymentTypeID;
	}
	public double getInstitutionalContribution() {
		return institutionalContribution;
	}
	public void setInstitutionalContribution(double institutionalContribution) {
		this.institutionalContribution = institutionalContribution;
	}
	public double getMemberContribution() {
		return memberContribution;
	}
	public void setMemberContribution(double memberContribution) {
		this.memberContribution = memberContribution;
	}
	public int getStaffPopulation() {
		return staffPopulation;
	}
	public void setStaffPopulation(int staffPopulation) {
		this.staffPopulation = staffPopulation;
	}
	public String getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}
	
	public double getOccupationAdjustment() {
		return occupationAdjustment;
	}
	public void setOccupationAdjustment(double occupationAdjustment) {
		this.occupationAdjustment = occupationAdjustment;
	}
	public double getExpectedClaims() {
		return expectedClaims;
	}
	public void setExpectedClaims() {
		this.expectedClaims = initialExpectedMonthlyClaims*occupationAdjustment;
	}

	
	
	public double getTotalSalary() {
		return totalSalary;
	}
	public void setTotalSalary(double totalSalary) {
		this.totalSalary = totalSalary;
	}
	public double getCommission() {
		return commission;
	}
	public void setCommission(double commission) {
		this.commission = commission;
	}
	public double getProfit() {
		return profit;
	}
	public void setProfit(double profit) {
		this.profit = profit;
	}
	public double getPremiumTax() {
		return premiumTax;
	}
	public void setPremiumTax(double premiumTax) {
		this.premiumTax = premiumTax;
	}
	public ArrayList<GroupMember> getMembers() {
		return members;
	}
	public void setMembers() {
		this.members = GRPDBUtility.getGroupMembersList(groupPolicyNumber);
	}

	public GroupPolicy() {
	}
	public GroupPolicy(String policyNumber) {
	this.groupPolicyNumber=policyNumber;
	//setMembers();
	}
	public ArrayList<double[]> getOccupationClassCharges() {
		return occupationClassCharges;
	}
	public void setOccupationClassCharges(ArrayList<double[]> occupationClassCharges) {
		this.occupationClassCharges = occupationClassCharges;
	}
	public double getInternalExpenses() {
		return InternalExpenses;
	}
	public void setInternalExpenses(double internalexpenses) {
		InternalExpenses = internalexpenses;
	}
	public double getInitialExpectedMonthlyClaims() {
		return initialExpectedMonthlyClaims;
	}
	public void setInitialExpectedMonthlyClaims(double initialExpectedMonthlyClaims) {
		this.initialExpectedMonthlyClaims = initialExpectedMonthlyClaims;
	}
	public double getProfitPlusExpense() {
		return profitPlusExpense;
	}
	public void setProfitPlusExpense() {
		this.profitPlusExpense = ((profit+InternalExpenses)/100)*expectedClaims;
	}
	public double getCommissionPlusTax() {
		return commissionPlusTax;
	}
	public void setCommissionPlusTax() {
		this.commissionPlusTax = ((commissionPlusTax+premiumTax)/100)*expectedClaims;
	}
	public double getMonthlypremium() {
		return monthlypremium;
	}
	public void setMonthlypremium() {
		this.monthlypremium = expectedClaims+commissionPlusTax+profitPlusExpense;
	}
	public double getPremiumPer1000() {
		return premiumPer1000;
	}
	public void setPremiumPer1000() {
		this.premiumPer1000 = monthlypremium/totalSalary;
	}
	public ArrayList<double[]> getAgebandCharges() {
		return agebandCharges;
	}
	public void setAgebandCharges() {
		this.agebandCharges = GRPDBUtility.getPolicyAgeBandRate(groupPolicyNumber);
	}

}
