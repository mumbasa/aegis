package com.component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import com.utility.DBUtility;
import com.utility.PaymentDBUtil;
import com.vaadin.data.util.BeanItemContainer;
import com.data.PolicyPayment;
import com.data.Staff;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededListener;

public class ChargesUpload extends Upload implements Receiver,
		SucceededListener, ClickListener {
	/**
	 * 
	 */
	private String fileNameString;
	final String LOCATION = "/home/bryan/";
	private static final long serialVersionUID = 1L;
	Button approve = new Button("Approve");
	CssLayout layout;
	Grid grid = new Grid();
	public File file = null;
	Staff staffs = (Staff) UI.getCurrent().getSession().getAttribute("staff");
	String payee;
	BeanItemContainer<PolicyPayment> bankPayments = new BeanItemContainer<PolicyPayment>(
			PolicyPayment.class);

	public ChargesUpload(String cap, CssLayout layout,String payee) {
		this.payee=payee;
		this.setCaption(cap);
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
					String[] data = place.split(",");
					PolicyPayment pay = new PolicyPayment();
					pay.setPolicyNumber(data[0]);
					pay.setAmount(Double.parseDouble(data[1]));
					pay.setDatePaid(data[2].trim());
					pay.setPolicyType(data[0].substring(0, 2));
					pay.setBank_id(Integer.parseInt(data[3]));
					pay.setPayee(payee);
					pay.setStaffID(staffs.getStaffNumber());
					if (payee.equalsIgnoreCase("source")){
						pay.setPaymentModeId(3);
					}
					else{
						pay.setPaymentModeId(4);
					}
					//getting the policy activation status
					String a =DBUtility.getPolicyActivatedStatus(data[0]);
					if (a==null | a.equals("")){
						pay.setStatus("N");
					}else{
					pay.setStatus(DBUtility.getPolicyActivatedStatus(data[0]));
					}
					bankPayments.addBean(pay);
					System.out.println(place);
				}

				count++;

			}
			reader.close();
			re.close();
		} catch (IOException |SQLException e) {
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
		
		grid.setContainerDataSource(bankPayments);
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.setEditorEnabled(true);
		grid.removeColumn("chequeNumber");
		grid.removeColumn("clearedDate");
		grid.removeColumn("cleared");
		grid.removeColumn("paymentModeId");
		grid.removeColumn("paymentType");
		grid.removeColumn("staffID");
		grid.removeColumn("branchID");
		grid.setSizeFull();
		return grid;

	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		int stat =0;
		String policyNumber = "";
		for (Object id :grid.getSelectedRows()){
			
			PolicyPayment pay =(PolicyPayment) id;
			
			
			if (pay.getStatus().equals("N") & !policyNumber.equals(pay.getPolicyNumber())){
				
			
				
				System.out.println("ACTIVATING POLICY \t"+pay.getDatePaid());
				
				DBUtility.activatePolicy(pay.getPolicyNumber(),pay.getDatePaid());
				
					
				System.out.println("POLICY ACTIVATED");
			
			policyNumber= pay.getPolicyNumber();
		
			stat =PaymentDBUtil.insertPolicyStandingOrderPayment(pay);		
			}
			
			else{
				System.out.println("Passing over");
			stat =PaymentDBUtil.insertPolicyStandingOrderPayment(pay);			
			}
		}
		
		if(stat==1){
			Notification.show("Finished");
			UI.getCurrent().getNavigator().navigateTo("Batch Bank Payment");
		}
		else{
			Notification.show("Finished",Type.ERROR_MESSAGE);
		}
	}

}
