package com.component;

import com.data.Customer;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class CustomerInformation extends VerticalLayout{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomerInformation(){
		Button edit = new Button("Edit");
		final FormLayout customerForm = new FormLayout();
		final Customer customer = new Customer() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
		};
		BeanItem<Customer> cus = new BeanItem<Customer>(customer);
		Button update = new Button("Update");
		FieldGroup group = new FieldGroup(cus);
		Field<?> firstName = group.buildAndBind("firstName");
		Field<?> lastName = group.buildAndBind("lastName");
		Field<?> middleName = group.buildAndBind("middleName");
		Field<?> address = group.buildAndBind("address");
		Field<?> dob = group.buildAndBind("dob");
		Field<?> mobile = group.buildAndBind("mobile");
		Field<?> children = group.buildAndBind("children");
		Field<?> telephone = group.buildAndBind("telephone");
		Field<?> sex = group.buildAndBind("sex");
		Field<?> email = group.buildAndBind("email");
		Field<?> age = group.buildAndBind("age");
		Field<?> registered = group.buildAndBind("dateRegistered");
		Field<?> marital = group.buildAndBind("maritalStatus");
		Field<?> region = group.buildAndBind("region");
		Field<?> title = group.buildAndBind("title");
		Field<?> customerNumber = group.buildAndBind("customerNumber");
		Field<?> occupation = group.buildAndBind("occupation");
		Field<?> countryOfBirth = group.buildAndBind("countryOfBirth");
		customerForm.addComponents(customerNumber, title, firstName, lastName, age,
				email, sex, middleName, dob, mobile, address, children,
				telephone, registered, marital, region, occupation,
				countryOfBirth,update);	
		this.addComponents(edit,customerForm);
		//customerForm.setEnabled(false);
		
		this.setMargin(true);
		
		update.addClickListener(new ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				customerForm.setEnabled(true);
				Notification.show(customer.getAddress());
				
			}
		});
		
	}

}
