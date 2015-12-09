package com.component;

import java.sql.SQLException;
import java.util.HashMap;

import com.utility.ComponentUtil;
import com.vaadin.ui.ComboBox;

public class PolicyNumbersCombo extends ComboBox{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HashMap<String, String> data;
	public PolicyNumbersCombo(String caption) {

		try {
			data = ComponentUtil.getApprovedPolicies();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String a : data.keySet()){
			this.addItem(a);
			this.setItemCaption(a, a);
			this.setNullSelectionAllowed(false);
		}
		this.setCaption(caption);

	}
	
	
	public PolicyNumbersCombo(String caption,String as) {

		try {
			data = ComponentUtil.getApprovedPoliciesForClaim();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String a : data.keySet()){
			this.addItem(a);
			this.setItemCaption(a, a);
			this.setNullSelectionAllowed(false);
		}
		this.setCaption(caption);

	}
	
	
	public String getActivity(){
		return data.get(this.getValue().toString());
		
	}
}
