package com.component;


import java.sql.SQLException;
import java.util.Calendar;

import com.vaadin.ui.ComboBox;

public class YearsCombo extends ComboBox{

	private static final long serialVersionUID = 1L;
	
	public YearsCombo() throws SQLException {
		// TODO Auto-generated constructor stub
		this.setCaption("Year");
		this.setNullSelectionAllowed(false);
		Calendar cal = Calendar.getInstance();
		

	for( int i=cal.getWeekYear()-25;i<=cal.getWeekYear();i++){
		this.addItem(i);
		this.setItemCaption(i,i+"");
		
		
		
	}

	
	
	
	}
	

}
