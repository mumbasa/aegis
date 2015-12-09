package com.data;

import java.io.Serializable;

public class PolicyStatement implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int policyDuration; 
	private double previousBalance; 
	private String paymentDate; 
	private String EOM; 
	private int age;
	private double premiumPaid;
	private double premiumLoadRate; 
	private double premiumLoadAmount;
	private double netPremium;
	private double policyFee; 
	private float mortalityRate; 
	private double insuranceCost;
	private double deathBenefit; 
	private double pa; 
	private double sumAtRisk; 
	private double interestRate; 
	private double interestAmount; 
	private double partialWithdrawal;
	private double  accumulatedFund;
	private double  surrenderPenalty;
	private double  cashValue; 
	private String policyNumber;
	private double netPremiumFinal;
	public double getTotalDeduction() {
		return totalDeduction;
	}
	public void setTotalDeduction(double totalDeduction) {
		this.totalDeduction = totalDeduction;
	}
	public double getCommission() {
		return commission;
	}
	public void setCommission(double commission) {
		this.commission = commission;
	}
	private double totalDeduction;
	private double commission;
	
	
	public int getPolicyDuration() {
		return policyDuration;
	}
	public void setPolicyDuration(int policyDuration) {
		this.policyDuration = policyDuration;
	}
	public double getPreviousBalance() {
		return previousBalance;
	}
	public void setPreviousBalance(double previousBalance) {
		this.previousBalance = previousBalance;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getEOM() {
		return EOM;
	}
	public void setEOM(String eOM) {
		EOM = eOM;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public double getPremiumPaid() {
		return premiumPaid;
	}
	public void setPremiumPaid(double premiumPaid) {
		this.premiumPaid = premiumPaid;
	}
	public double getPremiumLoadRate() {
		return premiumLoadRate;
	}
	public void setPremiumLoadRate(double premiumLoadRate) {
		this.premiumLoadRate = premiumLoadRate;
	}
	public double getPremiumLoadAmount() {
		return premiumLoadAmount;
	}
	public void setPremiumLoadAmount(double premiumLoadAmount) {
		this.premiumLoadAmount = premiumLoadAmount;
	}
	public double getNetPremium() {
		return netPremium;
	}
	public void setNetPremium(double netPremium) {
		this.netPremium = netPremium;
	}
	public double getPolicyFee() {
		return policyFee;
	}
	public void setPolicyFee(double policyFee) {
		this.policyFee = policyFee;
	}
	public double getMortalityRate() {
		return mortalityRate;
	}
	public void setMortalityRate(float mortalityRate) {
		this.mortalityRate = mortalityRate;
	}
	public double getInsuranceCost() {
		return insuranceCost;
	}
	public void setInsuranceCost(double insuranceCost) {
		this.insuranceCost = insuranceCost;
	}
	public double getDeathBenefit() {
		return deathBenefit;
	}
	public void setDeathBenefit(double deathBenefit) {
		this.deathBenefit = deathBenefit;
	}
	public double getPa() {
		return pa;
	}
	public void setPa(double pa) {
		this.pa = pa;
	}
	public double getSumAtRisk() {
		return sumAtRisk;
	}
	public void setSumAtRisk(double sumAtRisk) {
		this.sumAtRisk = sumAtRisk;
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	public double getInterestAmount() {
		return interestAmount;
	}
	public void setInterestAmount(double interestAmount) {
		this.interestAmount = interestAmount;
	}
	public double getPartialWithdrawal() {
		return partialWithdrawal;
	}
	public void setPartialWithdrawal(double partialWithdrawal) {
		this.partialWithdrawal = partialWithdrawal;
	}
	public double getAccumulatedFund() {
		return accumulatedFund;
	}
	public void setAccumulatedFund(double accumulatedFund) {
		this.accumulatedFund = accumulatedFund;
	}
	public double getSurrenderPenalty() {
		return surrenderPenalty;
	}
	public void setSurrenderPenalty(double surrenderPenalty) {
		this.surrenderPenalty = surrenderPenalty;
	}
	public double getCashValue() {
		return cashValue;
	}
	public void setCashValue(double cashValue) {
		this.cashValue = cashValue;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public double getNetPremiumFinal() {
		return netPremiumFinal;
	}
	public void setNetPremiumFinal(double netPremiumFinal) {
		this.netPremiumFinal = netPremiumFinal;
	}
}
