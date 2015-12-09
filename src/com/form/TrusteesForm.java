package com.form;

import java.sql.SQLException;
import java.util.ArrayList;

import com.component.CountriesCombo;
import com.component.CusNotification;
import com.component.DOBDateField;
import com.component.EmailTextBox;
import com.component.RegionCombo;
import com.component.RelationshipCombo;
import com.component.SexCombo;
import com.component.TelephoneField;
import com.component.TitleCombo;
import com.data.Beneficiary;
import com.data.Policy;
import com.data.PolicyHolder;
import com.data.Trustee;
import com.utility.DBUtility;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class TrusteesForm extends HorizontalLayout implements ClickListener,ValueChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Policy policy;

	TitleCombo title = new TitleCombo();
	TextField lastName = new TextField("Lastname");
	TextField firstName = new TextField("Midddle Name");
	TextField middleName = new TextField("First Name");
	DOBDateField dob = new DOBDateField("Date of Birth");
	final RelationshipCombo relation = new RelationshipCombo("Relationship");
	SexCombo gender = new SexCombo("Gender");
	TextArea address = new TextArea("Address");
	RegionCombo region = new RegionCombo("Region");
	EmailTextBox email = new EmailTextBox("e-Mail");
	TelephoneField telephone = new TelephoneField("Mobile");
	CountriesCombo nationality = new CountriesCombo("Nationality");
	ArrayList<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
	PolicyHolder holder;
	public TrusteesForm(Policy policy, ArrayList<Beneficiary> beneficiaries)
			throws ClassNotFoundException, SQLException {
		// TODO Auto-generated constructor stub
		final TextField policyNumber = new TextField("Policy Number");
		this.policy = policy;
		policyNumber.setValue(policy.getPolicyNumber());
		policyNumber.setEnabled(false);
		dob.setDateFormat("yyyy-MM-dd");

		email.addValidator(new EmailValidator("YES:"));

		gender.setMultiSelect(false);

		FormLayout biofrom = new FormLayout();
		FormLayout contact = new FormLayout();

		Button addCustomer = new Button("Add Trustee");
		addCustomer.addClickListener(this);
		addCustomer.setIcon(FontAwesome.PLUS_CIRCLE);
		biofrom.addComponents(policyNumber, title, lastName, middleName,
				firstName, dob, gender);
		contact.addComponents(nationality, relation, telephone, email, address,
				region, addCustomer);
		this.addComponents(biofrom, contact);
		this.setSpacing(true);
	}

	
	
	
	
	public TrusteesForm(PolicyHolder holder)
			throws ClassNotFoundException, SQLException {
		// TODO Auto-generated constructor stub
		final TextField policyNumber = new TextField("Policy Number");
		this.holder = holder;
		this.policy = holder.getPolicy();
	
		policyNumber.setValue(policy.getPolicyNumber());
		policyNumber.setEnabled(false);
		dob.setDateFormat("yyyy-MM-dd");
		dob.addValueChangeListener(this);
		email.addValidator(new EmailValidator("YES:"));

		gender.setMultiSelect(false);

		FormLayout biofrom = new FormLayout();
		FormLayout contact = new FormLayout();

		Button addCustomer = new Button("Add Trustee");
		addCustomer.addClickListener(this);
		addCustomer.setIcon(FontAwesome.PLUS_CIRCLE);
		biofrom.addComponents(title, lastName, middleName,
				firstName, dob, gender);
		contact.addComponents(nationality, relation, telephone, email, address,
				region, addCustomer);
		this.addComponents(biofrom, contact);
		this.setSpacing(true);
	}

	
	
	
	
	
	
	
	
	public TrusteesForm(Trustee trustee)throws ClassNotFoundException, SQLException {
		lastName.setValue(trustee.getLastName());
		firstName.setValue(trustee.getFirstName());
		middleName.setValue(trustee.getMiddleName());
		telephone.setValue(trustee.getMobile());
		address.setValue(trustee.getAddress());
		dob.setValue(DBUtility.getDate(trustee.getDob()));
		email.setValue(trustee.getEmail());
		
		dob.setDateFormat("yyyy-MM-dd");

		email.addValidator(new EmailValidator("YES:"));

		gender.setMultiSelect(false);

		FormLayout biofrom = new FormLayout();
		FormLayout contact = new FormLayout();

		Button addCustomer = new Button("Add Trustee");
		addCustomer.addClickListener(this);
		addCustomer.setIcon(FontAwesome.PLUS_CIRCLE);
		biofrom.addComponents(title, lastName, middleName,
				firstName, dob, gender);
		contact.addComponents(nationality, relation, telephone, email, address,
				region);
		this.addComponents(biofrom, contact);
		this.setSpacing(true);
	}
	
	
	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		Trustee trustee = new Trustee();
		trustee.setLastName(lastName.getValue());
		trustee.setMiddleName(middleName.getValue());
		trustee.setFirstName(firstName.getValue());
		trustee.setDob(dob.getDateValue());
		trustee.setAddress(address.getValue());
		trustee.setMobile(telephone.getValue());
		trustee.setEmail(email.getValue());
		trustee.setRegion(region.getValue().toString());
		trustee.setRelationship_id((int) relation.getValue());
		trustee.setTitle(title.getValue().toString());
		trustee.setSex(gender.getValue().toString());
		holder.setTrustee(trustee);
		String polNumber = DBUtility.insertPolicyOwner1(holder);
		if (polNumber != null) {
			if (holder.getPolicy().isMedicalCheckRequired()==true){
				CusNotification.getSuccess(polNumber +" Policy Added <br> Medical Report has to be verified for final Approval");
			}
			else{
				CusNotification.getSuccess(polNumber +" Policy Added");

			}
			
		
			UI.getCurrent().getNavigator().navigateTo("Existing Customer");
		} else {
			Notification.show("Policy Not Added due to Erro", Type.ERROR_MESSAGE);
		}
	}





	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		int age = DBUtility.getAge(dob.getDateValue());
		if (age<18){
			Notification.show("Trustee Must be more than 18 years", Type.WARNING_MESSAGE);
			dob.clear();
			dob.focus();
		}
		
	}

}
