package com.views;

import java.sql.SQLException;

import com.component.ClaimsApprovalWindow;
import com.utility.DBUtility;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ClaimsProcessing extends VerticalLayout implements ClickListener,
		View, ValueChangeListener, ColumnGenerator, ItemClickListener {

	/**
	 * 
	 */

	TabSheet tabSheet = new TabSheet();
	DateField searchTerm = new DateField("Date");
	CssLayout lay = new CssLayout();

	public ClaimsProcessing() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated constructor stub
		FormLayout thin = new FormLayout();
		searchTerm.setResolution(Resolution.MONTH);
		searchTerm.addValueChangeListener(this);
		Table claims = new Table();
		claims.addItemClickListener(this);
		claims.setContainerDataSource(DBUtility.getClaimsRequested());
		claims.addGeneratedColumn("Decision", this);
		claims.setVisibleColumns(new Object[] { "policyNumber", "amount",
				"dateApplied", "claimTypeId", "branchID", "status", "Decision" });
		claims.setSizeFull();
		claims.setSelectable(true);
		lay.setSizeFull();
		lay.addComponent(claims);

		thin.setStyleName("pads");
		thin.addComponent(searchTerm);
		Label head = new Label("POLICY CLAIM PROCESSING");
		head.setStyleName("Heading");
		searchTerm.addValueChangeListener(this);
		thin.setMargin(false);
		lay.setStyleName("paddings");

		// this.setMargin(true);
		this.addComponents(head, thin, lay);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		UI.getCurrent().getPage().setTitle("Claims Processing");
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-(generated method stub
		Notification.show(event.getProperty().getValue().toString());
	}

	@Override
	public Object generateCell(Table source, Object itemId, Object columnId) {
		// TODO Auto-generated method stub
		Button approveButton = new Button();
		approveButton.setIcon(FontAwesome.THUMBS_UP);
		Button dissapprButton = new Button();
		dissapprButton.setIcon(FontAwesome.THUMBS_DOWN);
		HorizontalLayout l = new HorizontalLayout(approveButton, dissapprButton);
		l.setSpacing(true);
		final Item item = source.getItem(itemId);
		final String policyNumber = item.getItemProperty("policyNumber").getValue().toString();
		final double amount = (double)item.getItemProperty("amount").getValue();
		final int claimID=(int)item.getItemProperty("claimID").getValue();;
		approveButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				UI.getCurrent().addWindow(new ClaimsApprovalWindow(policyNumber,amount,claimID));
				//Notification.show();
			}
		});

		dissapprButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Notification.show("No "
						+ item.getItemProperty("policyNumber").getValue()
								.toString());
			}
		});

		return l;
	}

	@Override
	public void itemClick(ItemClickEvent event) {
		// TODO Auto-generated method stub
		Item item = event.getItem();
		
		Notification.show(item.getItemProperty("claimID").getValue().toString()+"  No "
				+ item.getItemProperty("policyNumber").getValue().toString());

	}

}
