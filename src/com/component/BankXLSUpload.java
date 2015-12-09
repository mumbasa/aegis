package com.component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededListener;

public class BankXLSUpload extends Upload implements SucceededListener, Receiver {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	File file;

	public String fileName;
	final String LOCATION = "/home/bryan/";
			
	Embedded image = new Embedded();
	TextField field;

	public BankXLSUpload(String caption) {
		
		this.addSucceededListener(this);
		this.setReceiver(this);
		this.setCaption(caption);
		
		this.setIcon(FontAwesome.UPLOAD);;

	}

	public BankXLSUpload(Embedded image, TextField field) {
		this.image = image;
		this.addSucceededListener(this);
		this.setReceiver(this);
		this.field = field;
		this.setButtonCaption(""+FontAwesome.UPLOAD);
		this.setIcon(FontAwesome.UPLOAD);;

	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		// TODO Auto-generated method stub
		FileOutputStream stream = null;

		try {
			file = new File(LOCATION
					+ "/Pictures/"
					+ System.currentTimeMillis()
					+ filename.substring(filename.length() - 4,
							filename.length()));
			fileName = file.getName();
			System.out.println("This is the file name " + filename);
			stream = new FileOutputStream(file);
		} catch (FileNotFoundException ex) {

			ex.printStackTrace();
		}

		return stream;
	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		// TODO Auto-generated method stub
		image.setSource(new FileResource(file));
		image.setVisible(true);
		image.setWidth("150");
		image.setHeight("200");
		// float ratio = image.getHeight()/image.getWidth();
		// image.setWidth(""+image.getWidth());
		// image.setHeight(""+image.getHeight());
		// field.setValue(getFileName());
		this.setEnabled(false);
	}

	public String getFileName() {
		return fileName;
	}
}
