package com.data;

import java.util.Date;

public class PolicyOwner extends Customer{

	private static final long serialVersionUID = 1L;
	String policyOwnerId;
	private String region_id;
	private String picture;
	private String signature;
	private Date date;
	private String password;
	
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
	public String getPolicyOwnerId() {
		return policyOwnerId;
	}
	public void setPolicyOwnerId(String policyOwnerId) {
		this.policyOwnerId = policyOwnerId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getRegion_id() {
		return region_id;
	}
	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String passowrd) {
		this.password = passowrd;
	}
	
	
}
