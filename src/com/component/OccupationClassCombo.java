package com.component;

import java.util.HashMap;

import com.utility.ComponentUtil;
import com.vaadin.ui.ComboBox;

public class OccupationClassCombo extends ComboBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public OccupationClassCombo(){
		HashMap<Integer, String> data = null;
		
		data = ComponentUtil.getOccupationClass();
		for (int a : data.keySet()){
			this.addItem(a);
			this.setItemCaption(a, data.get(a));
		
		}
		this.setNullSelectionAllowed(false);
		
	//	this.setDescription("Occupation Class");
		this.setInputPrompt("Occupation Class");
	

	}
}
