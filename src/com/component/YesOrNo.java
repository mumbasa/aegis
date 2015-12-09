package com.component;

import com.vaadin.ui.OptionGroup;

public class YesOrNo extends OptionGroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public YesOrNo(String caption) {
		// TODO Auto-generated constructor stub
		this.setCaption(caption);
		this.addItem("Y");
		this.addItem("N");
		this.setItemCaption("Y", "Yes");
		this.setItemCaption("N", "No");
		this.setMultiSelect(false);
		this.setImmediate(true);
	
	}

}
