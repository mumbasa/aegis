package com.component;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class Sidebar extends AbsoluteLayout implements ClickListener,ValueChangeListener{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Sidebar() {
		// TODO Auto-generated constructor stub
	
	//	Staff staff = (Staff)UI.getCurrent().getSession().getAttribute("staff");	
	Embedded image = new Embedded();
//	FileResource file = new FileResource(new File(DBUtility.LOCATION+"/Pictures/"+staff.getPicture()));
	//image.setSource(file);
	Button bu = new Button();
	bu.setIcon(FontAwesome.POWER_OFF);
	HorizontalLayout topDetail = new HorizontalLayout();
	image.setHeight("80px");
	image.setWidth("80px");
	topDetail.setStyleName("top-details");

	
   Label staffName = new Label("AEGIS FRONTDESK");
   topDetail.addComponents(staffName);
  
    topDetail.setWidth("250px");
    this.addComponent(topDetail, "left:0;top:0");
    this.setStyleName("sidebar");
    Tree t = new Tree();
    t.addValueChangeListener(this);
    t.addItem("Dashboard");
    t.addItem("Add New Policy");
    t.addItem("Add Group Policy");
    t.addItem("Existing Group");
    t.addItem("Add New Group");
    t.setParent("Existing Group", "Add Group Policy");
    t.setParent("Add New Group", "Add Group Policy");
    
    t.addItem("Add Individual Policy");
    t.addItem("Existing Customer");
    t.addItem("New Customer");
    
    t.setParent("Existing Customer", "Add Individual Policy");
    t.setParent("New Customer", "Add Individual Policy");
    
    t.setParent("Add Group Policy", "Add New Policy");
    t.setParent("Add Individual Policy", "Add New Policy");

    
    t.addItem("Policy Approvals");
    t.addItem("Medicals Approval");
    t.addItem("Policy Approval");    
    t.setParent("Policy Approval", "Policy Approvals");
    t.setParent("Medicals Approval", "Policy Approvals");

    
    
    t.addItem("Policy Details");
    t.addItem("Search Customer");
    t.addItem("Search Policy");
    t.addItem("All Customers");
    t.addItem("Search Group");
    t.setParent("All Customers", "Policy Details");
    t.setParent("Search Customer", "Policy Details");
    t.setParent("Search Group", "Policy Details");
    t.setParent("Search Policy", "Policy Details");
   
    
    t.addItem("Operations");
    t.addItem("Policy Payment");
    t.addItem("Batch Bank Payment");
    t.addItem("Cheque Payment Processing");
    t.addItem("Claims Processing");
    t.addItem("Claims Filing");
    t.addItem("Source Processing");
    
    t.setParent("Policy Payment", "Operations");
    t.setParent("Batch Bank Payment", "Operations");
    t.setParent("Cheque Payment Processing", "Operations");
    t.setParent("Claims Processing", "Operations");
    t.setParent("Claims Filing", "Operations");
    t.setParent("Source Processing", "Operations");
   
    

    this.addComponent(t,"left:0;top:85px");


	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub4
		try{
		switch (event.getProperty().getValue().toString()){
		case "New Customer":
			UI.getCurrent().getNavigator().navigateTo("New Customer");
			break;
		case "Search Customer":
			UI.getCurrent().getNavigator().navigateTo("Search Customer");
			break;
			
		case "Search Group":
			UI.getCurrent().getNavigator().navigateTo("Search Group");
			break;
		case "Batch Bank Payment":
		UI.getCurrent().getNavigator().navigateTo("Batch Bank Payment");		
			break;
		case "Claims Filing":
		UI.getCurrent().getNavigator().navigateTo("Claims Filing");		
			break;	
		case "Search Policy":
			UI.getCurrent().getNavigator().navigateTo("Search Policy");		
				break;	
		case "Dashboard":
			UI.getCurrent().getNavigator().navigateTo("Dashboard");		
				break;
		case "Existing Customer":
			UI.getCurrent().getNavigator().navigateTo("Existing Customer");		
				break;
		case "Claims Processing":
			UI.getCurrent().getNavigator().navigateTo("Claims Processing");		
				break;
		case "Source Processing":
			UI.getCurrent().getNavigator().navigateTo("Source Processing");		
				break;
		case "Surrender":
			UI.getCurrent().getNavigator().navigateTo("Surrender");		
				break;
		
		case "Policy Payment":
			UI.getCurrent().getNavigator().navigateTo("Policy Payment");		
				break;
		
		case "Cheque Payment Processing":
			UI.getCurrent().getNavigator().navigateTo("Cheque Payment Processing");		
				break;
		
		case "Policy Approval":
			UI.getCurrent().getNavigator().navigateTo("Policy Approval");		
				break;	
		case "All Customers":
			UI.getCurrent().getNavigator().navigateTo("All Customers");		
							break;				
				
		case "Add New Group":
			UI.getCurrent().getNavigator().navigateTo("Add New Group");		
				break;	
		case "Existing Group":
			UI.getCurrent().getNavigator().navigateTo("Existing Group");		
				break;	
		
				
		case "Medicals Approval":
			UI.getCurrent().getNavigator().navigateTo("Medicals Approval");		
				break;	
				
			default:
				
		}
		}catch(NullPointerException ex){}
	
	}

}
