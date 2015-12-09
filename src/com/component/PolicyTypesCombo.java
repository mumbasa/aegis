package com.component;

import java.sql.SQLException;
import java.util.HashMap;

import com.utility.ComponentUtil;
import com.vaadin.ui.ComboBox;

public class PolicyTypesCombo extends ComboBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HashMap<String, String[]> datas;
	public PolicyTypesCombo() {
		try {
			datas = ComponentUtil.getPolicyTypes2();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String a : datas.keySet()) {
			this.addItem(a);
			this.setItemCaption(a, datas.get(a)[0]);

		}

		
		this.setNullSelectionAllowed(false);
		this.setCaption("Policy Types");
	}
	
	public int getInsuranceType(String a){
		
		return Integer.parseInt(datas.get(a)[1]);
		
		
	}
	
	
	
	
	public PolicyTypesCombo(String caption) throws SQLException {
		HashMap<String, String> data = ComponentUtil.getPolicyTypes();
		for (String a : data.keySet()) {
			this.addItem(a);
			this.setItemCaption(a, data.get(a));

		}

		this.setNullSelectionAllowed(false);
		this.setCaption(caption);
	}
	
	
	
	
	
	

	public PolicyTypesCombo(String policyNumber, int k) throws SQLException {
		HashMap<Integer, String> data = ComponentUtil.getPolicyTypes(policyNumber);
		for (int a : data.keySet()) {
			this.addItem(a);
			this.setItemCaption(a, data.get(a));
			this.setValue(a);

		}
		this.setEnabled(false);
		this.setNullSelectionAllowed(false);
		this.setCaption("Policy Type");
	}
	
	
	
	public PolicyTypesCombo(boolean me) {
		HashMap<String, String> data = null;
		try {
			data = ComponentUtil.getGroupPolicyTypes();
			for (String a : data.keySet()) {
				this.addItem(a);
				this.setItemCaption(a, data.get(a));
		

			}
		
			this.setNullSelectionAllowed(false);
			this.setCaption("Policy Type");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public PolicyTypesCombo(int k) throws SQLException {
		HashMap<String, String> data = ComponentUtil.getProductClass(k);
		for (String a : data.keySet()) {
			this.addItem(a);
			this.setItemCaption(a, data.get(a));
			this.setValue(a);

		}
		this.setEnabled(false);
		this.setNullSelectionAllowed(false);
		this.setCaption("Policy Type");
	}
}
