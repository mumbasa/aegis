package com.utility;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailUtility {
	public static Email email = new SimpleEmail();

	public static void main(String[] args) throws EmailException {
		// TODO Auto-generated method stub

		String a = RandomStringUtils.randomAlphanumeric(9);
		System.out.println(a);

	}

	public static void sendActicationEmail(String customerNumber,
			String password, String receiver) {

		String message = "Your policy number has been activated today\n"
				+ " You can check your policy information at http://bryanic.cloudapp.net/Aegisclient\n"
				+ "your username :" + customerNumber + "   \npassword :"
				+ password + "\n Make sure you change your passowrd\n"
				+ "Thank you";

		try {
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(587);
			email.setAuthenticator(new DefaultAuthenticator("droid9user",
					"creeks87"));
			email.setSSLOnConnect(true);
			email.setFrom("droid9user@gmail.com");
			email.setSubject("Insurance Policy Activation");
			email.setMsg(message);
			email.addTo(receiver);
			email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(message);
		System.out.println("Email sent on " + email.getSentDate());

	}
}
