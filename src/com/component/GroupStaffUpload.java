package com.component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import com.utility.DBUtility;
import com.utility.GRPDBUtility;
import com.vaadin.data.util.BeanItemContainer;
import com.data.GroupMember;
import com.data.GroupPolicy;
import com.data.Staff;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

public class GroupStaffUpload extends Upload implements Receiver,
		SucceededListener, ClickListener {
	/**
	 * 
	 */
	private String fileNameString;
	final String LOCATION = "/home/bryan/";
	private static final long serialVersionUID = 1L;
	Button approve = new Button("Approve");
	VerticalLayout layout;
	Grid grid = new Grid();
	public File file = null;
	Staff staffs = (Staff) UI.getCurrent().getSession().getAttribute("staff");
	GroupPolicy policy;
	BeanItemContainer<GroupMember> groupMembers = new BeanItemContainer<GroupMember>(
			GroupMember.class);

	public GroupStaffUpload(VerticalLayout layout,GroupPolicy policy) {
	 this.policy =policy;
		this.setCaption("Upload");
		this.layout = layout;
		this.addSucceededListener(this);
		this.setReceiver(this);
		approve.setIcon(FontAwesome.THUMBS_UP);
		approve.addClickListener(this);

	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		String extension = fileNameString.substring(fileNameString.length() - 3,fileNameString.length());
		if (extension.equals("csv")){
		FileReader re;
		try {
			re = new FileReader(LOCATION + fileNameString);
			BufferedReader reader = new BufferedReader(re);
			int count = 0;
			String place;
			while ((place = reader.readLine()) != null) {
				if (count > 0) {
				GroupMember member = new GroupMember();	
				String[]data = place.split(",");
				member.setFirstName(data[0]);
				member.setLastName(data[1]);
				member.setGroupId(policy.getGroupNumber());
				member.setStaffNumber(data[2]);
				member.setOccupationClass((data[3]));
				member.setDob(data[4]);
				member.setAge(DBUtility.getAge(data[4]));
				member.setGender(data[5].equalsIgnoreCase("male")?"M":"F");
				member.setSalary(Double.parseDouble(data[6]));
				member.setSumAssured(member.getSalary()*2);
				groupMembers.addBean(member);
				
				System.out.println(place);
				
				}
				count++;
				

			}
			reader.close();
			re.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		layout.removeAllComponents();
		
		layout.addComponents(getPaymentGrid(),approve);
		

		System.out.println(file.getName());
		}
		else{
			Notification.show("ONLY CSV FILES ALLOWED ",Type.ERROR_MESSAGE);
			
		}
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		// TODO Auto-generated method stub

		FileOutputStream stream = null;


		// filename = "name";
		try {
			file = new File(LOCATION + filename);
			fileNameString = filename;
			stream = new FileOutputStream(file);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stream;

	}

	public Grid getPaymentGrid() {
		
		grid.setContainerDataSource(groupMembers);
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.setEditorEnabled(true);

		grid.setSizeFull();
		return grid;

	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		int stat =1;

		for (Object id :grid.getSelectedRows()){
			GroupMember m =(GroupMember) id;
			if (GRPDBUtility.insertGroupMember(m)==1 & GRPDBUtility.insertGroupMemberPolicy(m, policy)==1){
				stat=1;	
			}else{
				Notification.show(m.getStaffNumber()+" not added",Type.WARNING_MESSAGE);
			
			}
		}

		if(stat==1){
			Notification.show("Finished");
			UI.getCurrent().getNavigator().navigateTo("Batch Bank Payment");
		}
		
	}

}
