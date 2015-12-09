package com.component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import com.data.PolicyOwner;
import com.data.Staff;
import com.data.User;
import com.utility.DBUtility;
import com.utility.PasswordHash;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;

public class PasswordSetRole extends FormLayout implements ClickListener,
		ValueChangeListener {

	private static final long serialVersionUID = 1L;
	Button addRole = new Button("Add Role");
	TabSheet tab;
		Window win;
	TextField customerNumber = new TextField("Customer Number");
	final PasswordField password = new PasswordField("Password");
	final PasswordField confirmPassword = new PasswordField("Confirm Password");

	public PasswordSetRole( TabSheet tab, PolicyOwner owner)
			throws SQLException {
		addRole.setEnabled(false);

		this.tab = tab;
		// TODO Auto-generated constructor stub

		customerNumber.setValue(owner.getCustomerNumber());
		this.tab.addTab(this, "Set Password");
	
		
		addRole.setIcon(FontAwesome.PLUS);
		customerNumber.setEnabled(false);
		addRole.addClickListener(this);
		this.addComponents(password, confirmPassword,
				addRole);
		confirmPassword.addValueChangeListener(this);
confirmPassword.setImmediate(true);

	}

	
	
	public PasswordSetRole(Staff owner,Window win) throws SQLException {
		this.win=win;
		addRole.setEnabled(false);
		// TODO Auto-generated constructor stub
		password.addValidator(new StringLengthValidator("The name must be 6-10 letters (was {0})",6, 10, true));
		customerNumber.setValue(owner.getStaffNumber());
		confirmPassword.setEnabled(false);
		addRole.setIcon(FontAwesome.PLUS);
		customerNumber.setEnabled(false);
		addRole.addClickListener(this);
		this.addComponents(customerNumber, password, confirmPassword, addRole);
		password.addValueChangeListener(this);
		confirmPassword.addValueChangeListener(new ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				if(confirmPassword.getValue().equals(password.getValue())){
					addRole.setEnabled(true);
				
					password.setEnabled(true);
				} else{
					addRole.setEnabled(false);
				}
			}
		});
		confirmPassword.setImmediate(true);

	}
	
	
	
	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub

		User user = new User();
		// user.setRole(Integer.parseInt(roles.getValue().toString()));
		user.setStaffNumber(customerNumber.getValue());
		String passwords = null;

		try {
			passwords = PasswordHash.createHash(password.getValue());
			user.setPassword(passwords);
			
			if (DBUtility.updatePassword(user) == 1) {
				Notification.show("Password Update Successfully");
				win.close();
			} else {
				Notification.show("Password Update Not Successful",
						Type.ERROR_MESSAGE);
			}

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		if (confirmPassword.getValue().equals(password.getValue())) {
			addRole.setEnabled(true);
			confirmPassword.setEnabled(false);
			password.setEnabled(false);

		} else {
		
			confirmPassword.setEnabled(true);
			addRole.setEnabled(false);
		}
	}

}
