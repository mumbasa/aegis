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
public class ClientBank implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int bankID;
    private String policyNumber;
    private String accountName;
    private String branch;
    private String region;
    private String accountNumber;
 
    public int getBankID() {
        return bankID;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getBranch() {
        return branch;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String r) {
        this.region = r;
    }

    public void setBranch(String b) {
        this.branch = b;
    }

    public void setPolicyNumber(String p) {
        this.policyNumber = p;
    }

    public void setAccountName(String a) {
        this.accountName = a;
    }
    public void setAccountNumber(String a) {
        this.accountNumber = a;
    }

    public void setBankID(String a) {
        int as = Integer.parseInt(a);
        this.bankID = as;
    }
    
    public void setBankID(int a) {

        this.bankID = a;
    }
}
