package com.form;

import java.sql.SQLException;

import tm.kod.widgets.numberfield.NumberField;

import com.component.AgentsCombo;
import com.component.CurrenciesCombo;
import com.component.PaymentFrequency;
import com.component.PaymentTypeCombo;
import com.component.PolicyTypesCombo;
import com.component.RegionCombo;
import com.data.Policy;
import com.data.PolicyOwner;
import com.data.Staff;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;

public class PolicyForm extends FormLayout implements View, ClickListener,
		BlurListener, ValueChangeListener {

	static final long serialVersionUID = 1L;
	TabSheet sheet;
	int medicalStat = 0;
	PolicyOwner customer;
	Policy policy = new Policy();
	final TextField customerNumber = new TextField("Policy Owner");
	final AgentsCombo agent = new AgentsCombo("AgentID");
	final NumberField premium = new NumberField("Premium");
	final NumberField sum = new NumberField("Initial Sum");
	final PaymentFrequency frequency = new PaymentFrequency("Payment Frequency");
	final PaymentTypeCombo paymentType = new PaymentTypeCombo("Payment Type");
	final RegionCombo region = new RegionCombo("Region");
	final PolicyTypesCombo types = new PolicyTypesCombo();
	final Button addPolicy = new Button("Add policy");
	final CurrenciesCombo currency = new CurrenciesCombo("Currency");
	CheckBox personalAccident = new CheckBox("Personal Accident");
	Staff staffs = (Staff) UI.getCurrent().getSession().getAttribute("staff");

	public PolicyForm(TabSheet sheet, PolicyOwner customer) throws SQLException {
		this.sheet = sheet;
		this.customer = customer;
		// TODO Auto-generated constructor stub
		types.addValueChangeListener(this);
		customerNumber.setValue(customer.getCustomerNumber());
		customerNumber.setEnabled(false);
		customerNumber.setVisible(false);
		sum.setDecimal(true);
		sum.validate();
		sum.addBlurListener(this);
		premium.setDecimal(true);
		premium.validate();
		premium.setImmediate(true);
		sum.addValueChangeListener(this);
		this.addComponents(customerNumber, types, currency, premium, sum,
				agent, frequency, paymentType, region, personalAccident,
				addPolicy);

		addPolicy.addClickListener(this);
		this.setStyleName("paddings");
	}

	public PolicyForm(Policy policy) throws ClassNotFoundException,
			SQLException {
		this.setEnabled(false);
		final TextField dateActivated = new TextField("ActivationDate");
		final TextField customerNumber = new TextField("Policy Owner");
		customerNumber.setValue(policy.getCustomerNumber());
		customerNumber.setEnabled(false);
		premium.setValue(policy.getPremium() + "");
		sum.setValue("" + policy.getInitialSumAssured());
		agent.setValue(policy.getAgentID());
		frequency.setValue(policy.getFrequency_id());
		types.setValue(policy.getPolicyType());
		region.setValue(policy.getRegion());
		paymentType.setValue(policy.getPayment_id());
		dateActivated.setValue(policy.getDateRegistered());
		dateActivated.setEnabled(false);
		final Button addHolder = new Button("Add Holder");
		addHolder.setVisible(false);
		addHolder.addClickListener(this);

		this.addComponents(customerNumber, dateActivated, types, premium, sum,
				agent, frequency, paymentType, region);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		CopyOfPolicyHolderForm holderForm = null;

		policy.setInitialSumAssured(sum.getValue());
		policy.setPolicyType(types.getValue().toString());
		policy.setFrequency_id(Integer
				.parseInt(frequency.getValue().toString()));

		// setting the insurance type to lead to forms and other details
		policy.setCurrency(currency.getValue().toString());
		policy.setInsuranceType(types.getInsuranceType(types.getValue()
				.toString()));
		policy.setPayment_id((Integer) paymentType.getValue());
		policy.setRegion((String) region.getValue());
		policy.setPremium(premium.getValue());
		policy.setRegion_id(region.getValue().toString());
		policy.setCustomer(customer);
		policy.setStaffID(staffs.getStaffNumber());
		if (agent.getValue() == null) {
			policy.setAgentID("");
		} else {
			policy.setAgentID(agent.getValue().toString());
		}
		int pay = (int) paymentType.getValue();

		// if client came personallly then agent is null
		if (policy.getAgentID().equals("")) {

			if (policy.getCustomer() != null) {

				try {
					holderForm = new CopyOfPolicyHolderForm(sheet, policy);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (pay == 1) {
					BankForm f = new BankForm(policy, sheet);
					this.sheet.addTab(f, "Bank Details");

					this.sheet.setSelectedTab(f);
				} else if (pay == 4) {
					SourceForm f = new SourceForm(policy, sheet);
					this.sheet.addTab(f, "Source Details");

				} else {
					this.sheet.addTab(holderForm, "Policy Holder Details");
					this.sheet.setSelectedTab(holderForm);

				}
				addPolicy.setEnabled(false);

			} else {

				Notification.show("Error", Type.ASSISTIVE_NOTIFICATION);
			}

		}
		// if an agent registered client
		else {

			if (policy.getCustomer() != null) {
				policy.setAgentID(agent.getValue().toString());

				try {
					holderForm = new CopyOfPolicyHolderForm(sheet, policy);

					if (pay == 1) {
						BankForm f = new BankForm(policy, sheet);
						this.sheet.addTab(f, "Bank Details");
						this.sheet.setSelectedTab(f);
					} else if (pay == 4) {
						SourceForm f = new SourceForm(policy, sheet);
						this.sheet.addTab(f, "Source Details");
						this.sheet.setSelectedTab(f);

					} else {
						this.sheet.addTab(holderForm, "Policy Holder Details");
						this.sheet.setSelectedTab(holderForm);

					}

				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				addPolicy.setEnabled(false);
			} else {

				Notification.show("Error", Type.ASSISTIVE_NOTIFICATION);
			}

		}

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void blur(BlurEvent event) {
		// TODO Auto-generated method stub
		if (Double.parseDouble(sum.getValue()) > 9999
				& Double.parseDouble(sum.getValue()) < 19999) {
			policy.setReinsurred(true);
			policy.setMedicalCheckRequired(false);
			Notification.show("This policy has to be reinsured for approval");

		} else if (Double.parseDouble(sum.getValue()) > 19999) {
			policy.setReinsurred(true);
			policy.setMedicalCheckRequired(true);
			Notification
					.show("A Verified Medical Report Required for Approval");

		} else {
			policy.setReinsurred(false);
			policy.setMedicalCheckRequired(false);
		}
	}

}
