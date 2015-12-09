package com.component;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededListener;

public class PictureUploader extends Upload implements Receiver,SucceededListener {
	/**
	 * 
	 */
	private String fileNameString;
	private static final long serialVersionUID = 1L;
	final String LOCATION =	VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	private Embedded image = new Embedded("Picture");
	 public File file=null;

	
	public PictureUploader(String cap,Embedded img,String holder){
		this.setCaption(cap);
		this.image=img;
		this.addSucceededListener(this);
		this.setReceiver(this);
		this.fileNameString = holder;
		
	}
	@Override
	public void uploadSucceeded(SucceededEvent event) {
		image.setSource(new FileResource(file));
		image.setVisible(true);
		float ratio = image.getHeight()/image.getWidth();
		image.setWidth("100");
		image.setHeight((ratio*100),Unit.PIXELS);
					
	System.out.println(file.getName());
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		// TODO Auto-generated method stub
		
		FileOutputStream stream =null;
		String extension =filename.substring(filename.length()-4, filename.length());
		
		//filename = "name";
		try {
			 file = new File(LOCATION+"/WEB-INF/images/owner/"+fileNameString+extension);
			 String s = file.getName();
			 fileNameString=s;
			 stream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stream;
		
	}
	public String getFileNameString() {
		return fileNameString;
	}


}
