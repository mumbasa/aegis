package com.component;

import java.text.SimpleDateFormat;

import com.utility.DBUtility;
import com.vaadin.ui.DateField;

public class DOBDateField extends DateField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DOBDateField(String caption) {
		// TODO Auto-generated constructor stub
	this.setCaption(caption);
	this.setRangeEnd(DBUtility.NOW);
	this.setDateFormat("yyyy-MM-dd");
	}
	public String getDateValue(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String d = df.format(this.getValue());
		return d;
		
	
	
}
}
