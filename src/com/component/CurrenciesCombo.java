package com.component;

import java.sql.SQLException;
import java.util.HashMap;

import com.utility.ComponentUtil;
import com.vaadin.ui.ComboBox;

public class CurrenciesCombo extends ComboBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CurrenciesCombo(String caption) {
	

		HashMap<String, String> data = null;
		try {
			data = ComponentUtil.getCurrencies();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String a : data.keySet()){
			this.addItem(a);
			this.setItemCaption(a, data.get(a));
			this.setNullSelectionAllowed(false);
			
		}
		this.setValue("GHS");
		this.setCaption(caption);
	}
	
}
