/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.data;
import java.io.Serializable;
/**
 *
 * @author Bryanic
 */
public class Source implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String policyNumber;
    private String staffNumber;
    private int companyID;
    private String branch;
    private String region;
    private String customerNumber;
    private Policy policy;
    
    public String getPolicyNumber() {
        return this.policyNumber;
    }

    public String getStaffNumber() {
        return this.staffNumber;
    }

    public String getBranch() {
        return this.branch;
    }

    public String getRegion() {
        return this.region;
    }
    
    public int getCompanyID() {

        return this.companyID;
    }
    
    
    public void setPolicyNumber(String p) {
  this.policyNumber =p;
    }

    public  void setStaffNumber(String s) {
   this.staffNumber=s;
    }


    public void setBranch(String bp) {
  this.branch =bp;
    }

    public  void setRegion(String s) {
   this.region=s;
    }

    
    public  void setCompanyID(String c) {
    int companyId = Integer.parseInt(c);
    this.companyID =companyId ;
    }
    
    
    public  void setCompanyID(int c) {

        this.companyID =c ;
        }
    

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}
    
    
}
