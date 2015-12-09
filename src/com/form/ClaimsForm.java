package com.form;

import java.sql.SQLException;

import tm.kod.widgets.numberfield.NumberField;

import com.component.ClaimsTypeCombo;
import com.component.DOBDateField;
import com.component.PolicyNumbersCombo;
import com.data.Claim;
import com.data.Staff;
import com.utility.DBUtility;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class ClaimsForm extends FormLayout implements ClickListener,
		ValueChangeListener {

	static final long serialVersionUID = 1L;

	final PolicyNumbersCombo policyNumber = new PolicyNumbersCombo(
			"Policy Number","Claims");
	final ClaimsTypeCombo claimType = new ClaimsTypeCombo("Claim Type");
	final TextField branch = new TextField("Branch");
	final TextField staff = new TextField("Staff ID");
	final NumberField amount = new NumberField("Amount Filed");
	final TextField payee = new TextField("Requested By");
	final Button button = new Button("Process Claim");
	DOBDateField dateApplied = new DOBDateField("Application Date");

	public ClaimsForm() throws ClassNotFoundException, SQLException {
		dateApplied.setValue(DBUtility.NOW);
		Staff staffs = (Staff) UI.getCurrent().getSession()
				.getAttribute("staff");
		
		staff.setValue(staffs.getStaffNumber());
		staff.setEnabled(false);
		amount.setDecimal(true);
		amount.setInvalidAllowed(false);
		branch.setValue(staffs.getBranch_id() + "");
		this.addComponents(claimType, policyNumber, amount, dateApplied, staff,
				branch, payee, button);
		this.setMargin(false);
		this.setStyleName("paddings");
		button.setEnabled(false);
		button.addClickListener(this);
		policyNumber.addValueChangeListener(this);
		claimType.addValueChangeListener(this);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		Claim claim = new Claim();
		claim.setPolicyNumber(policyNumber.getValue().toString());
		claim.setAmount(Double.parseDouble(amount.getValue()));
		claim.setDateApplied(dateApplied.getDateValue());
		claim.setStaffNumber(staff.getValue());
		claim.setBranchID(branch.getValue());
		claim.setClaimTypeId((int) claimType.getValue());
		if (DBUtility.insertPolicyClaim(claim) == 1) {
			Notification.show("Claim Applied");
			UI.getCurrent().getNavigator().navigateTo("Claims Processing");
		} else {

			Notification.show("Claim Applied", Type.ERROR_MESSAGE);

		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		if (policyNumber.getValue() == null | claimType.getValue() == null) {
			button.setEnabled(false);

		} else {
			button.setEnabled(true);
		}
	}

}
