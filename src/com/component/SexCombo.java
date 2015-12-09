package com.component;

import java.util.HashMap;

import com.vaadin.ui.OptionGroup;

public class SexCombo extends OptionGroup {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SexCombo(String caption) {
		HashMap<String, String> stat= new HashMap<String, String>();
		
		stat.put("F", "Female");
		stat.put("M", "Male");
	
		for (String a : stat.keySet()){
			this.addItem(a);
			this.setItemCaption(a, stat.get(a));
		}
		
		this.setNullSelectionAllowed(false);
		this.setCaption(caption);
		this.setMultiSelect(false);
	
	}

}
