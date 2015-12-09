package com.data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Bryanic
 */
public class Policy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int insuranceType;
	private PolicyOwner customer;
	private ClientBank bank;
	private Source source;
	private String customerNumber;
	private double premium;
	private String policyNumber;
	private String dateRegistered;
	private String policyType;
	private String region;
	private String dateActivated;
	private String paymentFrequency;
	private String staff_id;
	private String region_id;
	private String status;
	private String paymentType;
	private String agentId;
	private String currency;
	private int frequency_id;
	private int payment_id;
	double initailSumAssured;
	private boolean reinsurred;
	private boolean medicalCheckRequired;

	public PolicyOwner getCustomer() {
		return this.customer;
	}

	public String getCustomerNumber() {
		return this.customerNumber;

	}

	public double getPremium() {
		return this.premium;
	}

	public double getInitialSumAssured() {
		return this.initailSumAssured;
	}

	public String getDateRegistered() {
		return this.dateRegistered;

	}


	public String getPolicyType() {
		return this.policyType;

	}

	public String getPolicyNumber() {
		return this.policyNumber;

	}

	public String getPaymentFrequency() {
		return this.paymentFrequency;

	}

	public String getStaffID() {
		return this.staff_id;

	}

	public String getStatus() {
		return this.status;

	}

	public String getAgentID() {
		return this.agentId;

	}

	public String getRegion() {
		return this.region;

	}

	public String getPaymentType() {
		return this.paymentType;

	}

	public void setCustomer(PolicyOwner cus) {
		this.customer = cus;

	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;

	}

	public void setPremium(String prem) {
		double p = Double.parseDouble(prem);
		this.premium = p;
	}

	public void setInitialSumAssured(String sum) {
		double p = Double.parseDouble(sum);
		this.initailSumAssured = p;
	}

	public void setDateRegistered() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(new Date());
		this.dateRegistered = date;

	}



	public int getFrequency_id() {
		return frequency_id;
	}

	public void setFrequency_id(int frequency_id) {
		this.frequency_id = frequency_id;
	}

	public void setDateRegistered(String s) {
		dateRegistered = s;

	}

	public void setPolicyType(String polTy) {
		this.policyType = polTy;

	}

	public void setPolicyNumber(String pN) {
		this.policyNumber = pN;

	}

	public void setPaymentFrequency(String freq) {
		this.paymentFrequency = freq;

	}

	public void setStaffID(String staff) {
		this.staff_id = staff;

	}

	public void setStatus(String stat) {
		this.status = stat;

	}

	public void setAgentID(String agent) {
		this.agentId = agent;

	}

	public void setRegion(String region) {
		this.region = region;

	}

	public void setPaymentType(String payType) {
		this.paymentType = payType;

	}

	public int getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(int payment_id) {
		this.payment_id = payment_id;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public ClientBank getBank() {
		return bank;
	}

	public void setBank(ClientBank bank) {
		this.bank = bank;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public int getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(int insuranceType) {
		this.insuranceType = insuranceType;
	}

	public String getDateActivated() {
		return dateActivated;
	}

	public void setDateActivated(String dateActivated) {
		this.dateActivated = dateActivated;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}



	public boolean isMedicalCheckRequired() {
		return medicalCheckRequired;
	}

	public void setMedicalCheckRequired(boolean medicalCheckRequired) {
		this.medicalCheckRequired = medicalCheckRequired;
	}

	public boolean isReinsurred() {
		return reinsurred;
	}

	public void setReinsurred(boolean reinsurred) {
		this.reinsurred = reinsurred;
	}

	

}
