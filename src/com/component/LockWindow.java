package com.component;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import com.data.Staff;
import com.utility.DBUtility;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
 
public class LockWindow extends Window implements ClickListener{
	Staff staff = (Staff)UI.getCurrent().getSession().getAttribute("staff");	
	PasswordField password = new PasswordField();

	private static final long serialVersionUID = 1L;

	public LockWindow() {
		// TODO Auto-generated constructor stub
	this.setClosable(false);
    this.setDraggable(false);
    this.setResizable(false);
    this.setStyleName("lockWindow");
	this.setSizeFull();
	GridLayout grid = new GridLayout(7, 7);
	HorizontalLayout m = new HorizontalLayout();
	Button button = new Button();
	button.setIcon(FontAwesome.UNLOCK_ALT);
	
	Embedded image = new Embedded();
	FileResource file = new FileResource(new File(DBUtility.LOCATION+"/Pictures/"+staff.getPicture()));
	image.setSource(file);
	image.setStyleName("lockImage");
	image.setHeight("200px");
	image.setWidth("200px");
	VerticalLayout holder = new VerticalLayout(image,m);
	m.addComponents(password,button);
	button.addClickListener(this);
	grid.addComponent(holder, 3, 3);
	grid.setSizeFull();
	this.setContent(grid);
	
	
	
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		try {
			if(DBUtility.authenticateUser(staff.getStaffNumber(), password.getValue())){
			
			this.close();
			}else{
	
				Notification n = new Notification(" Unsuccessfully", Type.HUMANIZED_MESSAGE);
				n.show(UI.getCurrent().getPage());
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException
				| SQLException e) {
			// TODO Auto-generated catch block
		
			Notification n = new Notification(" Unsuccessfully", Type.ERROR_MESSAGE);
			n.show(UI.getCurrent().getPage());
			e.printStackTrace();
		}
	}

}
