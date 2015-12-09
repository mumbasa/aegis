package com.form;

import java.sql.SQLException;

import org.vaadin.haijian.CSVExporter;

import com.data.Policy;
import com.utility.DBUtility;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class StatementForm extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static double accumulated =0.0;
	Table statements = new Table();
	Policy policy;
	public StatementForm(Policy policy) {
		this.policy=policy;
		// TODO Auto-generated constructor stub
	
	String g = null;	
	CSVExporter exporter = new CSVExporter();
	
	
		statements.setSizeFull();
		statements.setColumnCollapsingAllowed(true);
		statements.setSelectable(true);
		try {
			statements.setContainerDataSource(DBUtility.getPolicyStatement2(policy.getPolicyNumber()));
			statements.setVisibleColumns(new Object[]{ "paymentDate","premiumPaid","interestAmount","partialWithdrawal","accumulatedFund","cashValue"});

			g=(policy.getPolicyNumber());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
		System.out.println(g);
		exporter.setCaption("Export " +g+ " Statement");
		exporter.setIcon(FontAwesome.DOWNLOAD);
		exporter.setContainerToBeExported(statements);
		this.addComponents(getStatementSlip(policy.getPolicyNumber()),exporter,statements);
		this.setSpacing(true);
		this.setSizeFull();
	}

	public static HorizontalLayout getStatementSlip(String policyNumber){
		FormLayout details = new FormLayout();
		FormLayout details2 = new FormLayout();
		FormLayout details3 = new FormLayout();
		TextField ageAtIssue = new TextField("Age @ Issue");
		TextField accFund = new TextField("Accumulated Fund");
		TextField issueDate = new TextField("Issue Date");
		TextField maturityAge = new TextField("Maturity Age");
		TextField currency = new TextField("Currency");
		TextField totalPremiums = new TextField("Total Premium");
		TextField cashValue = new TextField("Cash Value");
		TextField deathBenefit = new TextField("Death Benefit");
		TextField allWithdrawals = new TextField("Total Withdrawals");
		HorizontalLayout holder = new HorizontalLayout(details,details2,details3);
		holder.setSpacing(true);
		details.addComponents(ageAtIssue,issueDate,maturityAge);
		details2.addComponents(totalPremiums,cashValue,accFund);
		details3.addComponents(deathBenefit,allWithdrawals,currency);
		details.setEnabled(false);
		details2.setEnabled(false);
		details3.setEnabled(false);
		try {
			String[] set = DBUtility.getPolicyStatementSummary2(policyNumber);
			ageAtIssue.setValue(set[0]);
			issueDate.setValue(set[1]);
			maturityAge.setValue(set[2]);
			totalPremiums.setValue(set[3]);
			accFund.setValue(set[4]);
			setAccumulated(Double.parseDouble(set[4]));
			deathBenefit.setValue(set[5]);
			allWithdrawals.setValue(set[6]);
			cashValue.setValue(set[7]);
			currency.setValue("GH¢");
		} catch (SQLException | NullPointerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return  holder;
		
		
	}

	public static double getAccumulated() {
		return accumulated;
	}

	public static void setAccumulated(double accumulated) {
		StatementForm.accumulated = accumulated;
	}
	
}
