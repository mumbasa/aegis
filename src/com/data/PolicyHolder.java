package com.data;

import java.util.ArrayList;

public class PolicyHolder extends Customer {

	private static final long serialVersionUID = 1L;
	private String picture;
	private String signature;
	private String isSmoker;
	private String isDrinker;
	private String drinkingFrequency;
	private String smokingFrequency;
	private String hasFamilySickness;
	private String hasBeenAdmitted;
	private String admissionReason;
	private String familySicknessName;
	private String dateRegistered;
	private String policyNumber;
	private Policy policy;
	
	private String region_id;
	private ArrayList<Beneficiary> beneficiaries =new ArrayList<Beneficiary>();
	private Trustee trustee;
	private Health health;
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getIsSmoker() {
		return isSmoker;
	}
	public void setIsSmoker(String isSmoker) {
		this.isSmoker = isSmoker;
	}
	public String getIsDrinker() {
		return isDrinker;
	}
	public void setIsDrinker(String isDrinker) {
		this.isDrinker = isDrinker;
	}
	public String getDrinkingFrequency() {
		return drinkingFrequency;
	}
	public void setDrinkingFrequency(String drinkingFrequency) {
		this.drinkingFrequency = drinkingFrequency;
	}
	public Policy getPolicy() {
		return policy;
	}
	public void setPolicy(Policy policy) {
		this.policy = policy;
	}
	public String getSmokingFrequency() {
		return smokingFrequency;
	}
	public void setSmokingFrequency(String smokingFrequency) {
		this.smokingFrequency = smokingFrequency;
	}
	public String getHasFamilySickness() {
		return hasFamilySickness;
	}
	public void setHasFamilySickness(String hasFamilySickness) {
		this.hasFamilySickness = hasFamilySickness;
	}
	public String getHasBeenAdmitted() {
		return hasBeenAdmitted;
	}
	public void setHasBeenAdmitted(String hasBeenAdmitted) {
		this.hasBeenAdmitted = hasBeenAdmitted;
	}
	public String getAdmissionReason() {
		return admissionReason;
	}
	public void setAdmissionReason(String admissionReason) {
		this.admissionReason = admissionReason;
	}
	public String getFamilySicknessName() {
		return familySicknessName;
	}
	public void setFamilySicknessName(String familySicknessName) {
		this.familySicknessName = familySicknessName;
	}
	public String getDateRegistered() {
		return dateRegistered;
	}
	public void setDateRegistered(String dateRegistered) {
		this.dateRegistered = dateRegistered;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public String getRegion_id() {
		return region_id;
	}
	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	public ArrayList<Beneficiary> getBeneficiaries() {
		return beneficiaries;
	}
	public void setBeneficiaries(ArrayList<Beneficiary> beneficiaries) {
		this.beneficiaries = beneficiaries;
	}
	public Trustee getTrustee() {
		return trustee;
	}
	public void setTrustee(Trustee trustee) {
		this.trustee = trustee;
	}
	
	public boolean isTrusteeAllowed() {
		
		int stat = 1;
		for (int i = 0; i < getBeneficiaries().size(); i++) {
			if (getBeneficiaries().get(i).getAge() >17) {
				stat=0;
			}

		}
		if (stat==0){
			return false;
			
		}
		else{
			return true;
		}
		

	}
	public Health getHealth() {
		return health;
	}
	public void setHealth(Health health) {
		this.health = health;
	}
}
