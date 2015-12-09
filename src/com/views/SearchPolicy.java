package com.views;

import java.sql.SQLException;

import com.component.PolicyDetails;
import com.component.PolicyNumbersCombo;
import com.data.Policy;
import com.utility.DBUtility;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

public class SearchPolicy extends VerticalLayout implements ClickListener, View ,ValueChangeListener{

	/**
	 * 
	 */

	TabSheet tabSheet = new TabSheet();
	PolicyNumbersCombo searchTerm = new PolicyNumbersCombo("Policy Number");	
	VerticalLayout lay = new VerticalLayout();
		public SearchPolicy() throws ClassNotFoundException, SQLException {
			// TODO Auto-generated constructor stub
			FormLayout thin = new FormLayout();
			thin.setStyleName("pads");
			thin.addComponent(searchTerm);
			Label head = new Label("SEARCH POLICY");
			head.setStyleName("Heading");
			searchTerm.addValueChangeListener(this);
		thin.setMargin(false);
		lay.setStyleName("paddings");
			
			//this.setMargin(true);
			this.addComponents(head,thin,lay);
		}

	private static final long serialVersionUID = 1L;

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub

		try {

			Policy policies = DBUtility.getPolicyDetails(searchTerm.getValue()
					.toString());
			if (policies != null) {
			
					Notification.show("Good " + searchTerm.getValue());
					lay.removeAllComponents();
				

					lay.addComponent(new PolicyDetails(policies));
				
				

			} else {

				Notification.show("bad" + searchTerm.getValue());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Notification.show("Good", Type.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		try {

			Policy policies = DBUtility.getPolicyDetails(searchTerm.getValue()
					.toString());
			if (policies != null) {
			
					Notification.show("Good " + searchTerm.getValue());
					lay.removeAllComponents();
		

					lay.addComponent(new PolicyDetails(policies));
				
				

			} else {

				Notification.show("bad" + searchTerm.getValue());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Notification.show("Good", Type.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

}
