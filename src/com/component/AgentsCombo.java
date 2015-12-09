package com.component;

import java.sql.SQLException;
import java.util.HashMap;

import com.utility.ComponentUtil;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;

public class AgentsCombo extends ComboBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AgentsCombo(String caption) {
		HashMap<String, String> data = null;
		try {
			data = ComponentUtil.getAgents();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String a : data.keySet()){
			this.addItem(a);
			this.setItemCaption(a, data.get(a));
			
		}
		this.setFilteringMode(FilteringMode.CONTAINS);
		this.setNullSelectionAllowed(false);
		this.setCaption(caption);
	}
}
