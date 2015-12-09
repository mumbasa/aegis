package com.component;

import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;

public class TextFieldCus extends TextField implements FocusListener,BlurListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TextFieldCus(String caption){
		
		this.setCaption(caption);
		
		this.addBlurListener(this);
		this.addFocusListener(this);
		
	}

	public void blur(BlurEvent event) {
		// TODO Auto-generated method stub
		
		if(this.getValue().equals("")|this.getValue().equals(null)){
			

		Notification.show(this.getCaption()+" can not be left empty", Type.ERROR_MESSAGE);
	
	
		this.setValue("");
		}
	}

	@Override
	public void focus(FocusEvent event) {
		this.setComponentError(null);
		this.focus();
	}
	

	

}
