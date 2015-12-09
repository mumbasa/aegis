package com.component;

import com.data.GroupPolicy;
import com.group.components.PolicyAgeBandSummary;
import com.utility.GRPDBUtility;
import com.vaadin.data.Container.Indexed;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class AgebandPolicy extends VerticalLayout implements ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Grid grid = new Grid();
	Button button = new Button("Add Age");
	GroupPolicy policy;
	TabSheet sheet;
	public AgebandPolicy(GroupPolicy policy) {
		// TODO Auto-generated constructor stub
		this.policy = policy;
		grid.addColumn("AgeBand", String.class);
		grid.addColumn("MaleRate", Double.class);
		grid.addColumn("FemaleRate", Double.class);
		
		grid.setSizeFull();
		double [] maleAgeBand = policy.getAgebandCharges().get(0);
		double[] femaleAgeBand = policy.getAgebandCharges().get(1);


		for (int i = 0; i < GRPDBUtility.AGE_BANDS.length; i++) {
			grid.addRow(new Object[] { GRPDBUtility.AGE_BANDS[i], maleAgeBand[i], femaleAgeBand[i] });
		}
		
		this.setSpacing(true);
		this.addComponents(grid);
	
	}
	
	
	public AgebandPolicy(GroupPolicy policy,TabSheet sheet) {
		// TODO Auto-generated constructor stub
		this.policy = policy;
		this.sheet=sheet;
		grid.addColumn("AgeBand", String.class);
		grid.addColumn("MaleRate", Double.class);
		grid.addColumn("FemaleRate", Double.class);
		grid.setEditorEnabled(true);
		grid.setSizeFull();
		try {
			grid.saveEditor();
		} catch (CommitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < GRPDBUtility.AGE_BANDS.length; i++) {
			grid.addRow(new Object[] { GRPDBUtility.AGE_BANDS[i], 0.0, 0.0 });
		}
		button.addClickListener(this);
		this.setSpacing(true);
		this.addComponents(grid, button);
	}

	@Override
	public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
		// TODO Auto-generated method stub
		int stat=0;
		Indexed container = grid.getContainerDataSource();
		for (Object id : container.getItemIds()) {
			Item item = (Item) container.getItem(id);
			String ageband = item.getItemProperty("AgeBand").getValue().toString();
			double maleRate = (double) item.getItemProperty("MaleRate").getValue();
			double femaleRate = (double) item.getItemProperty("FemaleRate").getValue();
			if (GRPDBUtility.insertAgeBand(ageband, maleRate, femaleRate,policy.getGroupPolicyNumber()) !=1){
				stat=1;
			}
		}
			if (stat==0){
				Notification.show("Successfully Done");
				PolicyAgeBandSummary band = new PolicyAgeBandSummary(policy,sheet);
				
				sheet.addTab(band,"Policy Ageband Details");
		
				sheet.setSelectedTab(band);
			}else{
				Notification.show("Successfully not Done",Type.ERROR_MESSAGE);
			}
			
		}
	


}
