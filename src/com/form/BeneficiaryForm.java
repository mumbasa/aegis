package com.form;

import java.sql.SQLException;
import java.util.ArrayList;

import com.component.CountriesCombo;
import com.component.DOBDateField;
import com.component.EmailTextBox;
import com.component.RegionCombo;
import com.component.RelationshipCombo;
import com.component.SexCombo;
import com.component.CusNotification;
import com.component.TelephoneField;
import com.component.TitleCombo;
import com.data.Beneficiary;
import com.data.Policy;
import com.data.PolicyHolder;
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
import com.vaadin.ui.Slider;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class BeneficiaryForm extends HorizontalLayout implements ClickListener,
		ValueChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int totalPercent = 0;
	int total = 0;

	private Button up = new Button("Add ");
	private final TextField policyNumber = new TextField("Policy Number");
	private TitleCombo title = new TitleCombo();
	private TextField lastName = new TextField("Lastname");
	private TextField firstName = new TextField("Midddle Name");
	private TextField middleName = new TextField("First Name");
	private DOBDateField dob = new DOBDateField("Date of Birth");
	private EmailTextBox email = new EmailTextBox("e-Mail");
	private TelephoneField telephone = new TelephoneField("Mobile");
	final RelationshipCombo relation = new RelationshipCombo("Relationship");
	private RegionCombo region = new RegionCombo("Region");
	private SexCombo gender = new SexCombo("Gender");
	private TextArea address = new TextArea("Address");
	private CountriesCombo nationality = new CountriesCombo("Nationality");
	private Slider portion = new Slider("Portion");
	private TabSheet sheet;
	private PolicyHolder holder;

	public BeneficiaryForm(Policy policy, TabSheet sheet,
			ArrayList<Beneficiary> beneficiaries)
			throws ClassNotFoundException, SQLException {
		policyNumber.setEnabled(false);
		policyNumber.setValue(policy.getPolicyNumber());

		email.addValidator(new EmailValidator("YES:"));

		gender.setMultiSelect(false);

		portion.setMax(100.00 - totalPercent);
		portion.setWidth("200px");
		FormLayout biofrom = new FormLayout();
		FormLayout contact = new FormLayout();

		Button addCustomer = new Button("Add Beneficiary");
		addCustomer.setIcon(FontAwesome.PLUS_CIRCLE);
		biofrom.addComponents(policyNumber, portion, title, lastName,
				middleName, firstName, dob, gender);
		contact.addComponents(nationality, telephone, email, address,
				addCustomer);
		this.addComponents(biofrom, contact);

		addCustomer.addClickListener(this);
	}

	public BeneficiaryForm(PolicyHolder holder, TabSheet sheet) {
		// TODO Auto-generated constructor stub
		this.holder = holder;
		this.sheet = sheet;
		// beneficiaries =holder.getBeneficiaries();
		// this.beneficiaries = holder.getBeneficiaries();
		email.addValidator(new EmailValidator("YES:"));

		gender.setMultiSelect(false);

		portion.setMax(100.00 - totalPercent);
		portion.setWidth("200px");
		FormLayout biofrom = new FormLayout();
		FormLayout contact = new FormLayout();

		Button addCustomer = new Button("Add Beneficiary");
		addCustomer.setIcon(FontAwesome.PLUS_CIRCLE);
		biofrom.addComponents(portion, title, lastName, middleName, firstName,
				dob, gender, relation);
		contact.addComponents(nationality, telephone, email, address,
				addCustomer);
		this.addComponents(biofrom, contact);

		addCustomer.addClickListener(this);
	}

	public BeneficiaryForm(PolicyHolder holder, TabSheet sheet, int totalPercent) {
		// TODO Auto-generated constructor stub
		this.sheet = sheet;
		this.holder = holder;
		// this.beneficiaries = holder.getBeneficiaries();
		// TODO Auto-generated constructor stub
		this.totalPercent = totalPercent;
		// beneficiaries =holder.getBeneficiaries();

		dob.setDateFormat("yyyy-MM-dd");

		email.addValidator(new EmailValidator("YES:"));

		gender.setMultiSelect(false);

		portion.setMax(100.00 - totalPercent);
		portion.setValue(portion.getMax());
		portion.setWidth("200px");
		FormLayout biofrom = new FormLayout();
		FormLayout contact = new FormLayout();

		Button addCustomer = new Button("Add Beneficiary");

		addCustomer.setIcon(FontAwesome.PLUS_CIRCLE);
		biofrom.addComponents(portion, title, lastName, middleName, firstName,
				dob, gender, relation);
		contact.addComponents(nationality, region, telephone, email, address,
				up, addCustomer);
		up.setVisible(false);
		this.addComponents(biofrom, contact);
		this.setSpacing(true);
		addCustomer.addClickListener(this);
		portion.addValueChangeListener(this);
	}

	public BeneficiaryForm(Policy policy, TabSheet sheet,
			ArrayList<Beneficiary> beneficiaries, int totalPercent) {
		this.sheet = sheet;
		// TODO Auto-generated constructor stub
		this.totalPercent = totalPercent;
		policyNumber.setEnabled(false);
		policyNumber.setValue(policy.getPolicyNumber());

		dob.setDateFormat("yyyy-MM-dd");

		email.addValidator(new EmailValidator("YES:"));

		gender.setMultiSelect(false);

		portion.setMax(100.00 - totalPercent);
		portion.setValue(portion.getMax());
		portion.setWidth("200px");
		FormLayout biofrom = new FormLayout();
		FormLayout contact = new FormLayout();

		Button addCustomer = new Button("Add Beneficiary");

		addCustomer.setIcon(FontAwesome.PLUS_CIRCLE);
		biofrom.addComponents(policyNumber, portion, title, lastName, relation,
				middleName, firstName, dob, gender);
		contact.addComponents(nationality, region, telephone, email, address,
				up, addCustomer);
		up.setVisible(false);
		this.addComponents(biofrom, contact);
		this.setSpacing(true);
		addCustomer.addClickListener(this);
		portion.addValueChangeListener(this);
	}

	public BeneficiaryForm(Beneficiary beneficiary) {

		policyNumber.setEnabled(false);
		policyNumber.setValue(beneficiary.getPolicyNumber());
		lastName.setValue(beneficiary.getLastName());
		firstName.setValue(beneficiary.getLastName());
		middleName.setValue(beneficiary.getMiddleName());
		gender.setValue(beneficiary.getSex());
		portion.setValue(beneficiary.getPercentage());

		telephone.setValue(beneficiary.getTelephone());
		region.setValue(beneficiary.getRegion_id());
		address.setValue(beneficiary.getAddress());
		nationality.setValue(beneficiary.getCountryOfBirth());
		dob.setValue(DBUtility.getDate(beneficiary.getDob()));
		dob.setDateFormat("yyyy-MM-dd");
		email.setValue(beneficiary.getEmail());
		relation.setValue(beneficiary.getRelationship_id());
		title.setValue(beneficiary.getTitle());
		email.addValidator(new EmailValidator("YES:"));

		portion.setWidth("200px");

		FormLayout biofrom = new FormLayout();
		FormLayout contact = new FormLayout();
		biofrom.setEnabled(false);
		contact.setEnabled(false);
		Button addCustomer = new Button("Add Beneficiary");

		addCustomer.setIcon(FontAwesome.PLUS_CIRCLE);
		biofrom.addComponents(policyNumber, portion, title, lastName, relation,
				middleName, firstName, dob, gender);
		contact.addComponents(nationality, region, telephone, email, address);
		up.setVisible(false);
		this.addComponents(biofrom, contact);
		this.setSpacing(true);
		addCustomer.addClickListener(this);
		portion.addValueChangeListener(this);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
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

		this.holder.getBeneficiaries().add(beneficiary);

		// event.getButton().setEnabled(false);
		if (portion.getValue() < portion.getMax()) {
			totalPercent += portion.getValue();

			this.sheet.addTab(new BeneficiaryForm(holder, this.sheet,
					totalPercent), "Beneficiary ");
			sheet.setSelectedTab(sheet.getComponentCount() - 1);
			System.out.println("this is the me the end"
					+ holder.getBeneficiaries().size());

		} else if (portion.getValue() == portion.getMax()) {

			if (holder.isTrusteeAllowed() == true) {
				try {
					sheet.addTab(new TrusteesForm(holder), "Trustee");
					sheet.setSelectedTab(sheet.getComponentCount() - 1);

				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
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
		}

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		if (portion.getValue() == 0) {
			up.setVisible(true);

		}
	}

}
