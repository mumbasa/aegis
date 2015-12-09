package com.views;

import java.sql.SQLException;

import com.component.DOBDateField;
import com.component.PaymentsGrid;
import com.data.PolicyPayment;
import com.utility.DBUtility;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ChequeProcessing extends VerticalLayout implements  View ,ValueChangeListener,SelectionListener{

	/**
	 * 
	 */

	TabSheet tabSheet = new TabSheet();
	DOBDateField searchTerm = new DOBDateField("Date");	
	VerticalLayout lay = new VerticalLayout();
	Button clear= new Button("Clear Cheques");
	Grid grid = new Grid();
		public ChequeProcessing() throws ClassNotFoundException, SQLException {
			// TODO Auto-generated constructor stub
			FormLayout thin = new FormLayout();
			
			searchTerm.setResolution(Resolution.MONTH);
			searchTerm.setDateFormat("MM-yyyy");
			
			thin.setStyleName("pads");
			thin.addComponent(searchTerm);
			Label head = new Label("PREMIUMS CHEQUE PAYMENTS");
			head.setStyleName("Heading");
			searchTerm.addValueChangeListener(this);
			thin.setMargin(false);
			lay.setStyleName("paddings");
			
		
		
			lay.setSpacing(true);
			//this.setMargin(true);
		
			this.addComponents(head,thin,lay);
		}

	private static final long serialVersionUID = 1L;



	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		if (event.getProperty().equals(searchTerm)){
			String data = searchTerm.getDateValue();
			System.out.println("DAte is "+data);
			if ( data !=null|!data.equals("")){
				String[] bp = data.split("-");
				
				try {
					BeanItemContainer<PolicyPayment> payments =DBUtility.getPolicyChequePaymentsMonthly(1,"N",Integer.parseInt(bp[0]),Integer.parseInt(bp[1]));
					if(payments.getItemIds().size()>0){
						PaymentsGrid g = new PaymentsGrid(payments);
						lay.removeAllComponents();
						lay.addComponent(g);
						
					}
				
				
				} catch (NumberFormatException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else{
				try {
					BeanItemContainer<PolicyPayment> payments =DBUtility.getPolicyChequePayments(1,"N");
					if(payments.getItemIds().size()>0){
						PaymentsGrid g = new PaymentsGrid(payments);
						lay.removeAllComponents();
						lay.addComponent(g);
						
					}
				
				
				} catch (NumberFormatException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
	
	}else{
		System.out.println(event.getProperty().equals(searchTerm));
	}
	}
	@Override
	public void select(SelectionEvent event) {
		// TODO Auto-generated method stub
		UI.getCurrent().getPage().setTitle("Cheque Processing");
	}

}
