package com.component;

import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;

public class YesOrNoRadio extends OptionGroup implements BlurListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
TextField field;
	public YesOrNoRadio(String caption) {
		this.setCaption(caption);
		this.addItem("Y");
		this.addItem("N");
		this.setItemCaption("Y", "Yes");
		this.setItemCaption("N", "No");

		this.setNullSelectionAllowed(false);
		this.setCaption(caption);
		this.setMultiSelect(false);
		this.setImmediate(true);
	}

	
	public YesOrNoRadio(String caption,TextField field) {
		this.field=field;
		this.setCaption(caption);
		this.addItem("Y");
		this.addItem("N");
		this.setItemCaption("Y", "Yes");
		this.setItemCaption("N", "No");

		this.setNullSelectionAllowed(false);
		this.setCaption(caption);
		this.setMultiSelect(false);
		this.setImmediate(true);
		this.addBlurListener(this);
	}


	@Override
	public void blur(BlurEvent event) {
		// TODO Auto-generated method stub
	
			if (this.getValue().toString().equals("Y")){
				field.setVisible(true);
			}
			else{
				field.setVisible(false);
				
		
		}
	}
	



	
}
