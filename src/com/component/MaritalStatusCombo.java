package com.component;

import java.sql.SQLException;
import java.util.HashMap;

import com.utility.ComponentUtil;
import com.vaadin.ui.ComboBox;

public class MaritalStatusCombo extends ComboBox{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MaritalStatusCombo(String caption) throws ClassNotFoundException, SQLException{
		this.setCaption(caption);
		HashMap<Integer, String> data = ComponentUtil.getMaritalStatuses();
		for (int a : data.keySet()){
			this.addItem(a);
			this.setItemCaption(a, data.get(a));
			
		}
		
		this.setNullSelectionAllowed(false);
	}
		
		

}
