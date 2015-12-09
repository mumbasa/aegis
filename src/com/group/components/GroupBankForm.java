package com.group.components;

import com.component.BanksCombo;
import com.component.ExistingBankAccount;
import com.component.RegionCombo;
import com.data.ClientBank;
import com.data.GroupPolicy;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class GroupBankForm extends VerticalLayout implements ClickListener,
		ValueChangeListener {

	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CheckBox box = new CheckBox("Use Existing Bank Information");
	ExistingBankAccount existing;
	GroupPolicy policy = new GroupPolicy();
	final BanksCombo banks = new BanksCombo("Bank");
	final TextField groupNumber = new TextField("Group Number");
	TextField accountName = new TextField("Account Name");
	final TextField accountNumber = new TextField("Account Number");
	final RegionCombo region = new RegionCombo("Region");
	final TextField branch = new TextField("Branch");
	TabSheet sheet;
	FormLayout form;

	public GroupBankForm(TabSheet sheet) {
		form = new FormLayout();
		
		existing = new ExistingBankAccount(policy.getGroupNumber());
		FormLayout exis = new FormLayout(box,existing);
		// this.policy = policy;
		// final ClientBank clientBank = new ClientBank();
		this.sheet = sheet;
		groupNumber.setValue(policy.getGroupNumber());
		groupNumber.setEnabled(false);
		box.addValueChangeListener(this);
		Button addBank = new Button("Add Bank Detail");

		form.addComponents(groupNumber, banks, accountName, accountNumber,region, branch);
	
		addBank.addClickListener(this);
		this.addComponents(exis, form, addBank);
		this.setSpacing(true);
		this.setMargin(true);
	}

	public GroupBankForm(ClientBank bank) {

		groupNumber.setValue(bank.getPolicyNumber());
		accountName.setValue(bank.getAccountName());
		accountNumber.setValue(bank.getAccountNumber());
		region.setValue(bank.getRegion());
		branch.setValue(bank.getBranch());
		groupNumber.setEnabled(false);

		Button addBank = new Button("Add Bank Detail");

		this.addComponents(groupNumber, banks, accountName, accountNumber,
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

		} else {
			Notification.show("N-o", Type.ERROR_MESSAGE);

		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		if (box.getValue()==true) {
			form.setEnabled(false);
			existing.setEnabled(true);
		} else {
			form.setEnabled(true);
			existing.setEnabled(false);
		}

	}
}