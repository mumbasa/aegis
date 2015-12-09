package com.component;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.views.AddCustomer;
import com.views.AddGroupPolicy;
import com.views.AllCustomers;
import com.views.BatchBankPayments;
import com.views.ChequeProcessing;
import com.views.ClaimsProcessing;
import com.views.ClaimsView;
import com.views.Dashboard;
import com.views.ExistingGroup;
import com.views.MedicalApproval;
import com.views.NewPolicy;
import com.views.PaymentsForm;
import com.views.PolicyRequestApproval;
import com.views.SearchCustomer;
import com.views.SearchGroup;
import com.views.SearchPolicy;
import com.views.SourcePayments;

public class MainDiv extends AbsoluteLayout {

	private static final long serialVersionUID = 1L;

	public MainDiv() {
		// TODO Auto-generated constructor stub
		this.setSizeFull();
		getUI();
		UI ui= UI.getCurrent();
		Sidebar sidebar = new Sidebar();
		TopBar topbar = new TopBar();
		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		content.setStyleName("main");
		this.addComponent(sidebar, "left:0px;top:0");
		this.addComponent(topbar, "left:250px;top:0px");
		this.addComponent(content, "left:250px;top:80px");

		ViewDisplay viewer = new Navigator.ComponentContainerViewDisplay(content);
		Navigator navigator = new Navigator(ui, viewer);
		navigator.addView("New Customer", AddCustomer.class);
		navigator.addView("Policy Payment", PaymentsForm.class);
		navigator.addView("Claims Filing", ClaimsView.class);
		navigator.addView("Search Customer", SearchCustomer.class);
		navigator.addView("Search Group", SearchGroup.class);
		navigator.addView("Search Policy", SearchPolicy.class);
		navigator.addView("Existing Customer",NewPolicy.class);
		navigator.addView("Existing Group",ExistingGroup.class);
		navigator.addView("Dashboard",Dashboard.class);
		navigator.addView("Claims Processing",ClaimsProcessing.class);
		navigator.addView("Source Processing",SourcePayments.class);
		navigator.addView("Batch Bank Payment",BatchBankPayments.class);
		navigator.addView("Cheque Payment Processing",ChequeProcessing.class);
		navigator.addView("Medicals Approval",MedicalApproval.class);
		navigator.addView("Add New Group",AddGroupPolicy.class);
		navigator.addView("Policy Approval",PolicyRequestApproval.class);
		navigator.addView("All Customers",AllCustomers.class);
		navigator.navigateTo("Existing Group");

	}
	
}
