package com.views;

import java.sql.SQLException;

import com.component.Charter;
import com.data.Staff;
import com.group.components.OccupationClassRateGrid;
import com.utility.DBUtility;
import com.utility.GRPDBUtility;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.GridLayout.OutOfBoundsException;
import com.vaadin.ui.GridLayout.OverlapsException;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class Dashboard extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Staff staffs = (Staff) UI.getCurrent().getSession().getAttribute("staff");

	public Dashboard() throws OverlapsException, OutOfBoundsException,
			SQLException {
		// TODO Auto-generated constructor stub
		@SuppressWarnings("deprecation")
		Label head = new Label("DASHBOARD - Today is "
				+ DBUtility.NOW.toGMTString());
		head.setStyleName("Heading");
		GridLayout lay = new GridLayout(4, 1);
		lay.setWidth("100%");
		VerticalLayout downs = new VerticalLayout();
		VerticalLayout dailyCash = new VerticalLayout();
		// dailyCash.setSizeFull();
		try {
			Label todayCash = new Label(""+DBUtility.getStaffTodayPolicyAmount(staffs));
			todayCash.setStyleName("figures");
			Label stt = new Label("Total Amount for Today");
			stt.setStyleName("headers");
			Label cashIcon = new Label("GH¢");
			cashIcon.setStyleName("headers");
			dailyCash.addComponents(stt, todayCash, cashIcon);
			dailyCash.setComponentAlignment(cashIcon, Alignment.MIDDLE_CENTER);
			dailyCash.setComponentAlignment(todayCash, Alignment.MIDDLE_CENTER);
			dailyCash.setComponentAlignment(stt, Alignment.MIDDLE_CENTER);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Label bankRatio = new Label("Bank Cheques");
		Label bankPercent = new Label("60");
		Label cashkRatio = new Label("Cash Payments");
		bankRatio.setStyleName("headers");
		cashkRatio.setStyleName("headers");
		Label cashPercent = new Label("40");
		bankPercent.setStyleName("figures");
		cashPercent.setStyleName("figures");
		Label percent = new Label("%");
		percent.setStyleName("headers");
		VerticalLayout bv = new VerticalLayout(bankRatio, bankPercent);
		bv.setComponentAlignment(bankRatio, Alignment.MIDDLE_CENTER);
		bv.setComponentAlignment(bankPercent, Alignment.MIDDLE_CENTER);

		VerticalLayout cv = new VerticalLayout(cashkRatio, cashPercent);
		cv.setComponentAlignment(cashkRatio, Alignment.MIDDLE_CENTER);
		cv.setComponentAlignment(cashPercent, Alignment.MIDDLE_CENTER);

		HorizontalLayout ho = new HorizontalLayout(bv, percent, cv);
		// ho.setComponentAlignment(bv, Alignment.MIDDLE_CENTER);
		ho.setSpacing(true);
		ho.setSizeFull();
		lay.addComponent(ho, 2, 0);
		try {
			lay.addComponent(Charter.getChart2(DBUtility
					.getYearByYearRegistrationByStaff(staffs)),
					0, 0);
		} catch (OverlapsException | OutOfBoundsException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lay.addComponent(dailyCash, 3, 0);
		lay.addComponent(Charter.createPieChart("Work Done Today",
				DBUtility.getDailyWorkDoneByStaff(staffs)), 1,
				0);
		lay.setSpacing(true);
		
		HorizontalLayout down = new HorizontalLayout();
		
		downs.addComponents(lay,down);
		lay.setHeight("400px");
	
		downs.setExpandRatio(down, 5);
		this.addComponents(head,downs);
		downs.setSizeFull();
		down.setSizeFull();
		Panel p = new Panel();
		Label tit = new Label("Notifications");
		tit.setStyleName("tit");
		VerticalLayout l = new VerticalLayout(tit, p);
		l.setWidth("100%");
		l.setSpacing(false);
		//down.addComponents(GRPDBUtility.groupAgebandTotal(GRPDBUtility.getGroupMembersList("GP201500001"),GRPDBUtility.getPolicyAgeBandRate("GP201500001")));
		down.addComponents();

	
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		UI.getCurrent().getPage().setTitle("Dashboard");
	}

}
