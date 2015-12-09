/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Bryanic
 */
public class PolicyPayment implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double amount;
    private String policyNumber;
    private String chequeNumber;
    private String payee;
    private String datePaid;
    private String staffID;
    private String branchID;
    private String paymentType;
    private String policyType;
    private int  policyParentType;
    private String cleared;
    private String clearedDate;
    private String status;
    public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public int getPolicyParentType() {
		return policyParentType;
	}

	public void setPolicyParentType(int policyParentType) {
		this.policyParentType = policyParentType;
	}

	
    
    private int bank_id;
    public int getPaymentModeId() {
		return paymentModeId;
	}

	public void setPaymentModeId(int paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	private int paymentModeId;
	private int payID;
    public PolicyPayment(int id) {
    	this.payID = id;

    }

    
    public PolicyPayment() {
  

    }

    
    public double getAmount() {
        return amount;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public String getPayee() {
        return payee;
    }

    public String getDatePaid() {
        return datePaid;
    }

    public String getStaffID() {
        return staffID;
    }

    public String getBranchID() {
        return branchID;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setChequeNumber(String c) {
        this.chequeNumber = c;
    }

    public void setStaffID(String s) {
        this.staffID = s;
    }

    public void setBranchID(String b) {
        this.branchID = b;
    }

    public void setPolicyNumber(String policy) {
        this.policyNumber = policy;
    }

    public void setDatePaid() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        this.datePaid = date;

    }

    public void setDatePaid(String s) {
      
        this.datePaid = s;

    }   
    
    
    public void setPayee(String p) {
        this.payee = p;
    }

    public void setPaymnentType(String p) {
        this.paymentType = p;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setAmount(String amount) {
        double amountPaid = Double.parseDouble(amount);
        this.amount = amountPaid;
    }

	public int getBank_id() {
		return bank_id;
	}

	public void setBank_id(int bank_id) {
		this.bank_id = bank_id;
	}

	public String getCleared() {
		return cleared;
	}

	public void setCleared(String cleared) {
		this.cleared = cleared;
	}

	public String getClearedDate() {
		return clearedDate;
	}

	public void setClearedDate(String clearedDAte) {
		this.clearedDate = clearedDAte;
	}

	public int getPayID() {
		return payID;
	}

	public void setPayID(int payID) {
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
