package com.component;

import com.vaadin.ui.ComboBox;

public class ChildrenCombo extends ComboBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChildrenCombo(String caption,int s) {
	
		//is the number of percentage
		for (int i=0;i<=s;i++) {
			this.addItem(i);
			this.setItemCaption(""+i, ""+i);
		}
		this.setNullSelectionAllowed(false);
		this.setCaption(caption);
	}
}
