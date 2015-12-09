package com.form;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.data.GroupPolicy;
import com.utility.ComponentUtil;
import com.utility.GRPDBUtility;


public class Shee {
	private final static Logger log = Logger.getLogger(Shee.class.getName()); 
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		double k =  (6.0/8);
		GroupPolicy p = new GroupPolicy("GP201500001");
		//p.setMembers();
		System.err.println(p.getMembers().size());
	
		}
	

}
