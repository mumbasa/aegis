package com.form;

import com.data.Group;
import com.data.GroupMember;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

public class SelectMembers extends VerticalLayout implements ClickListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TwinColSelect colums = new TwinColSelect();
	BeanItemContainer<GroupMember> members;
	TabSheet sheet;
	Group group;
	Button button =new Button("Add");
	public SelectMembers(BeanItemContainer<GroupMember> m,TabSheet sheet,Group group) {
		// TODO Auto-generated constructor stub
		super();
		this.group = group;
		this.members=m;
		this.sheet=sheet;
		sheet.addTab(this);
		colums.setContainerDataSource(m);
		colums.setSizeFull();
		colums.setItemCaptionPropertyId("name");
		this.addComponents(colums,button);
		this.setSizeFull();
	}
	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}
	



}
