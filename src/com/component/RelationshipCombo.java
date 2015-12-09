package com.component;

import java.sql.SQLException;
import java.util.HashMap;

import com.utility.ComponentUtil;
import com.vaadin.ui.ComboBox;

public class RelationshipCombo extends ComboBox{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RelationshipCombo(String caption){
		this.setCaption(caption);
		
	try {
		HashMap<Integer,String> relation = ComponentUtil.getRelationships();
	for(int a : relation.keySet()){
		this.addItem(a);
		this.setItemCaption(a, relation.get(a));
	this.setNullSelectionAllowed(false);
	}
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	
		this.setNullSelectionAllowed(false);
	}
		
		
		
	}


