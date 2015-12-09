package com.component;

import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;

public class TelephoneField extends TextField implements FocusListener,BlurListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TelephoneField(String caption){
		
		this.setCaption(caption);
		
		

		this.addBlurListener(this);
		this.addFocusListener(this);
		
	}

	@Override
	public void blur(BlurEvent event) {
		// TODO Auto-generated method stub
		if(!this.getValue().matches("\\d{5,10}")){
			this.setValue("");
			Notification.show("Incorrect Format",Type.ERROR_MESSAGE);
			this.focus();
			this.setValue("");
		}
	
		
		
	}

	@Override
	public void focus(FocusEvent event) {
		this.setComponentError(null);
		
	}
	

}
