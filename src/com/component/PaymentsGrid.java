package com.component;

import com.data.PolicyPayment;
import com.utility.DBUtility;
import com.utility.PaymentDBUtil;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class PaymentsGrid extends VerticalLayout implements ClickListener,
		SelectionListener {

	Grid grid = new Grid();
	Button clear = new Button("Clear Cheques");

	public PaymentsGrid(BeanItemContainer<PolicyPayment> payments) {

		grid.setSelectionMode(SelectionMode.MULTI);
		grid.setContainerDataSource(payments);
		grid.removeColumn("paymentModeId");
		grid.removeColumn("payee");
		grid.removeColumn("paymentType");
		grid.removeColumn("policyType");
		grid.removeColumn("policyParentType");
		grid.setWidth("100%");
		clear.addClickListener(this);
		this.setSpacing(true);
		this.addComponents(grid,clear);

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void select(SelectionEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		int stat = 0;
		for (Object id : grid.getSelectedRows()) {
			PolicyPayment l = (PolicyPayment) id;

			if (DBUtility.ChequeApproval(l) == 1
					& PaymentDBUtil.insertPolicyChequeStatement(l) == 1) {
				stat = 1;
			} else {
				stat = 0;
			}

		}
		if (stat == 1) {
			Notification.show("All selected approved");
			UI.getCurrent().getNavigator().navigateTo("Cheque Payment Processing");
			
		} else {
			Notification.show("All selected approved",
					com.vaadin.ui.Notification.Type.ASSISTIVE_NOTIFICATION);

		}
	}

}
