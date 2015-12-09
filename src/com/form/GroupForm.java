package com.form;

import tm.kod.widgets.numberfield.NumberField;

import com.component.CompanyCombo;
import com.component.EmailTextBox;
import com.component.OccupationCombo;
import com.component.RegionCombo;
import com.component.TelephoneField;
import com.component.TitleCombo;
import com.data.Group;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class GroupForm extends HorizontalLayout implements ClickListener{
	TabSheet tabSheet = new TabSheet();
	private static final long serialVersionUID = 1L;
	TextField name = new TextField("Organization Name");

	TelephoneField tele = new TelephoneField("Telephone");
	TextArea address = new TextArea("Address");
	EmailTextBox email = new EmailTextBox("Email");
	CompanyCombo subsidiary = new CompanyCombo("Subsidiary");
	OccupationCombo business = new OccupationCombo("Business");
	NumberField staffs = new NumberField("Number of Staff");
	RegionCombo region = new RegionCombo("Region");

	TextField respresentative = new TextField("Representative");
	TitleCombo title = new TitleCombo();

	Button button = new Button("Add Group");
	
	public GroupForm(TabSheet tabSheet) {
		// TODO Auto-generated constructor stub
		this.tabSheet=tabSheet;
		FormLayout info = new FormLayout(name, tele, email, business, address);
		FormLayout info2 = new FormLayout(region, title, respresentative,
				subsidiary, button);
		button.addClickListener(this);
		this.addComponents(info, info2);
		this.setSpacing(true);
	}
	
	
	public GroupForm(Group group,TabSheet tabSheet) {
		// TODO Auto-generated constructor stub
		FormLayout info = new FormLayout(name, tele, email, business, address);
		FormLayout info2 = new FormLayout(region,  respresentative,
				subsidiary);
		//button.addClickListener(this);
		name.setValue(group.getCompanyName());
		tele.setValue(group.getTelephone());
		email.setValue(group.getEmail());
		address.setValue(group.getAddress());
		region.setValue(group.getRegion());
		respresentative.setValue(group.getRepresentative());
		subsidiary.setValue(group.getSubsidiaryID());
		
	
		
		this.tabSheet=tabSheet;
	
		this.addComponents(info, info2);
		this.setSpacing(true);
	}
	
	
	
	
	
	
	

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		Group group = new Group();
		GroupPolicyForm form = new GroupPolicyForm(tabSheet,group);
		
		group.setCompanyName(name.getValue());
		group.setTelephone(tele.getValue());
		group.setEmail(email.getValue());
		group.setIndustryID((int)business.getValue());
		group.setRepresentative(title.getValue()+" "+respresentative.getValue());
		group.setAddress(address.getValue());
		group.setRegion(region.getValue().toString());
		group.setSubsidiaryID((int)subsidiary.getValue());
		
		if(!group.getCompanyName().equals("")& group.getRegion()!=null){
		tabSheet.addTab(form, "Add Group Policy");
		tabSheet.setSelectedTab(form);
		}else{
			Notification.show("NO way", Type.ERROR_MESSAGE);
		}
		
	}
}
