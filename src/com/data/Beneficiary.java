package com.data;

import java.io.Serializable;

/**
 *
 * @author Bryanic
 */
public class Beneficiary extends Customer implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String policyNumber;
    private int relationship_id;
    private double percentage;
    private String region_id;
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public int getRelationship_id() {
		return relationship_id;
	}
	public void setRelationship_id(int relationship_id) {
		this.relationship_id = relationship_id;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public String getRegion_id() {
		return region_id;
	}
	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}


  

   
}
