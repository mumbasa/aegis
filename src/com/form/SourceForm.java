package com.form;

import java.sql.SQLException;

import com.component.CompanyCombo;
import com.component.RegionCombo;
import com.data.Policy;
import com.data.Source;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;

public class SourceForm extends FormLayout implements ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final TextField staffNumber = new TextField("Staff Number");
	final CompanyCombo company = new CompanyCombo("Company Name");
	final TextField policyNumber = new TextField("Policy Number");
	TabSheet sheet;
	RegionCombo region = new RegionCombo("Region");
	final TextField branch = new TextField("Branch");
	Policy policy;

	public SourceForm(Policy policy, TabSheet sheet) {
		this.policy = policy;
		this.sheet = sheet;
		policyNumber.setValue(policy.getPolicyNumber());
		policyNumber.setEnabled(false);

		Button addSource = new Button("Add Source Detail");
		addSource.addClickListener(this);
		this.addComponents(policyNumber, company, staffNumber, branch, region,
				addSource);

	}

	public SourceForm(Source source) {
		staffNumber.setValue(source.getStaffNumber());
		company.setValue(source.getCompanyID());
		region.setValue(source.getRegion());
		branch.setValue(source.getBranch());
		policyNumber.setValue(source.getPolicyNumber());
	
		policyNumber.setEnabled(false);

		this.addComponents(policyNumber, company, staffNumber, branch);

	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		Source source = new Source();
		source.setPolicyNumber(policy.getPolicyNumber());
		source.setCompanyID(company.getValue().toString());
		source.setStaffNumber(staffNumber.getValue());
		source.setCustomerNumber(policy.getCustomerNumber());
		policy.setSource(source);
		if (policy !=null & source !=null) {
			Notification.show("Yes", Type.TRAY_NOTIFICATION);
				CopyOfPolicyHolderForm holderForm;
	
			try {
			
				holderForm = new CopyOfPolicyHolderForm(sheet, policy);
				this.sheet.addTab(holderForm,"Policy Holder Details");
				this.sheet.setSelectedTab(holderForm);
			
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		

		} else {

			Notification.show("Yes", Type.ERROR_MESSAGE);
		}

	}

}
