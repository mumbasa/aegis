package com.component;

import com.vaadin.ui.ComboBox;

public class CountriesCombo extends ComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CountriesCombo(String string) {
		String[] countries = new String[] { "United Arab Emirates",
				"Afghanistan", "Antigua and Barbuda", "Anguilla", "Albania",
				"Armenia", "Angola", "Antarctica", "Argentina",
				"American Samoa", "Austria", "Australia", "Aruba",
				"�land Islands", "Azerbaijan", "Bosnia and Herzegovina",
				"Barbados", "Bangladesh", "Belgium", "Burkina Faso",
				"Bulgaria", "Bahrain", "Burundi", "Benin", "Saint Barth�lemy",
				"Bermuda", "Brunei Darussalam", "Bolivia",
				"Caribbean Netherlands ", "Brazil", "Bahamas", "Bhutan",
				"Bouvet Island", "Botswana", "Belarus", "Belize", "Canada",
				"Cocos (Keeling) Islands", "Congo, Democratic Republic of",
				"Central African Republic", "Congo", "Switzerland",
				"C�te D�Ivoire", "Cook Islands", "Chile", "Cameroon", "China",
				"Colombia", "Costa Rica", "Cuba", "Cape Verde", "Cura�ao",
				"Christmas Island", "Cyprus", "Czech Republic", "Germany",
				"Djibouti", "Denmark", "Dominica", "Dominican Republic",
				"Algeria", "Ecuador", "Estonia", "Egypt", "Western Sahara",
				"Eritrea", "Spain", "Ethiopia", "Finland", "Fiji",
				"Falkland Islands", "Micronesia, Federated States of",
				"Faroe Islands", "France", "Gabon", "United Kingdom",
				"Grenada", "Georgia", "French Guiana", "Guernsey", "Ghana",
				"Gibraltar", "Greenland", "Gambia", "Guinea", "Guadeloupe",
				"Equatorial Guinea", "Greece",
				"South Georgia and the South Sandwich Islands", "Guatemala",
				"Guam", "Guinea-Bissau", "Guyana", "Hong Kong",
				"Heard and McDonald Islands", "Honduras", "Croatia", "Haiti",
				"Hungary", "Indonesia", "Ireland", "Israel", "Isle of Man",
				"India", "British Indian Ocean Territory", "Iraq", "Iran",
				"Iceland", "Italy", "Jersey", "Jamaica", "Jordan", "Japan",
				"Kenya", "Kyrgyzstan", "Cambodia", "Kiribati", "Comoros",
				"Saint Kitts and Nevis", "North Korea", "South Korea",
				"Kuwait", "Cayman Islands", "Kazakhstan",
				"Lao People�s Democratic Republic", "Lebanon", "Saint Lucia",
				"Liechtenstein", "Sri Lanka", "Liberia", "Lesotho",
				"Lithuania", "Luxembourg", "Latvia", "Libya", "Morocco",
				"Monaco", "Moldova", "Montenegro", "Saint-Martin (France)",
				"Madagascar", "Marshall Islands", "Macedonia", "Mali",
				"Myanmar", "Mongolia", "Macau", "Northern Mariana Islands",
				"Martinique", "Mauritania", "Montserrat", "Malta", "Mauritius",
				"Maldives", "Malawi", "Mexico", "Malaysia", "Mozambique",
				"Namibia", "New Caledonia", "Niger", "Norfolk Island",
				"Nigeria", "Nicaragua", "The Netherlands", "Norway", "Nepal",
				"Nauru", "Niue", "New Zealand", "Oman", "Panama", "Peru",
				"French Polynesia", "Papua New Guinea", "Philippines",
				"Pakistan", "Poland", "St. Pierre and Miquelon", "Pitcairn",
				"Puerto Rico", "Palestinian Territory, Occupied", "Portugal",
				"Palau", "Paraguay", "Qatar", "Reunion", "Romania", "Serbia",
				"Russian Federation", "Rwanda", "Saudi Arabia",
				"Solomon Islands", "Seychelles", "Sudan", "Sweden",
				"Singapore", "Saint Helena", "Slovenia",
				"Svalbard and Jan Mayen Islands", "Slovakia (Slovak Republic)",
				"Sierra Leone", "San Marino", "Senegal", "Somalia", "Suriname",
				"South Sudan", "Sao Tome and Principe", "El Salvador",
				"Saint-Martin (Pays-Bas)", "Syria", "Swaziland",
				"Turks and Caicos Islands", "Chad",
				"French Southern Territories", "Togo", "Thailand",
				"Tajikistan", "Tokelau", "Timor-Leste", "Turkmenistan",
				"Tunisia", "Tonga", "Turkey", "Trinidad and Tobago", "Tuvalu",
				"Taiwan", "Tanzania", "Ukraine", "Uganda",
				"United States Minor Outlying Islands", "United States",
				"Uruguay", "Uzbekistan", "Vatican",
				"Saint Vincent and the Grenadines", "Venezuela",
				"Virgin Islands (British)", "Virgin Islands (U.S.)", "Vietnam",
				"Vanuatu", "Wallis and Futuna Islands", "Samoa", "Yemen",
				"Mayotte", "South Africa", "Zambia", "Zimbabwe" };

		for (String a : countries) {
			this.addItem(a);
			
		}
		this.setNullSelectionAllowed(false);
		this.setValue("Ghana");
		this.setCaption(string);
	}
}