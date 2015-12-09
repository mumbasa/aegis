package com.data;

import java.io.Serializable;

public class LPStatement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double surrender;
	private String eom;
	private int duration;
	private double prevbal;
	private int age;
	private double initialSum =0;
	private String agentNumer;
	private double interestRate;
	private double y1Commision;
	private double nextCommission =0;
	private double mortatilyCharge;
	private double insuranceCost;
	private double finalCommission=0;
	private double totalDeduction;
	private double dateDifference;
	private double netPremium;
	private double interestAmount;
	private double total =0;
	private double amountPaid;
	private double accumulated;
	private double deathBenefit;
	private double deathBenefitFactor;
	public double getTotalDeduction() {
		return totalDeduction;
	}

	public void setTotalDeduction() {
		System.out.println("Final C "+getFinalCommission());
		System.out.println("FinalIns "+getInsuranceCost());
		System.out.println("Final "+getMortatilyCharge());
		this.totalDeduction = (getMortatilyCharge()+getInsuranceCost()+getFinalCommission())*(getInitialSum()/1000);
		System.out.println("Final totalDeductuibon "+this.totalDeduction);
	}

	public double getSurrender() {
		return surrender;
	}

	public void setSurrender(double surrender) {
		this.surrender = surrender;
	}

	public String getEom() {
		return eom;
	}

	public void setEom(String eom) {
		this.eom = eom;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getPrevbal() {
		return prevbal;
	}

	public void setPrevbal(double prevbal) {
		this.prevbal = prevbal;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getInitialSum() {
		return initialSum;
	}

	public void setInitialSum(double initialSum) {
		this.initialSum = initialSum;
	}

	public String getAgentNumer() {
		return agentNumer;
	}

	public void setAgentNumer(String agentNumer) {
		this.agentNumer = agentNumer;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public double getY1Commision() {
		return y1Commision;
	}

	public void setY1Commision(double y1Commision) {
		this.y1Commision = y1Commision;
	}

	public double getNextCommission() {
		return nextCommission;
	}

	public void setNextCommission(double nextCommission) {
		this.nextCommission = nextCommission;
	}

	public double getMortatilyCharge() {
		return mortatilyCharge;
	}

	public void setMortatilyCharge(double mortatilyCharge) {
		this.mortatilyCharge = mortatilyCharge;
	}

	public double getInsuranceCost() {
		return insuranceCost;
	}

	public void setInsuranceCost(double insuranceCost) {
		this.insuranceCost = insuranceCost;
	}

	public double getFinalCommission() {
		return finalCommission;
	}

	public void setFinalCommission() {
		if (getAgentNumer().equals("")){
			this.finalCommission = 0;
		}else if(!getAgentNumer().equals("")& getDuration()>1){
			this.finalCommission= getNextCommission();
			
		}else if (!getAgentNumer().equals("")& getDuration()==1){
			
			this.finalCommission= getY1Commision();
		}
	
	}

	public double getDateDifference() {
		return dateDifference;
	}

	public void setDateDifference(int dateDifference) {
		this.dateDifference = dateDifference;
	}

	public double getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount() {
		if  (getPrevbal()==0 & getDuration()==1){
		double pow = (getDateDifference())/365;
		double p = 1+getInterestRate();
		double dic = Math.pow(p, pow)-1;
		//.out.println("this method net "+getNetPremium());
	//	System.out.println("this method s ---> "+getNetPremium()*dic);
		this.interestAmount =getNetPremium()*dic;
		
		}
		else{
			if (getNetPremium()>0){
				double pow = (getDateDifference()+18)/365;
				double p = 1+getInterestRate();
				double left = getNetPremium()*(Math.pow(p, pow)-1);
				double thi =Math.pow((1+getInterestRate()),(1.0/12))-1 ;
				double right = getPrevbal()*thi;
				this.interestAmount =left+right;	
				System.out.println("this method s ---> "+thi);
			}
			else{
				this.interestAmount = (getPrevbal()-getMortatilyCharge()-getInsuranceCost())*(Math.pow((1+getInterestRate()),((1/12)))-1 );

			}
			
		}
	}

	public double getTotal() {
		return total;
	}

	public void setTotal() {
		this.total = getNetPremium()+getInterestAmount();
	}

	public double getNetPremium() {
		return netPremium;
	}

	public void setNetPremium() {
		this.netPremium = getAmountPaid()-getTotalDeduction();
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public double getAccumulated() {
		System.out.println("Totla "+ getTotal()+"\t"+getInterestAmount());
		return accumulated;
	}

	public void setAccumulated() {
		this.accumulated = getTotal()+getPrevbal();
	}

	public double getDeathBenefit() {
		return deathBenefit;
	}

	public void setDeathBenefit() {
		this.deathBenefit = Math.max(getInitialSum(), getAccumulated()*getDeathBenefitFactor());
	}

	public double getDeathBenefitFactor() {
		return deathBenefitFactor;
	}

	public void setDeathBenefitFactor(double deathBenefitFactor) {
		this.deathBenefitFactor = deathBenefitFactor;
	}

	
}
