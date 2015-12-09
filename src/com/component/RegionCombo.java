package com.component;

import java.sql.SQLException;
import java.util.HashMap;

import com.utility.ComponentUtil;
import com.vaadin.ui.ComboBox;

public class RegionCombo extends ComboBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RegionCombo(String caption) {
		HashMap<String, String> data = null;
		try {
			data = ComponentUtil.getRegion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String a : data.keySet()){
			this.addItem(a);
			this.setItemCaption(a, data.get(a));
			
		}
		
		
		this.setNullSelectionAllowed(false);
		this.setCaption(caption);
	
	}

}
