package com.form;

import java.io.File;
import java.sql.SQLException;

import com.component.ChildrenCombo;
import com.component.CountriesCombo;
import com.component.DOBDateField;
import com.component.EmailTextBox;
import com.component.MaritalStatusCombo;
import com.component.OccupationCombo;
import com.component.PicUploader;
import com.component.RegionCombo;
import com.component.SexCombo;
import com.component.TelephoneField;
import com.component.TextFieldCus;
import com.component.TitleCombo;
import com.data.PolicyOwner;
import com.utility.DBUtility;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class CustomerForm extends HorizontalLayout implements ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Embedded image = new Embedded("Picture");
	PicUploader picture = new PicUploader(image, "Picture");
	Embedded signature = new Embedded("Signature/Thumbprint");
	PicUploader sign = new PicUploader(signature, "Signature/Thumbprint");
	TitleCombo title = new TitleCombo();
	TextFieldCus lastName = new TextFieldCus("Lastname");
	TextFieldCus firstName = new TextFieldCus("First Name");
	TextField middleName = new TextField("Middle Name");
	TextFieldCus place = new TextFieldCus("Place of Birth");
	DOBDateField dob = new DOBDateField("Date of Birth");
	TelephoneField telephone = new TelephoneField("Mobile");
	MaritalStatusCombo marital = new MaritalStatusCombo("Marital Status");
	ChildrenCombo children = new ChildrenCombo("No. of Children", 20);
	SexCombo gender = new SexCombo("Gender");
	TextArea address = new TextArea("Address");
	RegionCombo region = new RegionCombo("Region");
	CountriesCombo nationality = new CountriesCombo("Nationality");
	OccupationCombo occupation = new OccupationCombo("Occupation");
	EmailTextBox email = new EmailTextBox("e-Mail");
	TabSheet sheet;
	Button addCustomer = new Button("Add Customer");

	public CustomerForm(TabSheet sheet) throws ClassNotFoundException,
			SQLException {
		// TODO Auto-generated constructor stub
		this.sheet = sheet;
		dob.setDateFormat("yyyy-MM-dd");

		email.addValidator(new EmailValidator("YES:"));

		gender.setMultiSelect(false);

		FormLayout biofrom = new FormLayout();
		FormLayout contact = new FormLayout();

		addCustomer.setIcon(FontAwesome.PLUS_CIRCLE);
		biofrom.addComponents(picture, image, sign, signature, title, lastName,
				middleName, firstName, dob, gender);
		biofrom.setStyleName("formleft");
		contact.addComponents(nationality, place, occupation, marital,
				children, telephone, email, address, region, addCustomer);
		this.addComponents(biofrom, contact);
		this.setSpacing(true);
		addCustomer.addClickListener(this);
	}

	public CustomerForm(PolicyOwner owner) throws ClassNotFoundException,
			SQLException {
		// TODO Auto-generated constructor stub
		FileResource source = new FileResource(new File(DBUtility.LOCATION+"/Pictures/"+owner.getPicture()));
		image.setSource(source);
		image.setWidth("100px");
		image.setHeight("120px");
		
		FileResource signSource = new FileResource(new File(DBUtility.LOCATION+"/Pictures/"+owner.getSignature()));
		signature.setSource(signSource);
		signature.setWidth("100px");
		signature.setHeight("120px");
		
		lastName.setValue(owner.getLastName());
		firstName.setValue(owner.getFirstName());
		middleName.setValue(owner.getMiddleName());
		title.setValue(owner.getTitle());
		gender.setValue(owner.getSex());
		place.setValue(owner.getPlaceOfBirth());
		address.setValue(owner.getAddress());
	//	this.sheet = sheet;
		dob.setValue(DBUtility.getDate(owner.getDob()));
		children.setValue(owner.getChildren());
		email.setValue(owner.getEmail());
		occupation.setValue(owner.getOccupationId());
		region.setValue(owner.getRegion_id());
		marital.setValue(owner.getMaritalStatusId());
		nationality.setValue(owner.getCountryOfBirth());
		telephone.setValue(owner.getMobile());;

		email.addValidator(new EmailValidator("YES:"));
	
		

		FormLayout biofrom = new FormLayout();
		FormLayout contact = new FormLayout();

		addCustomer.setIcon(FontAwesome.PLUS_CIRCLE);
		biofrom.addComponents(image,signature, title, lastName,
				middleName, firstName, dob, gender);
		contact.addComponents(nationality, place, occupation, marital,
				children, telephone, email, address, region);
		biofrom.setEnabled(false);
		contact.setEnabled(false);
		this.addComponents(biofrom, contact);
		this.setSpacing(true);
		addCustomer.addClickListener(this);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stubc
		PolicyOwner customer = new PolicyOwner();
		customer.setLastName(lastName.getValue());
		customer.setMiddleName(middleName.getValue());
		customer.setFirstName(firstName.getValue());
		customer.setPlaceOfBirth(place.getValue());
		customer.setMobile(telephone.getValue());
		customer.setCountryOfBirth((String) nationality.getValue());
		customer.setAddress(address.getValue());
		customer.setTitle((String) title.getValue());
		customer.setOccupationId((int) occupation.getValue());
		customer.setEmail(email.getValue());
		customer.setMaritalStatusId((int) marital.getValue());
		customer.setRegion_id((String) region.getValue());
		customer.setTelephone(telephone.getValue());
		
		customer.setPicture(picture.fileName);
		customer.setSignature(sign.fileName);
		customer.setChildren((int) children.getValue());
		customer.setDob(dob.getDateValue());
		customer.setAge(DBUtility.getAge(dob.getDateValue()));
		customer.setDate(dob.getValue());
		customer.setSex((String) gender.getValue());

		if (customer != null) {
	
			
			try { 
				
				PolicyForm	policyForm = new PolicyForm(sheet, customer);
				sheet.addTab(policyForm, "Policy Details");
				sheet.setSelectedTab(policyForm);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
//		addCustomer.setEnabled(false);
	}
}
