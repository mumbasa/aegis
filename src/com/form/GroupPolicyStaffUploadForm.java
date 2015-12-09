package com.form;

import com.component.GroupStaffUpload;
import com.data.GroupPolicy;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class GroupPolicyStaffUploadForm extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TabSheet tabSheet;
	GroupPolicy policy;
	public GroupPolicyStaffUploadForm(TabSheet tabSheet,GroupPolicy policy) {
		// TODO Auto-generated constructor stub
		this.tabSheet=tabSheet;
		this.policy=policy;
		
		GroupStaffUpload upload = new GroupStaffUpload(this,policy);
		this.addComponent(upload);
	}

}
