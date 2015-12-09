package com.form;

import tm.kod.widgets.numberfield.NumberField;

import com.component.HealthComponents;
import com.data.Health;
import com.data.PolicyHolder;
import com.utility.DBUtility;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class Healthform extends FormLayout implements ClickListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Health health;
	Window window;
	HealthComponents c = new HealthComponents("Slipped Disk etc","Slipped Disk/Spinal/Hernia /Heumatoid etc");
	HealthComponents e = new HealthComponents("Respiratory /Allergies Condtion","Respiratory/Urinary / Allergic Condition? ");
	HealthComponents d = new HealthComponents("Diarrhoea /Piles Condition","Chronic/Recurrent Diarrhea/Piles/Veins/circulatory ");
	HealthComponents b = new HealthComponents("Heart /Diabetes Condtion","High BP,Heart Condition,Rheumatic Fever/Diabetes?");
	
	HealthComponents f = new HealthComponents("Jaundice / STD","Jaundice, sexually trasmitted disease including genital sores?");
	HealthComponents g = new HealthComponents("Special Condtion","Have you been treated by a Specialist in the past three (3) years?? ");
	HealthComponents h = new HealthComponents("Disabilities","Do you have any disability or physical defect?");
	HealthComponents i = new HealthComponents("Hospital Admission","Admitted to a hospital for a Disability / Physical defect in the (3) yrs?");
	
	
	HealthComponents j = new HealthComponents("Smoking Status","Are you a smoker?");
	HealthComponents k = new HealthComponents("Family Disease","Any Family or Hereditary Sickness?");
	HealthComponents l = new HealthComponents("Medications","Are you taking any drugs or medicines at present for any disease or condition?");
	HealthComponents m = new HealthComponents("Drinking Status","Are you a drinker?");
	NumberField bpRate = new NumberField("BP Rate");
	
	public Button addBeneficiary  = new Button("ADD Beneficiary");
	public Button verify  = new Button("Verified");
	PolicyHolder holder;
	TabSheet sheet ;
	public Healthform(PolicyHolder holder,TabSheet sheet) {
		// TODO Auto-generated constructor stub
		this.holder=holder;
		this.sheet = sheet;
		this.addComponents(b,c,d,e,f,g,h,i,j,k,l,m,bpRate,addBeneficiary);
		addBeneficiary.addClickListener(this);
	}
	
	public Healthform(Health health) {
		// TODO Auto-generated constructor stub
		this.health=health;
		this.addComponents(b,c,d,e,f,g,h,i,j,k,l,m,bpRate);
		
		c.setStatus(health.isSlippedDisk());
		c.setReason(health.getSlippedDdiskCondition());
		b.setStatus(health.isHeart());
		b.setReason(health.getHeartCondition());
		d.setStatus(health.isBowels());
		d.setReason(health.getBowelsCondition());
		e.setStatus(health.isRespiratory());
		e.setReason(health.getRespiratoryCondition());
		
		f.setStatus(health.isSTD());
		f.setReason(health.getSTDCondition());
		g.setStatus(health.isSpecial());
		g.setReason(health.getSpecialCondition());
		bpRate.setValue(health.getBpRate()+"");
		j.setStatus(health.isSmoker());
		j.setReason(health.getSmokingFrequency());
		k.setStatus(health.isFamilySickness());
		k.setReason(health.getFamilySicknessName());
		i.setStatus(health.isBeenAdmitted());
		i.setReason(health.getAdmissionReason());
		m.setStatus(health.isDrinker());
		m.setReason(health.getDrinkingFrequency());
		
		
		
		addBeneficiary.addClickListener(this);
	}
	
		public Healthform(Health health,int a,Window window) {
		// TODO Auto-generated constructor stub
			this.window=window;
			this.health = health;
		this.addComponents(b,c,d,e,f,g,h,i,j,k,l,m,bpRate,verify);
		j.setStatus(health.isSmoker());
		j.setReason(health.getSmokingFrequency());
		k.setStatus(health.isFamilySickness());
		k.setReason(health.getFamilySicknessName());
		i.setStatus(health.isBeenAdmitted());
		i.setReason(health.getAdmissionReason());
		m.setStatus(health.isDrinker());
		m.setReason(health.getDrinkingFrequency());
		verify.addClickListener(this);
		addBeneficiary.addClickListener(this);
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
	if (event.getButton().getCaption().equalsIgnoreCase("ADD Beneficiary")){
		Health health = new Health();
		health.setSlippedDdiskCondition(c.getDetails());
		health.setRespiratoryCondition(e.getDetails());
		health.setBowelsCondition(d.getDetails());
		
		health.setHeartCondition(b.getDetails());
		health.setSTDCondition(f.getDetails());
		health.setSpecialCondition(g.getDetails());
		health.setDisabledConditon(h.getDetails());
		
		health.setSmokingFrequency(j.getDetails());
		health.setFamilySicknessName(k.getDetails());
		health.setAdmissionReason(i.getDetails());
		health.setDrinkingFrequency(m.getDetails());
		holder.setHealth(health);
		
		
	Notification.show(holder.getPolicy().getPremium()+"");
	System.out.println(holder.getPolicy().getPremium());
	if (holder.getPolicy().getInsuranceType()==3){
		BeneficiaryForm benficiary = new BeneficiaryForm(holder,sheet);
		sheet.addTab(benficiary, "Beneficiary");
		sheet.setSelectedTab(benficiary);
		
	}
	}else{
		health.setSlippedDdiskCondition(c.getDetails());
		health.setRespiratoryCondition(e.getDetails());
		health.setBowelsCondition(d.getDetails());
		
		health.setHeartCondition(b.getDetails());
		health.setSTDCondition(f.getDetails());
		health.setSpecialCondition(g.getDetails());
		health.setDisabledConditon(h.getDetails());
		
		health.setSmokingFrequency(j.getDetails());
		health.setFamilySicknessName(k.getDetails());
		health.setAdmissionReason(i.getDetails());
		health.setDrinkingFrequency(m.getDetails());
		health.setVerified("Y");
		if(DBUtility.verifyMedicalInformation(health)==1){
			Notification.show("Health Report Verified");
			window.close();
			UI.getCurrent().getNavigator().navigateTo("Medicals Approval");
		}
		
	}
	}
	
	


}
