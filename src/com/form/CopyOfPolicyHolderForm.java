package com.form;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

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
import com.component.TitleCombo;
import com.component.YesOrNoRadio;
import com.data.Beneficiary;
import com.data.Policy;
import com.data.PolicyHolder;
import com.data.PolicyOwner;
import com.utility.DBUtility;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class CopyOfPolicyHolderForm extends HorizontalLayout implements View,
		ClickListener, ValueChangeListener {

	static final long serialVersionUID = 1L;
	TabSheet sheet;
	Embedded image = new Embedded("Picture");
	String picfile;
	String sigs;
	ArrayList<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
	int percentages = 0;
	
	private PicUploader picture = new PicUploader(image, "Picture");
	private Embedded signature = new Embedded("Signature");
	private PicUploader sign = new PicUploader(signature, "Signature");
	private TitleCombo title = new TitleCombo();
	private TextField lastName = new TextField("Lastname");
	TextField firstName = new TextField("First Name");
	private TextField middleName = new TextField("Middle Name");
	private TextField place = new TextField("Place of Birth");
	private DOBDateField dob = new DOBDateField("Date of Birth");
	private TelephoneField telephone = new TelephoneField("Mobile");
	private MaritalStatusCombo marital = new MaritalStatusCombo(
			"Marital Status");
	private ChildrenCombo children = new ChildrenCombo("No. of Children", 20);
	private SexCombo gender = new SexCombo("Gender");
	private TextArea address = new TextArea("Address");
	private RegionCombo region = new RegionCombo("Region");
	YesOrNoRadio checker = new YesOrNoRadio("Same as owner?");
	EmailTextBox email = new EmailTextBox("e-Mail");
	PolicyOwner owner;
	final Button addpolicyHolder = new Button("Add policyHolder");
	FormLayout bioform = new FormLayout();
	FormLayout contact = new FormLayout();
	TextField policyNumber = new TextField("Policy Number");
	CountriesCombo nationality = new CountriesCombo("Nationality");
	OccupationCombo occupation = new OccupationCombo("Occupation");
	final TextField sicknessAnswer = new TextField("Family Sickness name");
	final TextField drinkingAnswer = new TextField("Frequency of Drinking");
	final TextField hospitalAnswer = new TextField("Frequency of Smoking");
	final YesOrNoRadio drinkerQuestion = new YesOrNoRadio("Are you a drinker?",
			drinkingAnswer);
	final TextField smokingAnswer = new TextField("Frequency of Smoking");
	final YesOrNoRadio hospitalQuestion = new YesOrNoRadio(
			"Ever been Hospitalized?", hospitalAnswer);
	final YesOrNoRadio familySicknessQuestion = new YesOrNoRadio(
			"Any Family Sickness?", sicknessAnswer);
	final YesOrNoRadio smokingQuestion = new YesOrNoRadio("Are you Smoking",
			smokingAnswer);
	Policy policy = new Policy();
	PolicyHolder holder;

	public CopyOfPolicyHolderForm(TabSheet sheet, PolicyOwner owner,
			Policy policy, int insuranceType) throws ClassNotFoundException,
			SQLException {
		if (insuranceType == 4) {
			checker.setValue("Y");
			checker.setEnabled(false);
			contact.setEnabled(false);
			bioform.setEnabled(false);
			image.setVisible(false);
			sign.setVisible(false);
			picture.setVisible(false);
			sign.setVisible(false);
			firstName.setValue(owner.getFirstName());
			lastName.setValue(owner.getLastName());
			middleName.setValue(owner.getMiddleName());
			dob.setValue(DBUtility.getDate(owner.getDob()));
			gender.setValue(owner.getSex());
			title.setValue(owner.getTitle());
			marital.setValue(owner.getMaritalStatusId());
			address.setValue(owner.getAddress());
			email.setValue(owner.getEmail());
			occupation.setValue(owner.getOccupationId());
			region.setValue(owner.getRegion_id());
			place.setValue(owner.getPlaceOfBirth());
			telephone.setValue(owner.getMobile());
			children.setValue(owner.getChildren());
			sigs = owner.getSignature();
			picfile = owner.getPicture();

		}
		this.sheet = sheet;
		this.owner = owner;
		this.policy = policy;
		dob.setDateFormat("yyyy-MM-dd");
		policyNumber.setValue(policy.getPolicyNumber());
		email.addValidator(new EmailValidator("YES:"));

		smokingAnswer.setVisible(false);

		drinkingAnswer.setVisible(false);

		sicknessAnswer.setVisible(false);

		hospitalAnswer.setVisible(false);
		policyNumber.setEnabled(false);
		bioform.setEnabled(false);
		contact.setEnabled(false);
		VerticalLayout lay = new VerticalLayout();
		lay.addComponents(checker, bioform);

		addpolicyHolder.setIcon(FontAwesome.PLUS_CIRCLE);
		checker.addValueChangeListener(this);
		bioform.addComponents(policyNumber, picture, image, sign, signature,
				title, lastName, middleName, firstName, dob, gender);
		contact.addComponents(nationality, place, occupation, marital,
				children, telephone, email, address, region, addpolicyHolder);

		this.addComponents(lay, contact);
		this.setSpacing(true);
		addpolicyHolder.addClickListener(this);
	}

	// Same Owner as holder
	public CopyOfPolicyHolderForm(TabSheet sheet, PolicyOwner owner,
			Policy policy, String stat) throws ClassNotFoundException,
			SQLException {
		this.sheet = sheet;
		this.owner = owner;
		this.policy = policy;
		checker.setValue(stat);
		checker.setEnabled(false);
		dob.setDateFormat("yyyy-MM-dd");
		policyNumber.setValue(policy.getPolicyNumber());
		email.addValidator(new EmailValidator("YES:"));

		smokingAnswer.setVisible(false);

		drinkingAnswer.setVisible(false);

		sicknessAnswer.setVisible(false);

		hospitalAnswer.setVisible(false);
		policyNumber.setEnabled(false);
		bioform.setEnabled(false);
		contact.setEnabled(false);
		VerticalLayout lay = new VerticalLayout();
		lay.addComponents(checker, bioform);
		Button addpolicyHolder = new Button("Add policyHolder");
		addpolicyHolder.setIcon(FontAwesome.PLUS_CIRCLE);
		checker.addValueChangeListener(this);
		bioform.addComponents(policyNumber, picture, image, sign, signature,
				title, lastName, middleName, firstName, dob, gender);
		contact.addComponents(nationality, place, occupation, marital,
				children, telephone, email, address, region, addpolicyHolder);

		this.addComponents(lay, contact);
		this.setSpacing(true);
		addpolicyHolder.addClickListener(this);
	}

	public CopyOfPolicyHolderForm(TabSheet sheet, Policy policy)
			throws ClassNotFoundException, SQLException {
		this.sheet = sheet;
		this.owner = policy.getCustomer();
		this.policy = policy;

		dob.setDateFormat("yyyy-MM-dd");
		policyNumber.setValue(policy.getPolicyNumber());
		email.addValidator(new EmailValidator("YES:"));

		smokingAnswer.setVisible(false);

		drinkingAnswer.setVisible(false);

		sicknessAnswer.setVisible(false);

		hospitalAnswer.setVisible(false);
		policyNumber.setEnabled(false);
		bioform.setEnabled(false);
		contact.setEnabled(false);
		VerticalLayout lay = new VerticalLayout();
		lay.addComponents(checker, bioform);
	
		addpolicyHolder.setIcon(FontAwesome.PLUS_CIRCLE);
		checker.addValueChangeListener(this);
		bioform.addComponents(policyNumber, picture, image, sign, signature,
				title, lastName, middleName, firstName, dob, gender);
		contact.addComponents(nationality, place, occupation, marital,
				children, telephone, email, address, region);
		
		this.addComponents(lay, contact, addpolicyHolder);
		this.setSpacing(true);
		addpolicyHolder.addClickListener(this);
	}

	public CopyOfPolicyHolderForm(PolicyHolder holder)
			throws ClassNotFoundException, SQLException {
		FileResource source = new FileResource(new File(DBUtility.LOCATION
				+ "/Pictures/" + holder.getPicture()));
		image.setSource(source);
		image.setWidth("100px");
		image.setHeight("120px");

		FileResource signSource = new FileResource(new File(DBUtility.LOCATION
				+ "/Pictures/" + holder.getSignature()));
		signature.setSource(signSource);
		signature.setWidth("100px");
		signature.setHeight("120px");
		checker.setVisible(false);
		this.holder = holder;
		dob.setDateFormat("yyyy-MM-dd");
		policyNumber.setValue(policy.getPolicyNumber());
		firstName.setValue(holder.getFirstName());
		lastName.setValue(holder.getLastName());
		middleName.setValue(holder.getMiddleName());
		dob.setValue(DBUtility.getDate(holder.getDob()));
		gender.setValue(holder.getSex());
		title.setValue(holder.getTitle());
		marital.setValue(holder.getMaritalStatusId());
		address.setValue(holder.getAddress());
		email.setValue(holder.getEmail());
		occupation.setValue(holder.getOccupationId());
		region.setValue(holder.getRegion_id());
		place.setValue(holder.getPlaceOfBirth());
		telephone.setValue(holder.getMobile());
		children.setValue(holder.getChildren());
		familySicknessQuestion.setValue(holder.getHasFamilySickness());
		drinkerQuestion.setValue(holder.getIsDrinker());
		smokingQuestion.setValue(holder.getIsSmoker());
		hospitalQuestion.setValue(holder.getHasBeenAdmitted());
		hospitalAnswer.setValue(holder.getAdmissionReason());
		drinkingAnswer.setValue(holder.getDrinkingFrequency());
		smokingAnswer.setValue(holder.getSmokingFrequency());
		sicknessAnswer.setValue(holder.getFamilySicknessName());
		VerticalLayout lay = new VerticalLayout();
		lay.addComponents(checker, bioform);
		Button addpolicyHolder = new Button("Add policyHolder");
		addpolicyHolder.setIcon(FontAwesome.PLUS_CIRCLE);
		checker.addValueChangeListener(this);
		bioform.addComponents(image, signature, title, lastName, middleName,
				firstName, dob, gender);
		contact.addComponents(nationality, place, occupation, marital,
				children, telephone, email, address, region);

		bioform.setEnabled(false);
		contact.setEnabled(false);
		this.addComponents(lay, contact);
		this.setSpacing(true);
		addpolicyHolder.addClickListener(this);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
		PolicyHolder policyHolder = new PolicyHolder();
		policyHolder.setLastName(lastName.getValue());
		policyHolder.setMiddleName(middleName.getValue());
		policyHolder.setFirstName(firstName.getValue());
		policyHolder.setPlaceOfBirth(place.getValue());
		policyHolder.setMobile(telephone.getValue());
		policyHolder.setCountryOfBirth((String) nationality.getValue());
		policyHolder.setAddress(address.getValue());
		policyHolder.setTitle((String) title.getValue());
		policyHolder.setOccupationId((int) occupation.getValue());
		policyHolder.setEmail(email.getValue());
		policyHolder.setMaritalStatusId((int) marital.getValue());
		policyHolder.setRegion_id((String) region.getValue());
		policyHolder.setTelephone(telephone.getValue());
		policyHolder.setSex(gender.getValue().toString());
		policyHolder.setPicture(picture.fileName);
		policyHolder.setSignature(sign.fileName);
		policyHolder.setChildren((int) children.getValue());
		policyHolder.setDob(dob.getDateValue());
		policyHolder.setAge(DBUtility.getAge(dob.getDateValue()));
		policyHolder.setPolicyNumber(policyNumber.getValue());
		policyHolder.setPolicy(policy);
		addpolicyHolder.setEnabled(false);
		if (checker.getValue().toString().equals("Y")) {
			policyHolder.setSignature(sigs);
			policyHolder.setPicture(picfile);
		} else {
			policyHolder.setSignature(sign.fileName);
			policyHolder.setPicture(picture.fileName);
		}

		System.out.println(gender.getValue().toString());
		// Notification.show(,Type.ASSISTIVE_NOTIFICATION);
		if (policyHolder != null & policy != null) {

			Healthform healthForm = new Healthform(policyHolder, sheet);

			sheet.addTab(healthForm, "Medicals");
			sheet.setSelectedTab(healthForm);

		} else {

			Notification.show("No way", Type.ASSISTIVE_NOTIFICATION);
		}
		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub

		// Same as the holder
		String g = (String) checker.getValue();
		if (g.equals("Y")) {
			contact.setEnabled(false);
			bioform.setEnabled(false);
			image.setVisible(false);
			sign.setVisible(false);
			picture.setVisible(false);
			sign.setVisible(false);
			firstName.setValue(owner.getFirstName());
			lastName.setValue(owner.getLastName());
			middleName.setValue(owner.getMiddleName());
			dob.setValue(DBUtility.getDate(owner.getDob()));
			gender.setValue(owner.getSex());
			title.setValue(owner.getTitle());
			marital.setValue(owner.getMaritalStatusId());
			address.setValue(owner.getAddress());
			email.setValue(owner.getEmail());
			occupation.setValue(owner.getOccupationId());
			region.setValue(owner.getRegion_id());
			place.setValue(owner.getPlaceOfBirth());
			telephone.setValue(owner.getMobile());
			children.setValue(owner.getChildren());
			sigs = owner.getSignature();
			picfile = owner.getPicture();

		} else {
			image.setVisible(true);
			sign.setVisible(true);
			picture.setVisible(true);
			sign.setVisible(true);
			contact.setEnabled(true);
			bioform.setEnabled(true);

		}

	}
}
