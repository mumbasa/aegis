package com.component;

import java.sql.SQLException;
import java.util.HashMap;

import com.utility.ComponentUtil;
import com.vaadin.ui.ComboBox;

public class PaymentModeCombo extends ComboBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PaymentModeCombo(String caption)  {
		HashMap<Integer, String> data = null;
		try {
			data = ComponentUtil.getPaymentMode();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int a : data.keySet()){
			this.addItem(a);
			this.setItemCaption(a, data.get(a));
			
		}
		this.setNullSelectionAllowed(false);
		this.setCaption(caption);
	}
}
