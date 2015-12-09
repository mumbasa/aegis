package com.group.components;

import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;

import tm.kod.widgets.numberfield.NumberField;

public class Decimalfield extends NumberField implements BlurListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Decimalfield(String caption) {
		// TODO Auto-generated constructor stub
		super();
		this.setCaption(caption);
		this.setDecimal(true);
	}

	@Override
	public void blur(BlurEvent event) {
		// TODO Auto-generated method stub
	

	}
}
