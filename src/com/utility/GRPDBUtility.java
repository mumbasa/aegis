package com.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.component.FilterableGrid;
import com.data.Group;
import com.data.GroupMember;
import com.data.GroupPolicy;
import com.data.Policy;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Grid;

public class GRPDBUtility {

	public final static String[] AGE_BANDS = new String[]{"0-29","30-34","35-39","40-44","45-49","50-54","55-59","60-64","65-69","70-74","75-79"};
	public final static String[] OCCUPATION_CLASSES = ComponentUtil.getOccupationClassRates().keySet().toArray(new String[ComponentUtil.getOccupationClass().size()]);
	public final static Double[] OCCUPATION_CLASSES_RATES = ComponentUtil.getOccupationClassRates().values().toArray(new Double[ComponentUtil.getOccupationClass().size()]);

	
	public static void main(String[] args) {

	System.out.println(getExpectedClaimAmount(GRPDBUtility.getGroupMembersList("GP201500001"), getPolicyAgeBandRate("GP201500001")));
	
	}
	
	
	
	
	public static int insertGroup(Group group) {
		
		int status = 0;
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;

		String state = "INSERT INTO group_client(company, address, tel, latitude, associate, business, representative, region_id,email,longitude) "
				+ "VALUES(?,?,?,?,?,?,?,now(),?,?,?)";

		
		try {
			ps = connection.prepareStatement(state);
			
			ps.setString(1, group.getCompanyName());
			ps.setString(2, group.getAddress());
			ps.setString(3,group.getTelephone());
			ps.setString(4,group.getLatitude());
			ps.setInt(5, group.getSubsidiaryID());
			ps.setInt(6,group.getIndustryID());
			ps.setString(7, group.getRepresentative());
			ps.setString(8, group.getRegion());
			ps.setString(9, group.getEmail());
			ps.setString(10, group.getLocation());
			
			if (ps.executeUpdate() == 1) {
				if(groupNumberGeneration(group)==1){
					
					
					
					
					status=1;
				}
			
				System.out.println("Excwelend");

			} else {
				System.out.println("ahist");

			}
			ps.close();
			connection.close();
			pool.freeConnection();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return status;
	}
	
	
	public static int insertGroupMember(GroupMember member) {
		int status = 0;
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		String state = "INSERT INTO insurance.group_member (first_name, last_name, middle_name, occupation_class, dob, gender, group_id, staff_number, date_added) VALUES (?,?,?,?,?,?,?,?,now());";
		try {
			ps = connection.prepareStatement(state);
			ps.setString(1, member.getFirstName());
			ps.setString(2,member.getLastName());
			ps.setString(3, member.getMiddleName());
			ps.setString(4,member.getOccupationClass());
			ps.setString(5,member.getDob());
			ps.setString(6,member.getGender());
			ps.setString(7, member.getGroupId());
			ps.setString(8, member.getStaffNumber());
			if (ps.executeUpdate() == 1) {
				
							System.out.println("Excwelend");

			} else {
				System.out.println("ahist");

			}
			ps.close();
			connection.close();
			pool.freeConnection();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return status;
	}
	
	
	
	
	public static int insertGroupPolicyPremium(GroupPolicy policy) {
		int status = 0;
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		String state = "INSERT INTO insurance.group_policy_premium (initial_expected_claims, occupation_adjustment, expected_claims, profit_internal_expense, commission_premium_tax, premium_per_month, payroll, premium_per_thousand,group_policy_number) VALUES (?,?,?,?,?,?,?,?,?);";
		
		
		String update = "UPDATE group_client_policy SET is_approved='Y' WHERE group_policy_number=?";
		
		try {
			ps = connection.prepareStatement(state);
			ps.setDouble(1, policy.getInitialExpectedMonthlyClaims());
			ps.setDouble(2, policy.getOccupationAdjustment());
			ps.setDouble(3, policy.getExpectedClaims());
			ps.setDouble(4, policy.getProfitPlusExpense());
			ps.setDouble(5, policy.getCommissionPlusTax());
			ps.setDouble(6, policy.getMonthlypremium());
			ps.setDouble(7, policy.getTotalSalary());
			ps.setDouble(8, policy.getPremiumPer1000());
			ps.setString(9, policy.getGroupPolicyNumber());
			if(ps.executeUpdate()==1){
				ps2 = connection.prepareStatement(update);
				ps2.setString(1,policy.getGroupPolicyNumber());
				status=ps2.executeUpdate();	
				
			}		
			ps.close();
			ps2.close();
			connection.close();
			pool.freeConnection();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return status;
	}
	
	
		
	
	
	public static int insertGroupMemberPolicy(GroupMember member,GroupPolicy policy) {
		int status = 0;
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		String state = "INSERT INTO `insurance`.`group_member_policy` (`group_member_number`, `group_policy_number`, `salary`, `occupation_class`, `sum_assured`) VALUES ((SELECT group_member_id FROM insurance.group_member where staff_number=? and group_id=? and gender=?),?,?,?,?)";
		try {
			ps = connection.prepareStatement(state);
			ps.setString(1, member.getStaffNumber());
			ps.setString(2,policy.getGroupNumber());
			ps.setString(3, member.getGender());
			ps.setString(4,policy.getGroupPolicyNumber());
			ps.setDouble(5, member.getSalary());
			ps.setString(6,member.getOccupationClass());
			ps.setDouble(7,member.getSumAssured());
			status=ps.executeUpdate();
			ps.close();
			connection.close();
			//pool.freeConnection();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return status;
	}
	
	
	public static int insertAgeBand(String ageband,double maleRate,double female,String policyNumber) {
		int status = 0;
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		String state = "INSERT INTO age_band (age_band, female, male,group_policy_number) VALUES (?,?,?,?)";
		try {
			ps = connection.prepareStatement(state);
			ps.setString(1, ageband);
			ps.setDouble(2,maleRate);
			ps.setDouble(3,female);
			ps.setString(4, policyNumber);
			status=ps.executeUpdate();
			ps.close();
			connection.close();
			pool.freeConnection();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return status;
	}
	
	
	
	public static int insertGroup(Group group,GroupPolicy policy) {
		int status = 0;
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;

		String state = "INSERT INTO group_client(company, address, tel, latitude, associate, business, representative, date_added,region_id,email,longitude) "
				+ "VALUES(?,?,?,?,?,?,?,now(),?,?,?)";

		String policyInsert = "INSERT INTO group_client_policy(group_client_number, staff_eligible, benefit, payment_frequency, payment_mode, institution_contribution, member_contribution,policy_type_id,date_added,currency,is_approved,multiplier) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,'N',?)";

		try {
		
			ps = connection.prepareStatement(state);
			
			ps.setString(1, group.getCompanyName());
			ps.setString(2, group.getAddress());
			ps.setString(3,group.getTelephone());
			ps.setString(4,group.getLatitude());
			ps.setInt(5, group.getSubsidiaryID());
			ps.setInt(6,group.getIndustryID());
			ps.setString(7, group.getRepresentative());
			ps.setString(8, group.getRegion());
			ps.setString(9, group.getEmail());
			ps.setString(10, group.getLocation());
			
			if (ps.executeUpdate() == 1) {
				if(groupNumberGeneration(group)==1){
				
					ps2 = connection.prepareStatement(policyInsert);
					ps2.setString(1,group.getGroupNumber());
					ps2.setInt(2,policy.getStaffPopulation());
					ps2.setDouble(3, policy.getBenefits());
					ps2.setString(4, policy.getPaymentFrequency());
					ps2.setInt(5,policy.getPaymentTypeID());
				
					ps2.setDouble(6, policy.getInstitutionalContribution());
					ps2.setDouble(7, policy.getMemberContribution());
					ps2.setString(8, policy.getPolicyType());
					ps2.setString(9, policy.getDateRegistered());
					ps2.setString(10,policy.getCurrency());
					ps2.setDouble(11, policy.getMultiplier());

					policy.setGroupNumber(group.getGroupNumber());
					if (ps2.executeUpdate()==1){
						
						if(groupPolicyNumberGeneration(policy)==1){
							status=1;
						 }
						ps2.close();
						
					}
					
					
				}
			
				System.out.println("Excwelend");

			} else {
				System.out.println("ahist");

			}
			
			ps.close();
			connection.close();
			pool.freeConnection();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return status;
	}
	

	public static int insertGroupKnown(Group group,GroupPolicy policy) {
		int status = 0;
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps2 = null;

		String policyInsert = "INSERT INTO group_client_policy(group_client_number, staff_eligible, benefit, payment_frequency, payment_mode, institution_contribution, member_contribution,policy_type_id,date_added,currency,is_approved,multiplier) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,'N',?)";

		try {
		
					
					ps2 = connection.prepareStatement(policyInsert);
					ps2.setString(1,group.getGroupNumber());
					ps2.setInt(2,policy.getStaffPopulation());
					ps2.setDouble(3, policy.getBenefits());
					ps2.setString(4, policy.getPaymentFrequency());
					ps2.setInt(5,policy.getPaymentTypeID());
				
					ps2.setDouble(6, policy.getInstitutionalContribution());
					ps2.setDouble(7, policy.getMemberContribution());
					ps2.setString(8, policy.getPolicyType());
					ps2.setString(9, policy.getDateRegistered());
					ps2.setString(10,policy.getCurrency());
					ps2.setDouble(11, policy.getMultiplier());
					policy.setGroupNumber(group.getGroupNumber());
					
					if (ps2.executeUpdate()==1){
						
						if(groupPolicyNumberGeneration(policy)==1){
							status=1;
						 }
						ps2.close();
						System.out.println("Excwelend");
					}
				
			 else {
				System.out.println("ahist");

			}
			
		
			connection.close();
			pool.freeConnection();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return status;
	}
	
	public static Group getGroupDetail(String customerNumber)
			throws SQLException {
		Group owner = null;
		String statement = "SELECT company, address, tel, latitude, associate, business, representative, group_number,"
				+ " region_id, email, longitude from group_client where group_number=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, customerNumber);
		ResultSet set = query.executeQuery();
		if (set.next()) {
			owner = new Group();
			owner.setCompanyName(set.getString(1));
			owner.setAddress(set.getString(2));
			owner.setTelephone(set.getString(4));
			owner.setLatitude(set.getString(4));
			owner.setSubsidiaryID(set.getInt(5));
			owner.setIndustry(set.getString(6));
			owner.setRepresentative(set.getString(7));
			owner.setGroupNumber(set.getString(8));
			owner.setRegion(set.getString(9));
			owner.setEmail(set.getString(10));
			owner.setLongitude(set.getString(11));
		}
		set.close();
		conn.close();

		return owner;

	}
	
	
	
	
	
	public static GroupPolicy getPolicyDetail(String customerNumber)
			throws SQLException {
		GroupPolicy owner = null;
		String statement = "Select group_client_number, staff_eligible, benefit,payment_frequency, payment_mode, effective_date, end_date, "
				+ "institution_contribution, member_contribution, group_policy_number, policy_type_id from group_client_policy where group_client_number=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, customerNumber);
		ResultSet set = query.executeQuery();
		if (set.next()) {
			owner = new GroupPolicy(set.getString(10));
			
			owner.setGroupNumber(set.getString(1));
			owner.setStaffPopulation(set.getInt(2));
			owner.setBenefits(set.getDouble(3));
			owner.setPaymentFrequency(set.getString(4));
			owner.setPaymentTypeID(set.getInt(5));
			owner.setDateActivated(set.getString(6));
			owner.setMaturityDate(set.getString(7));
			owner.setInstitutionalContribution(set.getDouble(8));
			owner.setMemberContribution(set.getDouble(9));
			owner.setGroupPolicyNumber(set.getString(10));
			owner.setPolicyType(set.getString(11));
		}
		set.close();
		conn.close();
		owner.setMembers();
		return owner;

	}
	
	
	public static ArrayList<GroupPolicy> getPolicyAllDetails(String customerNumber)
			throws SQLException {
		ArrayList<GroupPolicy> policies = new ArrayList<GroupPolicy>();
		 
		String statement = "Select group_client_number, staff_eligible, benefit,payment_frequency, payment_mode, effective_date, end_date,institution_contribution, member_contribution, "
				+ "p.group_policy_number, policy_type_id,group_client_number, staff_eligible, benefit,multiplier, "
				+ "profit, expense_charge, commission, policy_premium_tax,initial_expected_claims, occupation_adjustment, "
				+ "expected_claims, profit_internal_expense, commission_premium_tax, premium_per_month, payroll, premium_per_thousand,currency from group_client_policy as p join group_policy_premium as r on p.group_policy_number=r.group_policy_number where group_client_number=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, customerNumber);
		ResultSet set = query.executeQuery();
		while (set.next()) {
			GroupPolicy owner = new GroupPolicy(set.getString(10));
			
			owner.setGroupNumber(set.getString(1));
			owner.setStaffPopulation(set.getInt(2));
			owner.setBenefits(set.getDouble(3));
			owner.setPaymentFrequency(set.getString(4));
			owner.setPaymentTypeID(set.getInt(5));
			owner.setDateActivated(set.getString(6));
			owner.setMaturityDate(set.getString(7));
			owner.setInstitutionalContribution(set.getDouble(8));
			owner.setMemberContribution(set.getDouble(9));
			owner.setGroupPolicyNumber(set.getString(10));
			owner.setPolicyType(set.getString(11));
			owner.setGroupNumber(set.getString(12));
			owner.setStaffPopulation(set.getInt(13));
			owner.setBenefits(set.getDouble(14));
			owner.setMembers();
			owner.setMulitiplier(set.getDouble(15));
			owner.setProfit(set.getDouble(16));
			owner.setInternalExpenses(set.getDouble(17));
			owner.setCommission(set.getDouble(18));
			owner.setPremiumTax(set.getDouble(19));
			owner.setInitialExpectedMonthlyClaims(set.getDouble(20));
			owner.setOccupationAdjustment(set.getDouble(21));
			owner.setCurrency(set.getString(22));
			owner.setExpectedClaims();
			owner.setProfitPlusExpense();
			owner.setCommissionPlusTax();
			owner.setMonthlypremium();
			owner.setPremiumPer1000();
			owner.setMembers();
			System.err.println("Running");
			owner.setAgebandCharges();
			policies.add(owner);
			
		}
		set.close();
		conn.close();
		
		return policies;

	}
	
	
	public static ArrayList<double[]> getPolicyAgeBandRate(String customerNumber){
		double [] maleAgeBand = new double[AGE_BANDS.length];
		double[] femaleAgeBand = new double[AGE_BANDS.length];
		ArrayList<double[]> data = new ArrayList<double[]>();
		String statement = "SELECT  female, male FROM insurance.age_band where group_policy_number=?;";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query;
		try {
			query = conn.prepareStatement(statement);
			query.setString(1, customerNumber);
			ResultSet set = query.executeQuery();
			int a=0;
			while (set.next()) {
				maleAgeBand[a]=set.getDouble(2);
				femaleAgeBand[a]=set.getDouble(2);
				a++;
			}
			set.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		data.add(maleAgeBand);
		data.add(femaleAgeBand);
		return data;

	}	
	public static void getPolicyCharges (GroupPolicy policy){
		String statement = "SELECT  profit, expense_charge, commission, policy_premium_tax FROM group_client_policy where group_policy_number=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query;
		try {
			query = conn.prepareStatement(statement);
			query.setString(1, policy.getGroupPolicyNumber());
			ResultSet set = query.executeQuery();

			if (set.next()) {
			policy.setProfit(set.getDouble(1));
			policy.setInternalExpenses(set.getDouble(2));
			policy.setCommission(set.getDouble(3));
			policy.setPremiumTax(set.getDouble(4));
			}
			query.close();
			set.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	

	}	
	
	
	
	public static BeanItemContainer<GroupMember> getGroupMembers(String customerNumber)
		 {
		BeanItemContainer<GroupMember>  members = new BeanItemContainer<GroupMember>(GroupMember.class);
		String statement = "SELECT group_member_id, first_name, last_name,  occupation_class, dob, gender, staff_number, date_added FROM insurance.group_member where group_id=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query;
		try {
			query = conn.prepareStatement(statement);
			query.setString(1, customerNumber);
			ResultSet set = query.executeQuery();
			while (set.next()) {
				GroupMember m = new GroupMember();
				m.setGroupMemberNumber(set.getString(1));
				m.setFirstName(set.getString(2));
				m.setLastName(set.getString(3));
				m.setOccupationClass(set.getString(4));
				m.setDob(set.getString(5));
				m.setGender(set.getString(6));
				m.setStaffNumber(set.getString(7));
				members.addBean(m);
				
			} 
			set.close();
			conn.close();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		return members;

	}	
	
	
	
	public static ArrayList<GroupMember> getGroupPolicyMembers(String customerNumber)
	 {
		ArrayList<GroupMember>  members = new ArrayList<GroupMember>();
	String statement = "SELECT  group_member_id, first_name, last_name,  m.occupation_class, dob, gender, staff_number, date_added FROM insurance.group_member as m join group_member_policy as p on p.group_member_number=group_member_id  where group_policy_number=?";
	ConnectionPool pool = new ConnectionPool();
	Connection conn = pool.getConnectionFromPool();
	PreparedStatement query;
	try {
		query = conn.prepareStatement(statement);
		query.setString(1, customerNumber);
		ResultSet set = query.executeQuery();
		while (set.next()) {
			GroupMember m = new GroupMember();
			m.setGroupMemberNumber(set.getString(1));
			m.setFirstName(set.getString(2));
			m.setLastName(set.getString(3));
			m.setOccupationClassId(set.getInt(4));
			m.setDob(set.getString(5));
			m.setGender(set.getString(6));
			m.setStaffNumber(set.getString(7));
			members.add(m);
			
		} 
		set.close();
		conn.close();
		
	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	

	return members;

}
	
	public static BeanItemContainer<GroupMember> getPolicyMembers(String customerNumber)
	 {
		BeanItemContainer<GroupMember>  members = new BeanItemContainer<GroupMember>(GroupMember.class);
	String statement = "SELECT  group_member_id, first_name, last_name,  m.occupation_class, dob, gender, staff_number, date_added FROM insurance.group_member as m join group_member_policy as p on p.group_member_number=group_member_id  where group_policy_number=?";
	ConnectionPool pool = new ConnectionPool();
	Connection conn = pool.getConnectionFromPool();
	PreparedStatement query;
	try {
		query = conn.prepareStatement(statement);
		query.setString(1, customerNumber);
		ResultSet set = query.executeQuery();
		while (set.next()) {
			GroupMember m = new GroupMember();
			m.setGroupMemberNumber(set.getString(1));
			m.setFirstName(set.getString(2));
			m.setLastName(set.getString(3));
			m.setOccupationClass(set.getString(4));
			m.setDob(set.getString(5));
			m.setGender(set.getString(6));
			m.setStaffNumber(set.getString(7));
			members.addBean(m);
			
		} 
		set.close();
		conn.close();
		
	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	

	return members;

}	
	
	
	public static ArrayList<GroupMember> getGroupMembersList(String customerNumber)
	 {
		ArrayList<GroupMember>  members = new ArrayList<GroupMember>();
	String statement = "SELECT group_member_id, first_name, last_name,  m.occupation_class, dob, gender, staff_number, salary FROM insurance.group_member as m join group_member_policy as p on m.group_member_id=p.group_member_number where group_policy_number=?";
	ConnectionPool pool = new ConnectionPool();
	Connection conn = pool.getConnectionFromPool();
	PreparedStatement query;
	try {
		query = conn.prepareStatement(statement);
		query.setString(1, customerNumber);
		ResultSet set = query.executeQuery();
		while (set.next()) {
			GroupMember m = new GroupMember();
			m.setGroupMemberNumber(set.getString(1));
			m.setFirstName(set.getString(2));
			m.setLastName(set.getString(3));
			m.setOccupationClassId(set.getInt(4));
			m.setDob(set.getString(5));
			m.setGender(set.getString(6));
			m.setStaffNumber(set.getString(7));
			m.setSalary(set.getDouble(8));
			members.add(m);
			
		} 
		query.close();
		set.close();
		conn.close();
		conn.close();
		
	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	

	return members;

}

	public static int groupNumberGeneration(Group group) {
		int stat=0;
		
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		String state = "UPDATE group_client SET group_number = CONCAT(YEAR(CURDATE()),lpad(id,5,'0'),region_id) WHERE company= ? AND tel= ? and region_id=?";
		String groupNumberSelect = "SELECT group_number from group_client  WHERE company= ? AND tel= ? and region_id=? order by id desc limit 1";
		try {
			
			ps = connection.prepareStatement(state);
			ps.setString(1, group.getCompanyName());
			ps.setString(2, group.getTelephone());
			ps.setString(3, group.getRegion());
			
			if (ps.executeUpdate()==1){
			
				ps2 = connection.prepareStatement(groupNumberSelect);
			
				ps2.setString(1, group.getCompanyName());
				ps2.setString(2, group.getTelephone());
				ps2.setString(3, group.getRegion());
				ResultSet set = ps2.executeQuery();
				if (set.next()){
				
					group.setGroupNumber(set.getString(1));
				stat = 1;
				}
				set.close();
				ps2.close();
			}
			ps.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
		System.out.println("Group number os "+group.getGroupNumber());
		return stat;
	}
	
	

	public static int insertPolicyCharges(GroupPolicy groupPolicy) {
		int stat=0;
		
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		String state = "UPDATE group_client_policy SET profit=?, expense_charge=?, commission=?,policy_premium_tax=? WHERE group_policy_number=?";
		try {
			
			ps = connection.prepareStatement(state);
			ps.setDouble(1, groupPolicy.getProfit());
			ps.setDouble(2, groupPolicy.getInternalExpenses());
			ps.setDouble(3, groupPolicy.getCommission());
			ps.setDouble(4, groupPolicy.getPremiumTax());
			ps.setString(5,groupPolicy.getGroupPolicyNumber());
			stat=ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	
		return stat;
	}
	
	public static int insertApprovePolicy(GroupPolicy groupPolicy) {
		int stat=0;
		
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		String state = "UPDATE group_client_policy SET is_approved='Y' WHERE group_policy_number=?";
		try {
			
			ps = connection.prepareStatement(state);
			ps.setString(1,groupPolicy.getGroupPolicyNumber());
			stat=ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	
		return stat;
	}
	
	
	public static  FilterableGrid groupAgebandGeneration(ArrayList<GroupMember> members) {
		FilterableGrid grid = new FilterableGrid();
		grid.addColumn("AgeBand",String.class);
		grid.addColumn("MaleCount",Integer.class);
		grid.addColumn("MaleSumAmount",Double.class);
		grid.addColumn("FemaleCount",Integer.class);
		grid.addColumn("FemaleSumAmount",Double.class);
		grid.addColumn("TotalCount",Integer.class);
		grid.addColumn("TotalCountSum",Double.class);
		int[] maleAgeBand = new int[AGE_BANDS.length];
		int[] femaleAgeBand = new int[AGE_BANDS.length];
		int[] totalAgeBand = new int[AGE_BANDS.length];
		double[] femaleAgeBandSum = new double[AGE_BANDS.length];
		double[] totalAgeBandSum = new double[AGE_BANDS.length];
		double[] maleAgeBandSum = new double[AGE_BANDS.length];
		grid.setHeightByRows(30);
		for (GroupMember member : members){
			int age = DBUtility.getAge(member.getDob());
			totalAgeBand[getAgeBand(age)]++;
			totalAgeBandSum[getAgeBand(age)]+=member.getSalary();
			if (member.getGender().equalsIgnoreCase("M")){
				maleAgeBand[getAgeBand(age)]++;
				maleAgeBandSum[getAgeBand(age)]+=member.getSalary();
			}else{
				femaleAgeBand[getAgeBand(age)]++;
				femaleAgeBandSum[getAgeBand(age)]+=member.getSalary();
			}
			
		}
		for (int i =0;i<AGE_BANDS.length;i++){
			grid.addRow(new Object[]{AGE_BANDS[i],maleAgeBand[i],maleAgeBandSum[i],femaleAgeBand[i],femaleAgeBandSum[i],totalAgeBand[i],totalAgeBandSum[i]});
		}
		grid.addRow(new Object[]{"Total",sumArray(maleAgeBand),sumArray(maleAgeBandSum),sumArray(femaleAgeBand),sumArray(femaleAgeBandSum),sumArray(totalAgeBand),sumArray(totalAgeBandSum)});
		System.out.println(maleAgeBand[1]);
		return grid;
	}
	
	
	
	public static  Grid groupAgebandTotal(ArrayList<GroupMember> members,ArrayList<double[]> data) {
		Grid grid = new Grid();
		grid.setSizeFull();
		grid.addColumn("AgeBand",String.class);
		grid.addColumn("MaleRate",Double.class);
		grid.addColumn("FemaleRate",Double.class);
		grid.addColumn("MaleSumAmount",Double.class);
		grid.addColumn("FemaleSumAmount",Double.class);
		grid.addColumn("MaleTotalAmount",Double.class);
		grid.addColumn("FemalTotalAmount",Double.class);
		grid.addColumn("TotalAmount",Double.class);
		double[] maleAgeBandRate = data.get(0);

		double[] femaleAgeBandRate = data.get(1);
		double[] maleAgeBandSum = new double[AGE_BANDS.length];
		double[] femaleAgeBandSum = new double[AGE_BANDS.length];
		

		double[] totalAgeBandSum = new double[AGE_BANDS.length];
		
		
		for (GroupMember member : members){
			int age = DBUtility.getAge(member.getDob());
	

			if (member.getGender().equalsIgnoreCase("M")){
				System.out.println(member.getSalary());
				maleAgeBandSum[getAgeBand(age)]+=member.getSalary();
			}else{
		
				femaleAgeBandSum[getAgeBand(age)]+=member.getSalary();
			}
			
		}
		for (int i =0;i<AGE_BANDS.length;i++){
		double maleAmount =maleAgeBandSum[i]*maleAgeBandRate[i];
		totalAgeBandSum[i]+=maleAmount;
		double femaleAmount =femaleAgeBandSum[i]*femaleAgeBandRate[i];
		totalAgeBandSum[i]+=femaleAmount;
		//grid.addRow(new Object[]{AGE_BANDS[i],0.1,0.1});

		grid.addRow(new Object[]{AGE_BANDS[i],maleAgeBandRate[i],femaleAgeBandRate[i],maleAgeBandSum[i],femaleAgeBandSum[i],maleAmount,femaleAmount,maleAmount+femaleAmount});
		}
		grid.addRow(new Object[]{"Total",maleAgeBandRate[5],femaleAgeBandRate[5],sumArray(maleAgeBandSum),sumArray(femaleAgeBandSum),0.0,0.0,sumArray(totalAgeBandSum)});
		
		System.out.println(sumArray(totalAgeBandSum)+" ===> total");
		return grid;
	}
	
	
	public static  FilterableGrid groupAgeBandFees() {
		FilterableGrid grid = new FilterableGrid();
		grid.addColumn("AgeBand",String.class);
		grid.addColumn("MaleRate",Double.class);
		grid.addColumn("FemaleRate",Double.class);
		grid.setEditorEnabled(true);


		
		for (int i =0;i<AGE_BANDS.length;i++){
			grid.addRow(new Object[]{AGE_BANDS[i],0.0,0.0});
		}
		
		return grid;
	}
	
	
	
	
	
	
	
	public static double sumArray(double[] ar){
		double sum = 0;
		for (double a : ar){
			sum+=a;
		}
		return sum;
	}
	
	public static int sumArray(int[] ar){
		int sum = 0;
		for (int a : ar){
			sum+=a;
		}
		return sum;
	}
	
	
	
	
	
	public static int groupPolicyNumberGeneration(GroupPolicy policy) {
		int stat=0;
		
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		String state = "UPDATE group_client_policy SET group_policy_number = CONCAT(policy_type_id,YEAR(CURDATE()),lpad(group_policy_id,5,'0')) WHERE group_client_number= ? AND date_added= ?";
		String groupPolicyNumber = "SELECT group_policy_number from group_client_policy where group_client_number=? order by group_policy_id desc limit 1";
		try {
			System.out.println("Group number os >"+policy.getGroupNumber());
			ps = connection.prepareStatement(state);
			ps.setString(1, policy.getGroupNumber());
			ps.setString(2, policy.getDateRegistered());
			if(ps.executeUpdate()==1){
				ps2 = connection.prepareStatement(groupPolicyNumber);
				ps2.setString(1,policy.getGroupNumber());
				ResultSet set = ps2.executeQuery();
				if(set.next()){
					policy.setGroupPolicyNumber(set.getString(1));
					stat=1;
				}
				
			}
			ps.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	
		return stat;
	}
	
	public static int groupPolicyMemberGenerations(Policy policy) {
		int stat=0;
		
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;

		String state = "UPDATE customer_policy SET policy_number = CONCAT(policy_type_id,YEAR(CURDATE()),lpad(client_policy_id,5,'0'),region_id) WHERE customer_number= ? AND premium= ?";

		try {
			
			ps = connection.prepareStatement(state);
			ps.setString(1, policy.getCustomerNumber());
			ps.setDouble(2, policy.getPremium());
			stat= ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return stat;
	}
	
	
	public static int insertGroupBankAccount(Group group) {
		int stat=0;
		
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;

		String state = "INSERT INTO insurance.client_bank (client_id, bank_id, account_number,account_name, branch, region, is_group) VALUES (?,?,?,?,?,?,'Y')";

		try {
			
			ps = connection.prepareStatement(state);
			ps.setString(1, group.getGroupNumber());
			ps.setInt(2, group.getBank().getBankID());
			ps.setString(3, group.getBank().getAccountNumber());
			ps.setString(4, group.getBank().getAccountName());
			ps.setString(3, group.getBank().getBranch());
			ps.setString(5,group.getBank().getRegion());
			stat= ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return stat;
	}
	
	
	
	public static double getOccupationClassMultiplier(ArrayList<GroupMember> members) {
		int[] counts = new int[GRPDBUtility.OCCUPATION_CLASSES.length];
		double[] percentage = new double[GRPDBUtility.OCCUPATION_CLASSES.length];
		DecimalFormat formatter = new DecimalFormat("#0.00");
		int totalcount = 0;
		double occupationMultiplier = 0;
		for (GroupMember m : members) {
			// setting the count of each member of the class;
			// substract 1 to get match the array with the id
			counts[m.getOccupationClassId() - 1]++;
			totalcount++;
		}

		for (int i = 0; i < GRPDBUtility.OCCUPATION_CLASSES.length; i++) {
			percentage[i] = (counts[i] * 1.0 / totalcount);
			// adding the product of class occupation rate by the percentage to
			// the total
			occupationMultiplier += (percentage[i] * GRPDBUtility.OCCUPATION_CLASSES_RATES[i]);
			
		}
		double news = Double.parseDouble(formatter.format(occupationMultiplier));
		
		return news;
	}
		

	public static double getTotalfactoredAmount(ArrayList<GroupMember> members,ArrayList<double[]> data){
		double[] maleAgeBandSum = new double[GRPDBUtility.AGE_BANDS.length];
		double[] femaleAgeBandSum = new double[GRPDBUtility.AGE_BANDS.length];
		double[] totalAgeBandSum = new double[GRPDBUtility.AGE_BANDS.length];
		double[] maleAgeBandRate = data.get(0);
		double[] femaleAgeBandRate = data.get(1);
		for (GroupMember member : members){
			int age = DBUtility.getAge(member.getDob());
			if (member.getGender().equalsIgnoreCase("M")){
				System.out.println(member.getSalary());
				maleAgeBandSum[GRPDBUtility.getAgeBand(age)]+=member.getSalary();
			}else{
		
				femaleAgeBandSum[GRPDBUtility.getAgeBand(age)]+=member.getSalary();
			}
			}
		for (int i =0;i<GRPDBUtility.AGE_BANDS.length;i++){
		double maleAmount =maleAgeBandSum[i]*maleAgeBandRate[i];
		totalAgeBandSum[i]+=maleAmount;
		double femaleAmount =femaleAgeBandSum[i]*femaleAgeBandRate[i];
		totalAgeBandSum[i]+=femaleAmount;

		}
		return(GRPDBUtility.sumArray(totalAgeBandSum));
	
	}
	
	
	public static double getExpectedClaimAmount(ArrayList<GroupMember> members,ArrayList<double[]> data){
		int[] counts = new int[GRPDBUtility.OCCUPATION_CLASSES.length];
		double[] percentage = new double[GRPDBUtility.OCCUPATION_CLASSES.length];
		DecimalFormat formatter = new DecimalFormat("#0.00");
		int totalcount = 0;
		double occupationMultiplier = 0;
		
		
		double[] maleAgeBandSum = new double[GRPDBUtility.AGE_BANDS.length];
		double[] femaleAgeBandSum = new double[GRPDBUtility.AGE_BANDS.length];
		double[] totalAgeBandSum = new double[GRPDBUtility.AGE_BANDS.length];
		double[] maleAgeBandRate = data.get(0);
		double[] femaleAgeBandRate = data.get(1);
		for (GroupMember member : members){
			int age = DBUtility.getAge(member.getDob());
			counts[member.getOccupationClassId() - 1]++;
			totalcount++;
			if (member.getGender().equalsIgnoreCase("M")){
				System.out.println(member.getSalary());
				maleAgeBandSum[GRPDBUtility.getAgeBand(age)]+=member.getSalary();
			}else{
		
				femaleAgeBandSum[GRPDBUtility.getAgeBand(age)]+=member.getSalary();
			}
			}
		for (int i =0;i<GRPDBUtility.AGE_BANDS.length;i++){
		double maleAmount =maleAgeBandSum[i]*maleAgeBandRate[i];
		totalAgeBandSum[i]+=maleAmount;
		double femaleAmount =femaleAgeBandSum[i]*femaleAgeBandRate[i];
		totalAgeBandSum[i]+=femaleAmount;

		}
		for (int i = 0; i < GRPDBUtility.OCCUPATION_CLASSES.length; i++) {
			percentage[i] = (counts[i] * 1.0 / totalcount);
			// adding the product of class occupation rate by the percentage to
			// the total
			occupationMultiplier += (percentage[i] * GRPDBUtility.OCCUPATION_CLASSES_RATES[i]);
			
		}
		double news = Double.parseDouble(formatter.format(occupationMultiplier));
		
		
		
		return(GRPDBUtility.sumArray(totalAgeBandSum)*news);
	
	}
	
	
	
	
	public static int getAgeBand(int age){
		if (age>=0 &age<=29){
			return 0;
		}
		else if (age>=30 &age<=34){
			return 1;
		}
		else if (age>=35 &age<=39){
			return 2;
		}
		else if (age>=40 &age<=44){
			return 3;
		}
		else if (age>=45 &age<=49){
			return 4;
		}
		else if (age>=50 &age<=54){
			return 5;
		}
		else if (age>=55 &age<=59){
			return 6;
		}
		else if (age>=60 &age<=64){
			return 7;
		}
		else if (age>=65 &age<=69){
			return 8;
		}
		
		else if (age>=70 &age<=74){
			return 9;
		}

		else{
			return 10;
		}
		
		
	}
}
