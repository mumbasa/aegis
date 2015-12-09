package com.component;

import java.sql.SQLException;

import com.data.Beneficiary;
import com.data.ClientBank;
import com.data.Health;
import com.data.Policy;
import com.data.PolicyHolder;
import com.data.Source;
import com.data.Trustee;
import com.form.BankForm;
import com.form.BeneficiaryForm;
import com.form.CopyOfPolicyHolderForm;
import com.form.Healthform;
import com.form.PolicyForm;
import com.form.SourceForm;
import com.form.StatementForm;
import com.form.TrusteesForm;
import com.utility.DBUtility;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;

public class GroupPolicyDetails extends TabSheet {

	private static final long serialVersionUID = 1L;

	public GroupPolicyDetails(Policy p) {
		// TODO Auto-generated constructor stub

		try {
			this.setSizeFull();
			this.addTab(new PolicyForm(p), p.getPolicyNumber() + " Details");
			ClientBank cBank=DBUtility.getBankPolicyBankDetails(p.getPolicyNumber());
			Source source = DBUtility.getBankPolicySourceDetails(p.getPolicyNumber());
			Health health = DBUtility.getHolderHealth(p.getPolicyNumber());
			Healthform healthForm = new Healthform(health);
			healthForm.setEnabled(false);
			if (cBank !=null){
				this.addTab(new BankForm(cBank), "Bank Details");
				
			}else if (source !=null){
				this.addTab(new SourceForm(source),  "Source Details");
				
			}
			
			PolicyHolder hol = DBUtility.getPolicyHolderDetails(p.getPolicyNumber());
			if (hol != null)
				this.addTab(new CopyOfPolicyHolderForm(hol),p.getPolicyNumber() + " Holder Details");
			this.addTab(healthForm, "Medical Details");
			for (Beneficiary ben : DBUtility.getPolicyBeneficiariesDetails(p)) {
				this.addTab(new BeneficiaryForm(ben), "Beneficiary");
			
			}
			Trustee trustee =DBUtility.getPolicyTrusteeDetails(p);
			if (trustee !=null){
			this.addTab(new TrusteesForm(trustee), p.getPolicyNumber() + " Trustee Details");
			}
			Table table = new Table("Payments");
			table.setSizeFull();
			table.setContainerDataSource(DBUtility.getPolicyPayments(p));
			table.setVisibleColumns(new Object[]{"amount","datePaid","paymentType","chequeNumber","payee"});
			StatementForm statements = new StatementForm(p);
			
	
			//statements.setPageLength(DBUtility.getPolicyStatement(p.getPolicyNumber()).size());
			this.addTab(table,"Payments");
			this.addTab(statements,"Statement");
			//this.setStyleName("paddings");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
