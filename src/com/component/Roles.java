package com.component;


import java.sql.SQLException;
import java.util.HashMap;

import com.utility.DBUtility;
import com.vaadin.ui.ComboBox;

public class Roles extends ComboBox{

	private static final long serialVersionUID = 1L;
	
	public Roles() throws SQLException {
		// TODO Auto-generated constructor stub
		this.setCaption("Role");
		this.setNullSelectionAllowed(false);
	HashMap<Integer,String> data = DBUtility.getRole();
	for( int a : data.keySet()){
		this.addItem(a);
		this.setItemCaption(a, data.get(a));
		
		
		
	}
	
	
	
	
	}
	

}
