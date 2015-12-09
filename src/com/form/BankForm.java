package com.form;

import java.sql.SQLException;

import com.component.BanksCombo;
import com.component.RegionCombo;
import com.data.ClientBank;
import com.data.Policy;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;

public class BankForm extends FormLayout implements ClickListener {

	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Policy policy = new Policy();
	final BanksCombo banks = new BanksCombo("Bank");
	final TextField policyNumber = new TextField("Policy Number");
	TextField accountName = new TextField("Account Name");
	final TextField accountNumber = new TextField("Account Number");
	final RegionCombo region = new RegionCombo("Region");
	final TextField branch = new TextField("Branch");
	TabSheet sheet;
//policy to append the client bank carried from the second form
	public BankForm(Policy policy, TabSheet sheet) {
		this.policy = policy;
		// final ClientBank clientBank = new ClientBank();
		this.sheet = sheet;
		policyNumber.setValue(policy.getPolicyNumber());
		policyNumber.setEnabled(false);

		Button addBank = new Button("Add Bank Detail");

		this.addComponents(policyNumber, banks, accountName, accountNumber,
				region, branch, addBank);
		addBank.addClickListener(this);
	}

	public BankForm(ClientBank bank) {

		policyNumber.setValue(bank.getPolicyNumber());
		accountName.setValue(bank.getAccountName());
		accountNumber.setValue(bank.getAccountNumber());
		region.setValue(bank.getRegion());
		branch.setValue(bank.getBranch());
		policyNumber.setEnabled(false);

		Button addBank = new Button("Add Bank Detail");

		this.addComponents(policyNumber, banks, accountName, accountNumber,
				region, branch);
		addBank.addClickListener(this);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		ClientBank clientBank = new ClientBank();
		clientBank.setAccountName(accountName.getValue());
		clientBank.setAccountNumber(accountNumber.getValue());
		clientBank.setBranch(branch.getValue());
		clientBank.setRegion(region.getValue().toString());
		clientBank.setBankID(banks.getValue().toString());
		policy.setBank(clientBank);
		if (clientBank != null & policy != null) {
			
			try {
				CopyOfPolicyHolderForm holderForm = new CopyOfPolicyHolderForm(sheet, policy);	
				this.sheet.addTab(holderForm,"Policy Holder Details");
			this.sheet.setSelectedTab(holderForm);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

		} else {
			Notification.show("N-o", Type.ERROR_MESSAGE);

		}
	}
}