package com.component;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class HealthComponents extends VerticalLayout implements ValueChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CheckBox doy = new CheckBox();
	TextField reason = new TextField("Details");

	public HealthComponents(String caption ,String check) {
		// TODO Auto-generated constructor stub
	this.addComponents(doy,reason);
	reason.setVisible(false);
	doy.setCaption(check);
	doy.addValueChangeListener(this);
	this.setCaption(caption);
	reason.setWidth("400px");
	
	}
	
	public void setStatus(boolean answer){
		doy.setValue(answer);
	}
	
	public void setReason(String answer){
	
		reason.setValue(answer);
	}
	
		@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		if(event.getProperty().getValue().equals(true)){
			reason.setVisible(true);
		}else{
			reason.setVisible(false);
		}
	}
		
		public String getDetails(){
			if (reason.getValue() == null){
				return "";
			}
			else{
				return reason.getValue();
			}
		}
		
		public String getStatus(){
			if (doy.getValue()==true){
				return "Y";
			}
			else{
				return "N";
			}
			
		} 
	
}
