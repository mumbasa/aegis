package com.component;

import com.vaadin.ui.ComboBox;

public class TitleCombo extends ComboBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TitleCombo() {
		String[] titles = new String[] { "Ms", "Miss", "Mrs", "Mr", "Master",
				"Rev", "Fr (Father)", "Dr.",
				"Atty (Attorney)", "Prof", "Hon",
				"Pres (President)", "Gov (Governor)", "Coach", "Ofc (Officer)",
				"Maj (Major)", "Capt (Captain)", "Cmdr (Commander)",
				"Lt (Lieutenant)", "Lt Col",
				"Col", "Gen"
				
		};
		for (String a : titles) {
			this.addItem(a);
		}
		this.setCaption("Title");
this.setNullSelectionAllowed(false);
	}
}
