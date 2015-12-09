package com.component;

import java.util.ArrayList;

import com.data.Group;
import com.data.GroupMember;
import com.data.GroupPolicy;
import com.utility.GRPDBUtility;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class GroupMembersGrid extends VerticalLayout implements
		ValueChangeListener, ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	OccupationClassCombo occupationClass = new OccupationClassCombo();
	FilterableGrid members;
	Button button = new Button("Update");
	TabSheet sheet;
	Group group;
	GroupPolicy policy;

	public GroupMembersGrid(Group group, TabSheet sheet, GroupPolicy policy) {
		// TODO Auto-generated constructor stub
		Label warn = new Label("Make Sure to set the SALARY for each staff");
		this.sheet = sheet;
		this.group = group;
		this.policy = policy;
		members = new FilterableGrid(GRPDBUtility.getGroupMembers(group.getGroupNumber()));
		members.setEditorEnabled(true);
		try {
			members.saveEditor();
		} catch (CommitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		members.removeColumn("email");
		members.removeColumn("dob");
		members.removeColumn("age");
		members.removeColumn("groupId");
		members.removeColumn("telephone");
		occupationClass.addValueChangeListener(this);
		button.addClickListener(this);
		this.addComponents(warn,members, occupationClass, button);
		this.setSpacing(true);
	}
	
	
	public GroupMembersGrid(Group group, TabSheet sheet) {
		// TODO Auto-generated constructor stub
		this.sheet = sheet;
		this.group = group;
	
		members = new FilterableGrid(GRPDBUtility.getGroupMembers(group.getGroupNumber()));
		members.setEditorEnabled(true);
		try {
			members.saveEditor();
		} catch (CommitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		members.removeColumn("email");
		members.removeColumn("dob");
		members.removeColumn("age");
		members.removeColumn("groupId");
		members.removeColumn("telephone");
		members.removeColumn("salary");
		members.removeColumn("sumAssured");
		members.removeColumn("staffNumber");
		members.removeColumn("middleName");
		members.removeColumn("groupMemberNumber");
		button.addClickListener(this);
		this.addComponents(members, occupationClass, button);
		this.setSpacing(true);
	}
	
	
	public GroupMembersGrid(GroupPolicy policy) {
		// TODO Auto-generated constructor stub
		this.policy=policy;
		BeanItemContainer<GroupMember> membs = new BeanItemContainer<GroupMember>(GroupMember.class);
		membs.addAll(policy.getMembers());
		members = new FilterableGrid(membs);
		members.setEditorEnabled(true);
		try {
			members.saveEditor();
		} catch (CommitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		members.removeColumn("email");
		members.removeColumn("dob");
		members.removeColumn("age");
		members.removeColumn("gender");
		members.removeColumn("groupId");
		members.removeColumn("telephone");
		members.removeColumn("salary");
		members.removeColumn("sumAssured");
		members.removeColumn("staffNumber");
		members.removeColumn("middleName");
		members.removeColumn("occupationClassId");
		members.removeColumn("groupMemberNumber");
		button.addClickListener(this);
		this.addComponents(members, occupationClass, button);
		this.setSpacing(true);
	}
	
	
	
	
	
	
	public GroupMembersGrid(Group group, TabSheet sheet,int a) {
		// TODO Auto-generated constructor stub
		this.sheet = sheet;
		this.group = group;
	
		members = new FilterableGrid(GRPDBUtility.getPolicyMembers(group.getGroupNumber()));
		members.setEditorEnabled(true);
		try {
			members.saveEditor();
		} catch (CommitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		members.removeColumn("email");
		members.removeColumn("dob");
		members.removeColumn("age");
		members.removeColumn("groupId");
		members.removeColumn("telephone");
		members.removeColumn("salary");
		members.removeColumn("sumAssured");
		members.removeColumn("staffNumber");
		members.removeColumn("middleName");
		members.removeColumn("groupMemberNumber");
		button.addClickListener(this);
		this.addComponents(members, occupationClass, button);
		this.setSpacing(true);
	}
	

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		for (Object id : members.getSelectedRows()) {
			GroupMember m = (GroupMember) id;
			System.out.println(m.getDob());
			members.getContainerDataSource().removeItem(id);
			m.setOccupationClass("" + (int) occupationClass.getValue());
			members.getContainerDataSource().addItem(m);
			System.out.println(members.getContainerDataSource().getItem(id)
					.getItemProperty("occupationClass").getValue().toString());

		}
		occupationClass.commit();
	}


	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		Notification.show("Some of Data not Entererd");
		int stat = 1;
		ArrayList<GroupMember> mem = new ArrayList<GroupMember>();
		for (Object o : members.getContainerDataSource().getItemIds()) {
			GroupMember k = (GroupMember) o;
			k.setSumAssured(policy.getMultiplier());
			mem.add(k);
			System.out.println("dsfsdfdsdsafsaf adsfasd");
			if (k.getSalary() == 0) {
				//setting to zero to notify if a salary is null or zero
				stat = 0;
			}
		}
		if (stat == 1) {
			stat = 0;
			for (GroupMember m : mem) {
				if (GRPDBUtility.insertGroupMemberPolicy(m, policy) == 1) {
					//removing member to show the members with errors;
					//members.getContainerDataSource().removeItem(m);
				}else{
					stat=1;
				}

			}
			
		}	else{
			Notification.show("No",Type.ERROR_MESSAGE);
		}
		
		if (stat == 0) {
			UI.getCurrent().getNavigator().navigateTo("Dashboard");
		} else {
			Notification.show("Some of Data not Entererd",
					Type.ERROR_MESSAGE);
		}

	}



}
