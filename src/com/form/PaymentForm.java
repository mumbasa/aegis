package com.form;

import java.sql.SQLException;

import tm.kod.widgets.numberfield.NumberField;

import com.component.BanksCombo;
import com.component.DOBDateField;
import com.component.PaymentModeCombo;
import com.component.PolicyNumbersCombo;
import com.component.PolicyTypesCombo;
import com.component.YesOrNo;
import com.data.PolicyPayment;
import com.data.Staff;
import com.utility.DBUtility;
import com.utility.PaymentDBUtil;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.TextField;

public class PaymentForm extends FormLayout implements ClickListener,
		ValueChangeListener {

	static final long serialVersionUID = 1L;
	PolicyNumbersCombo policyNumber;
	YesOrNo yesNo = new YesOrNo("Is Active");
	final PaymentModeCombo paymentType = new PaymentModeCombo("Payment Type");
	final TextField branch = new TextField("Branch");
	final TextField staff = new TextField("Staff ID");
	final NumberField amount = new NumberField("Premium");
	final TextField payee = new TextField("Payee");
	final TextField checknumber = new TextField("CheckNunber");
	DOBDateField datePaid = new DOBDateField("Date Paid");
	BanksCombo bank = new BanksCombo("Bank");
	PolicyTypesCombo typesCombo;

	public PaymentForm() throws ClassNotFoundException, SQLException {
		this.setStyleName("paddings");
		amount.setDecimal(true);
		amount.setValidationVisible(true);
		// policyNumber.addValueChangeListener(this);
		Staff staffs = (Staff) UI.getCurrent().getSession()
				.getAttribute("staff");
		staff.setValue(staffs.getStaffNumber());
		staff.setEnabled(false);
		checknumber.setVisible(false);
		bank.setVisible(false);
		branch.setValue("" + staffs.getBranch_id());
		branch.setEnabled(false);
		policyNumber.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				yesNo.setValue(policyNumber.getActivity());
			}
		});
		final Button button = new Button("Pay");
		paymentType.addValueChangeListener(this);
		button.addClickListener(this);

		this.addComponents(policyNumber, yesNo, paymentType, checknumber, bank,
				amount, staff, branch, payee, button);
		yesNo.setEnabled(false);

		this.setMargin(false);
	}

	public PaymentForm(PolicyNumbersCombo b) throws ClassNotFoundException,
			SQLException {
		this.policyNumber = b;
		this.setStyleName("paddings");
		amount.setDecimal(true);
		// policyNumber.addValueChangeListener(this);
		Staff staffs = (Staff) UI.getCurrent().getSession()
				.getAttribute("staff");
		staff.setValue(staffs.getStaffNumber());
		staff.setEnabled(false);
		checknumber.setVisible(false);
		bank.setVisible(false);
		branch.setValue("" + staffs.getBranch_id());
		branch.setEnabled(false);
		datePaid.setValue(DBUtility.NOW);
		final Button button = new Button("Pay");
		paymentType.addValueChangeListener(this);
		button.addClickListener(this);
		typesCombo = new PolicyTypesCombo(b.getValue().toString(), 1);
		this.addComponents(typesCombo, yesNo, paymentType, checknumber, bank,
				amount, staff, branch, payee,datePaid, button);
		yesNo.setEnabled(false);
		yesNo.setValue(b.getActivity());

		this.setMargin(false);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		PolicyPayment payments = new PolicyPayment();
		payments.setAmount(amount.getValue());
		payments.setChequeNumber(checknumber.getValue());
		payments.setBranchID(branch.getValue());
		payments.setPayee(payee.getValue());
		payments.setStaffID(staff.getValue());
		payments.setDatePaid(datePaid.getDateValue());
		payments.setPaymentModeId((int) paymentType.getValue());
		payments.setPolicyNumber(policyNumber.getValue().toString());
		payments.setPolicyType(typesCombo.getItemCaption((int) typesCombo.getValue()));
		payments.setPolicyParentType((int) typesCombo.getValue());

		if (checknumber.isVisible()==true) {
			payments.setChequeNumber(checknumber.getValue());
			payments.setBank_id((int) bank.getValue());
			if (PaymentDBUtil.insertPolicyChequePayment(payments) == 1) {

				Notification.show("Paid Successfully");
				UI.getCurrent().getNavigator().navigateTo("Policy Payment");
			} else {

				Notification.show("Paid UnSuccessfully", Type.ERROR_MESSAGE);
			}

		} else {
			if (payments.getPolicyParentType() == 3) {
				Notification.show("patent :" + payments.getPolicyNumber());
				// if policy not Activated
				if (yesNo.getValue().toString().equals("N")) {

					if (DBUtility.activatePolicy(policyNumber.getValue()
							.toString(), payments.getDatePaid()) == 1) {
						Notification.show(policyNumber.getValue().toString()
								+ " activated", Type.TRAY_NOTIFICATION);

					if (PaymentDBUtil.insertPolicyProductPayment2(payments) == 1) {
							Notification.show("Payment Success");
							UI.getCurrent().getNavigator().navigateTo("Search Policy");
						} else {
							Notification.show("Payment UnSuccessful",
									Type.ERROR_MESSAGE);

						}
					} else {
						Notification.show("Activation UnSuccessful",
								Type.ERROR_MESSAGE);

					}

				} else {

					if (PaymentDBUtil.insertPolicyProductPayment2(payments) == 1) {
						Notification.show("Payment Success");

					} else {
						Notification.show("Payment UnSuccessful",
								Type.ERROR_MESSAGE);

					}

				}
			}

			else {

				Notification.show("No payment Info", Type.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		// getting the component calling the valuechange interface

		if ((int) event.getProperty().getValue() == 1) {
			checknumber.setVisible(true);
			bank.setVisible(true);

		} else {
			checknumber.setVisible(false);
			bank.setVisible(false);
		}

	}

}
