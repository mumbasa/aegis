package com.component;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;

public class EmailTextBox extends TextField implements FocusListener,BlurListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailTextBox(String caption){
		
		this.setCaption(caption);
		
		EmailValidator emailValid = new EmailValidator("{0} this is not a valid EmailAddress");
		this.addValidator(emailValid);
		this.addBlurListener(this);
		this.addFocusListener(this);
		
	}

	@Override
	public void blur(BlurEvent event) {
		// TODO Auto-generated method stub
		this.validate();
		
		if(!this.isValid()){
			
		Notification l = new Notification("Enter the correct email Address");
		Notification.show("dadadaa", Type.ERROR_MESSAGE);
		l.show(Page.getCurrent());	
	
		this.setValue("");
		}
	}

	@Override
	public void focus(FocusEvent event) {
		this.setComponentError(null);
		
	}
	

}
