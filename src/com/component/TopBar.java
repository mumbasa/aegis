package com.component;

import java.io.File;

import com.data.Staff;
import com.utility.DBUtility;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.views.Login;

public class TopBar extends HorizontalLayout implements ClickListener, Command {

	private static final long serialVersionUID = 1L;
	Button logout = new Button();
	Button lock = new Button();



	Staff staff = (Staff)VaadinSession.getCurrent().getAttribute("staff");
	MenuBar operations = new MenuBar();
	MenuItem mythings = operations.addItem(staff.getLastName()+" " +staff.getFirstName(), null);
	MenuItem me = operations.addItem("", FontAwesome.GEAR, null);
	//MenuItem logout = me.addItem("Logout", null);

	//MenuItem profile = me.addItem("Profile", null);
	MenuItem password = me.addItem("Change Password", this);
	public TopBar() {
		// TODO Auto-generated constructor stu
		FileResource image = new FileResource(new File(DBUtility.LOCATION+"Pictures/"+staff.getPicture()));
		Embedded pic = new Embedded();
		pic.setSource(image);
		pic.setWidth("40px");
		pic.setHeight("40px");
		pic.setStyleName("profilepic");
		Label title = new Label("USERNAME");
		title.setStyleName("username");
		HorizontalLayout iconsHolder = new HorizontalLayout();
		iconsHolder.setStyleName("topbar-buttons");
		iconsHolder.setSpacing(false);
		logout.setIcon(FontAwesome.POWER_OFF);
		this.setStyleName("topbar");
		
		
		//image holder for the picture of the student

		
		
		lock.setIcon(FontAwesome.LOCK);
		iconsHolder.addComponents(pic,operations,lock, logout);
		this.addComponent(iconsHolder);
		
		this.setComponentAlignment(iconsHolder, Alignment.TOP_RIGHT);

		this.setWidth("100%");

		lock.addClickListener(this);
		logout.addClickListener(this);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub

		if (event.getButton().getIcon().equals(FontAwesome.LOCK)) {
			UI.getCurrent().addWindow(new LockWindow());

		} else if (event.getButton().getIcon().equals(FontAwesome.POWER_OFF)) {

			UI.getCurrent().getSession().setAttribute("staff", null);
			UI.getCurrent().setContent(new Login(getUI()));
		}

	}

	@Override
	public void menuSelected(MenuItem selectedItem) {
		// TODO Auto-generated method stub
switch (selectedItem.getText()) {
case "Login":

	break;
case "Change Password":
	UI.getCurrent().addWindow(new PasswordChange());
	break;
default:
	break;
}
	}

}
