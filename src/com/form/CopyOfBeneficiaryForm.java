package com.form;

import java.sql.SQLException;
import java.util.ArrayList;

import com.component.CountriesCombo;
import com.component.DOBDateField;
import com.component.EmailTextBox;
import com.component.SexCombo;
import com.component.TelephoneField;
import com.component.TitleCombo;
import com.data.Beneficiary;
import com.data.Policy;
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
import com.vaadin.ui.Slider;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class CopyOfBeneficiaryForm extends HorizontalLayout implements ClickListener,ValueChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
	int pecent = 0;
	Button up = new Button("Add ");
	final TextField policyNumber = new TextField("Policy Number");
	TitleCombo title = new TitleCombo();
	TextField lastName = new TextField("Lastname");
	TextField firstName = new TextField("Midddle Name");
	TextField middleName = new TextField("First Name");
	DOBDateField dob = new DOBDateField("Date of Birth");
	EmailTextBox email = new EmailTextBox("e-Mail");
	TelephoneField telephone = new TelephoneField("Mobile");
	//final RelationshipCombo relation = new RelationshipCombo("Relationship");
	SexCombo gender = new SexCombo("Gender");
	TextArea address = new TextArea("Address");
	//RegionCombo region = new RegionCombo("Region");
	CountriesCombo nationality = new CountriesCombo("Nationality");
	Slider portion = new Slider("Portion");
	TabSheet sheet = new TabSheet();
	TextField field = new TextField("Portions");
	public CopyOfBeneficiaryForm(Policy policy,TabSheet sheet) throws ClassNotFoundException,
			SQLException {
		// TODO Auto-generated constructor stub

		policyNumber.setEnabled(false);
		policyNumber.setValue(policy.getPolicyNumber());
		

		dob.setDateFormat("yyyy-MM-dd");

		email.addValidator(new EmailValidator("YES:"));

		gender.setMultiSelect(false);

		portion.setMax(100.00);
		portion.setWidth("200px");
		FormLayout biofrom = new FormLayout();
		FormLayout contact = new FormLayout();

		Button addCustomer = new Button("Add Beneficiary");
		addCustomer.setIcon(FontAwesome.PLUS_CIRCLE);
		biofrom.addComponents(policyNumber,portion, title, lastName,
				middleName, firstName, dob, gender);
		contact.addComponents(nationality, telephone, email, address,
				addCustomer);
		this.addComponents(biofrom, contact);
		this.setSpacing(true);
		addCustomer.addClickListener(this);
	}

	
	public CopyOfBeneficiaryForm(){
// TODO Auto-generated constructor stub
field.setValue(""+pecent);
policyNumber.setEnabled(false);
//policyNumber.setValue(policy.getPolicyNumber());

dob.setDateFormat("yyyy-MM-dd");

email.addValidator(new EmailValidator("YES:"));

gender.setMultiSelect(false);

portion.setMax(100.00);
portion.setWidth("200px");
FormLayout biofrom = new FormLayout();
FormLayout contact = new FormLayout();

Button addCustomer = new Button("Add Beneficiary");

addCustomer.setIcon(FontAwesome.PLUS_CIRCLE);
biofrom.addComponents(policyNumber,field, portion, title, lastName,
		middleName, firstName, dob, gender);
contact.addComponents(nationality, telephone, email, address,
		 up,addCustomer);
up.setVisible(false);
this.addComponents(biofrom, contact);
this.setSpacing(true);
addCustomer.addClickListener(this);
sheet.addTab(this,"Beneficiary");
portion.addValueChangeListener(this);
}

	
	
	
	
	
	
	
	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		if (pecent<100){
		pecent+=portion.getValue();
		Beneficiary beneficiary = new Beneficiary();
		beneficiary.setLastName(lastName.getValue());
		beneficiary.setMiddleName(middleName.getValue());
		beneficiary.setFirstName(firstName.getValue());
		beneficiary.setDob(dob.getDateValue());
		beneficiary.setCountryOfBirth(nationality.getValue().toString());
		beneficiary.setAddress(address.getValue());
		beneficiary.setAge(DBUtility.getAge(dob.getDateValue()));
		beneficiary.setSex(gender.getValue().toString());
		beneficiary.setMobile(telephone.getValue());
		beneficiary.setEmail(email.getValue());
		beneficiary.setTitle(title.getValue().toString());
		beneficiary.setPercentage(portion.getValue());
		beneficiaries.add(beneficiary);
		portion.setMax(100-pecent);
		sheet.addTab(new CopyOfBeneficiaryForm(),"Haja");
		}
		else if(pecent==100){
			event.getButton().setEnabled(false);
		
		}
		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
	if (portion.getValue()==0){
			up.setVisible(true);
		
	}
	}

}
