package com.component;

import java.sql.SQLException;
import java.util.HashMap;

import com.utility.ComponentUtil;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;

public class CustomersCombo extends ComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomersCombo(String caption) {
	
		HashMap<String, String> data = null;
		try {
			data = ComponentUtil.getAllCustomers();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String a : data.keySet()){
			this.addItem(a);
			this.setItemCaption(a, data.get(a));
			this.setNullSelectionAllowed(false);
			
		}
		this.setCaption(caption);
	}
	
	public CustomersCombo() {
		
		HashMap<String, String> data = null;
		try {
			data = ComponentUtil.getAllCustomersNames();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String a : data.keySet()){
			this.addItem(a);
			this.setItemCaption(a, data.get(a));
			this.setNullSelectionAllowed(false);
			this.setFilteringMode(FilteringMode.CONTAINS);
		}
	
	}
	
	
public CustomersCombo(int b,String f) {
		
		HashMap<String, String> data = null;
		try {
			data = ComponentUtil.getAllGroups();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String a : data.keySet()){
			this.addItem(a);
			this.setItemCaption(a, data.get(a));
			this.setNullSelectionAllowed(false);
			this.setFilteringMode(FilteringMode.CONTAINS);
		}
	this.setCaption(f);
	}
	
	
}
