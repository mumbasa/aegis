package com.utility;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.RandomStringUtils;

import com.data.Beneficiary;
import com.data.Claim;
import com.data.ClientBank;
import com.data.Health;
import com.data.Policy;
import com.data.PolicyHolder;
import com.data.PolicyOwner;
import com.data.PolicyPayment;
import com.data.PolicyStatement;
import com.data.Source;
import com.data.Staff;
import com.data.Trustee;
import com.data.User;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

public class DBUtility {
	public static final String LOCATION = "/home/bryan/";
	public static final Date NOW = Calendar.getInstance().getTime();

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public static void SQLS() {

	}

	public static boolean authenticateUser(String staffNumber, String password)
			throws SQLException, NoSuchAlgorithmException,
			InvalidKeySpecException {
		boolean status = false;
		String statement = "Select password,role_id from users where staff_number=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, staffNumber);
		ResultSet set = query.executeQuery();
		if (set.next()) {

			if ((PasswordHash.validatePassword(password, set.getString(1)) == true)) {
				status = true;
				System.out.println("shit");
			}
		}
		set.close();
		conn.close();

		return status;

	}

	public static HashMap<Integer, String> getRole() throws SQLException {
		HashMap<Integer, String> pays = new HashMap<Integer, String>();
		String statement = "SELECT role_id,role FROM roles;";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();
		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			pays.put(set.getInt(1), set.getString(2));

		}
		set.close();
		query.close();
		conn.close();

		return pays;

	}

	public static Double getStaffTodayPolicyAmount(Staff staff)
			throws SQLException {
		Double pays = 0.0;
		String statement = "SELECT sum(amount) FROM insurance.policy_payment where date(date_paid)=curdate() and data_entry_staff=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, staff.getStaffNumber());
		ResultSet set = query.executeQuery();
		if (set.next()) {
			pays = set.getDouble(1);

		}
		query.close();
		set.close();
		conn.close();

		return pays;

	}

	public static BeanItemContainer<Claim> getClaimsRequested()
			throws SQLException {
		BeanItemContainer<Claim> claims = new BeanItemContainer<Claim>(
				Claim.class);
		String statement = "SELECT  policy_number, amount, date_applied, claim_type_id, "
				+ "staff_id, approved_date, approved_amount, status_id,claims_id FROM insurance.policy_claims where status_id ='P' or status_id='N'";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		ArrayList<Claim> claimArray = new ArrayList<>();
		ResultSet set = query.executeQuery();
		while (set.next()) {
			Claim claim = new Claim();
			claim.setPolicyNumber(set.getString(1));
			claim.setAmount(set.getDouble(2));
			claim.setDateApplied(set.getString(3));
			claim.setClaimTypeId(set.getInt(4));
			claim.setStaffNumber(set.getString(5));
			claim.setDateApproved(set.getString(6));
			claim.setApprovedAmount(set.getDouble(7));
			claim.setStatus(set.getString(8));
			claim.setClaimID(set.getInt(9));
			claimArray.add(claim);
		}
		claims.addAll(claimArray);
		query.close();
		set.close();
		conn.close();

		return claims;

	}

	public static HashMap<String, Number> getYearByYearRegistrationByStaff(
			Staff staff) throws SQLException {
		HashMap<String, Number> pays = new HashMap<String, Number>();
		String statement = "SELECT  year(entry_date), count(*) from customer_policy where staff_id=? group by year(entry_date)";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, staff.getStaffNumber());
		ResultSet set = query.executeQuery();
		while (set.next()) {
			pays.put(set.getString(1), set.getInt(2));

		}
		query.close();
		set.close();
		conn.close();

		return pays;

	}

	public static HashMap<String, Number> getDailyWorkDoneByStaff(Staff staff)
			throws SQLException {
		HashMap<String, Number> pays = new HashMap<String, Number>();
		String statement = "SELECT (SELECT count(*) FROM insurance.policy_payment where month(date_paid)=month(curdate()) and data_entry_staff=?), (SELECT count(*) FROM insurance.customer_policy where month(entry_date)=month(curdate())), (SELECT count(*) FROM insurance.policy_claims where month(date_applied)=month(curdate()) and claim_type_id=1), (SELECT count(*) FROM insurance.policy_claims where month(date_applied)=month(curdate()) and claim_type_id=2);";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, staff.getStaffNumber());
		ResultSet set = query.executeQuery();
		if (set.next()) {
			int total = set.getInt(1) + set.getInt(2) + set.getInt(3)
					+ set.getInt(4);
			pays.put("Payments", (set.getDouble(1) / total) * 100);
			pays.put("Policies Registered", (set.getDouble(2) / total) * 100);
			pays.put("Partial Withdrawals", (set.getDouble(3) / total) * 100);
			pays.put("Surrenders", (set.getDouble(4) / total) * 100);
			System.out.println("Total is " + (set.getDouble(1) / total) * 100);
		}
		query.close();
		set.close();
		conn.close();

		return pays;

	}

	public static Staff getStaffDetail(String staffNumber) throws SQLException {
		Staff staff = null;
		String statement = "SELECT staff.last_name,staff.middle_name,staff.first_name, branch_id, staff.portrait_picture,"
				+ "staff.staff_number FROM staff where staff_number=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, staffNumber);
		ResultSet set = query.executeQuery();
		if (set.next()) {
			staff = new Staff();
			staff.setLastName(set.getString(1));
			staff.setMiddleName(set.getString(2));
			staff.setFirstName(set.getString(3));
			staff.setBranch_id(set.getInt(4));
			staff.setPicture(set.getString(5));
			staff.setStaffNumber(set.getString(6));

		}
		query.close();
		set.close();
		conn.close();

		return staff;

	}

	public static String[] getPolicyStatementSummary(String policyNumber)
			throws SQLException {
		String[] data = new String[8];
		String statement = "SELECT any_age(dob,activation_date) as age,date(activation_date),"
				+ "date(date_add(activation_date, interval (maturity_age) YEAR))as m_Date,"
				+ "(SELECT sum(amount) from policy_payment where policy_number=h.policy_number) as am,"
				+ "(SELECT accumulated_fund from statement where policy_number = h.policy_number order by statement_id desc limit 1) as acc,"
				+ "(SELECT death_benefit from statement where policy_number = h.policy_number order by statement_id desc limit 1) as db,"
				+ " (SELECT sum(amount) FROM insurance.policy_claims where policy_number=? and claim_type_id=1) as cp,"
				+ "(SELECT cash_value from statement where policy_number = h.policy_number order by statement_id desc limit 1) as cash FROM customer_policy as c join policy_type as p on c.policy_type_id=p.policy_type_id join policy_holder as h on c.policy_number=h.policy_number where c.policy_number=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, policyNumber);
		query.setString(2, policyNumber);

		ResultSet set = query.executeQuery();
		if (set.next()) {
			for (int i = 0; i < data.length; i++) {
				data[i] = set.getString((i + 1));
				System.err.println("yes " + data[i]);

			}

		}
		query.close();
		set.close();
		conn.close();
		;

		return data;

	}

	public static String[] getPolicyStatementSummary2(String policyNumber)
			throws SQLException {
		String[] data = new String[8];
		String statement = "SELECT any_age(dob,activation_date) as age,date(activation_date),"
				+ "date(date_add(activation_date, interval (maturity_age) YEAR))as m_Date,"
				+ "(SELECT sum(amount) from policy_payment where policy_number=h.policy_number) as am,"
				+ "(SELECT accumulated_fund from policy_statement where policy_number = h.policy_number order by statement_id desc limit 1) as acc,"
				+ "(SELECT death_benefit FROM insurance.policy_statement where policy_number=? order by statement_id desc limit 1) as db,"
				+ " (SELECT sum(amount) FROM insurance.policy_claims where policy_number=? and claim_type_id=1) as cp,"
				+ "(SELECT surrender_cash_value from policy_statement where policy_number = h.policy_number order by statement_id desc limit 1) as cash FROM customer_policy as c join policy_type as p on c.policy_type_id=p.policy_type_id join policy_holder as h on c.policy_number=h.policy_number where c.policy_number=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, policyNumber);
		query.setString(2, policyNumber);
		query.setString(3, policyNumber);
		ResultSet set = query.executeQuery();
		if (set.next()) {
			for (int i = 0; i < data.length; i++) {
				data[i] = set.getString((i + 1));
				System.err.println("yes " + data[i]);

			}

		}
		query.close();
		set.close();
		conn.close();

		return data;

	}



	public static PolicyOwner getCustomerDetail(String customerNumber)
			throws SQLException {
		PolicyOwner owner = null;
		String statement = "SELECT first_name, middle_name, last_name, address,region_id,"
				+ "mobile_number, email, dob, date_added, marital_status_id, "
				+ "occupation_id, picture, signature, customer_number, "
				+ "children, country, title_id, birthplace, age, "
				+ "telephone,sex from customer where customer_number=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, customerNumber);
		ResultSet set = query.executeQuery();
		if (set.next()) {
			owner = new PolicyOwner();
			owner.setFirstName(set.getString(1));
			owner.setMiddleName(set.getString(2));
			owner.setLastName(set.getString(3));
			owner.setAddress(set.getString(4));
			owner.setRegion_id(set.getString(5));
			owner.setMobile(set.getString(6));
			owner.setEmail(set.getString(7));
			owner.setDob(set.getString(8));
			owner.setDateRegistered(set.getString(9));
			owner.setMaritalStatusId(set.getInt(10));
			owner.setOccupationId(set.getInt(11));
			owner.setPicture(set.getString(12));
			owner.setSignature(set.getString(13));
			owner.setCustomerNumber(set.getString(14));
			owner.setChildren(set.getInt(15));
			owner.setCountryOfBirth(set.getString(16));
			owner.setTitle(set.getString(17));
			owner.setPlaceOfBirth(set.getString(18));
			owner.setAge(getAge(set.getString(8)));
			owner.setTelephone(set.getString(19));
			owner.setSex(set.getString(21));
			
		}
		set.close();
		conn.close();

		return owner;

	}
	
	
	
	
	
	
	
	
	public static BeanItemContainer<PolicyOwner> AllgetCustomerDetails()
			throws SQLException {
		BeanItemContainer<PolicyOwner>  owners = new BeanItemContainer<PolicyOwner>(PolicyOwner.class);
		String statement = "SELECT first_name, middle_name, last_name, address,region_id,"
				+ "mobile_number, email, dob, date_added, marital_status_id, "
				+ "occupation_id, picture, signature, customer_number, "
				+ "children, country, title_id, birthplace, age, "
				+ "telephone,sex from customer";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		ResultSet set = query.executeQuery();
		while (set.next()) {
			PolicyOwner owner = new PolicyOwner();
			owner.setFirstName(set.getString(1));
			owner.setMiddleName(set.getString(2));
			owner.setLastName(set.getString(3));
			owner.setAddress(set.getString(4));
			owner.setRegion_id(set.getString(5));
			owner.setMobile(set.getString(6));
			owner.setEmail(set.getString(7));
			owner.setDob(set.getString(8));
			owner.setDateRegistered(set.getString(9));
			owner.setMaritalStatusId(set.getInt(10));
			owner.setOccupationId(set.getInt(11));
			owner.setPicture(set.getString(12));
			owner.setSignature(set.getString(13));
			owner.setCustomerNumber(set.getString(14));
			owner.setChildren(set.getInt(15));
			owner.setCountryOfBirth(set.getString(16));
			owner.setTitle(set.getString(17));
			owner.setPlaceOfBirth(set.getString(18));
			owner.setAge(getAge(set.getString(8)));
			owner.setTelephone(set.getString(19));
			owner.setSex(set.getString(21));
			owners.addBean(owner);
		}
		set.close();
		conn.close();

		return owners;

	}
	
	
	
	
	
	

	public static PolicyHolder getPolicyHolderDetails(String policyNumber)
			throws SQLException {
		PolicyHolder owner = null;
		String statement = "SELECT first_name, middle_name, last_name, mailing_address,region_id,"
				+ "mobile_number, email, dob, date_added, marital_status_id, "
				+ "occupation_id, portrait_picture, signature, "
				+ "children, nationality, title_id, birthplace,"
				+ "telephone,sex,h.policy_number , "
				+ "is_smoker, is_drinker, has_been_admitted, drinking_frequency, smoking_frequency, reason_admission, has_family_sickness, family_sickness_name from policy_holder as h inner join medical as m on h.policy_number = m.policy_number where h.policy_number=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, policyNumber);
		ResultSet set = query.executeQuery();
		if (set.next()) {
			owner = new PolicyHolder();
			owner.setFirstName(set.getString(1));
			owner.setMiddleName(set.getString(2));
			owner.setLastName(set.getString(3));
			owner.setAddress(set.getString(4));
			owner.setRegion_id(set.getString(5));
			owner.setMobile(set.getString(6));
			owner.setEmail(set.getString(7));
			owner.setDob(set.getString(8));
			owner.setDateRegistered(set.getString(9));
			owner.setMaritalStatusId(set.getInt(10));
			owner.setOccupationId(set.getInt(11));
			owner.setPicture(set.getString(12));
			owner.setSignature(set.getString(13));
			owner.setChildren(set.getInt(14));
			owner.setCountryOfBirth(set.getString(15));
			owner.setTitle(set.getString(16));
			owner.setPlaceOfBirth(set.getString(17));
			owner.setAge(getAge(set.getString(8)));
			owner.setTelephone(set.getString(18));
			owner.setSex(set.getString(19));
			owner.setPolicyNumber(set.getString(20));
			owner.setIsSmoker(set.getString(21));
			owner.setIsDrinker(set.getString(22));
			owner.setHasBeenAdmitted(set.getString(23));
			owner.setDrinkingFrequency(set.getString(24));
			owner.setSmokingFrequency(set.getString(25));
			owner.setAdmissionReason(set.getString(26));
			owner.setFamilySicknessName(set.getString(28));
			owner.setHasFamilySickness(set.getString(27));
			System.out.println("SEx is " + owner.getSex());
		}
		query.close();
		set.close();
		conn.close();

		return owner;

	}

	
	
	public static BeanItemContainer<PolicyHolder>  getHolderMedicalVerificaiton()
			throws SQLException {
		BeanItemContainer<PolicyHolder> holders = new BeanItemContainer<PolicyHolder>(PolicyHolder.class);
		String statement = "SELECT first_name, middle_name, last_name, mailing_address,region_id,"
				+ "mobile_number, email, dob, date_added, marital_status_id, "
				+ "occupation_id, portrait_picture, signature, "
				+ "children, nationality, title_id, birthplace,"
				+ "telephone,sex,policy_number from policy_holder where policy_number IN (SELECT policy_number FROM insurance.medical where is_verified='N')";
				
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		ResultSet set = query.executeQuery();
		while (set.next()) {
			PolicyHolder owner = new PolicyHolder();
			owner.setFirstName(set.getString(1));
			owner.setMiddleName(set.getString(2));
			owner.setLastName(set.getString(3));
			owner.setAddress(set.getString(4));
			owner.setRegion_id(set.getString(5));
			owner.setMobile(set.getString(6));
			owner.setEmail(set.getString(7));
			owner.setDob(set.getString(8));
			owner.setDateRegistered(set.getString(9));
			owner.setMaritalStatusId(set.getInt(10));
			owner.setOccupationId(set.getInt(11));
			owner.setPicture(set.getString(12));
			owner.setSignature(set.getString(13));
			owner.setChildren(set.getInt(14));
			owner.setCountryOfBirth(set.getString(15));
			owner.setTitle(set.getString(16));
			owner.setPlaceOfBirth(set.getString(17));
			owner.setAge(getAge(set.getString(8)));
			owner.setTelephone(set.getString(18));
			owner.setSex(set.getString(19));
			owner.setPolicyNumber(set.getString(20));
			holders.addBean(owner);
			System.out.println("SEx is " + owner.getSex());
		}
		query.close();
		set.close();
		conn.close();

		return holders;

	}
	
	
	
	
	
	
	
	
	public static BeanItemContainer<PolicyStatement> getPolicyStatement(
			String policyNumber) throws SQLException {
		ArrayList<PolicyStatement> statements = new ArrayList<PolicyStatement>();
		String statement = "SELECT  policy_duration, previous_balance, payment_date, EOM, age, premium_paid, premium_load_rate, premium_load_amount, "
				+ "net_premium, policy_fee, mortality_rate, insurance_cost, death_benefit, pa, sum_at_risk, interest_rate, interest_amount, "
				+ "partial_withdrawal, accumulated_fund, surrender_penalty, cash_value, policy_number, net_premium2 FROM insurance.statement where  policy_number=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, policyNumber);
		ResultSet set = query.executeQuery();
		while (set.next()) {
			PolicyStatement pStatement = new PolicyStatement();
			pStatement.setPolicyDuration(set.getInt(1));
			pStatement.setPreviousBalance(set.getDouble(2));
			pStatement.setPaymentDate(set.getString(3));
			pStatement.setEOM(set.getString(4));
			pStatement.setAge(set.getInt(5));
			pStatement.setPremiumPaid(set.getDouble(6));
			pStatement.setPremiumLoadRate(set.getDouble(7));
			pStatement.setPremiumLoadAmount(set.getDouble(8));
			pStatement.setNetPremium(set.getDouble(9));
			pStatement.setPolicyFee(set.getDouble(10));
			pStatement.setMortalityRate(set.getFloat(11));
			pStatement.setInsuranceCost(set.getDouble(12));
			pStatement.setDeathBenefit(set.getDouble(13));
			pStatement.setPa(set.getDouble(14));
			pStatement.setSumAtRisk(set.getDouble(15));
			pStatement.setInterestRate(set.getDouble(16));
			pStatement.setInterestAmount(set.getDouble(17));
			pStatement.setPartialWithdrawal(set.getDouble(18));
			pStatement.setAccumulatedFund(set.getDouble(19));
			pStatement.setSurrenderPenalty(set.getDouble(20));
			pStatement.setCashValue(set.getDouble(21));
			pStatement.setPolicyNumber(set.getString(22));
			pStatement.setNetPremiumFinal(set.getDouble(23));
			statements.add(pStatement);
		}
		query.close();
		set.close();
		conn.close();
		BeanItemContainer<PolicyStatement> policyStatements = new BeanItemContainer<PolicyStatement>(
				PolicyStatement.class);
		policyStatements.addAll(statements);
		return policyStatements;

	}

	public static BeanItemContainer<PolicyStatement> getPolicyStatement2(
			String policyNumber) throws SQLException {
		ArrayList<PolicyStatement> statements = new ArrayList<PolicyStatement>();
		String statement = "SELECT  policy_duration, previous_balance, payment_date, EOM, age, "
				+ "premium_paid, mortality_rate, expenses_charge, commission,total_deduction,"
				+ " net_premium, interest_rate, interest_amount, death_benefit, pa, "
				+ "partial_withdrawal, accumulated_fund, surrender_penalty, surrender_cash_value  FROM policy_statement where  policy_number=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, policyNumber);
		ResultSet set = query.executeQuery();
		while (set.next()) {
			PolicyStatement pStatement = new PolicyStatement();
			pStatement.setPolicyDuration(set.getInt(1));
			pStatement.setPreviousBalance(set.getDouble(2));
			pStatement.setPaymentDate(set.getString(3));
			pStatement.setEOM(set.getString(4));
			pStatement.setAge(set.getInt(5));

			pStatement.setPremiumPaid(set.getDouble(6));
			pStatement.setMortalityRate(set.getFloat(7));
			pStatement.setPolicyFee(set.getDouble(8));
			pStatement.setCommission(set.getDouble(9));
			pStatement.setTotalDeduction(set.getDouble(10));

			pStatement.setNetPremium(set.getDouble(11));
			pStatement.setInterestRate(set.getDouble(12));
			pStatement.setInterestAmount(set.getDouble(13));
			pStatement.setDeathBenefit(set.getDouble(14));
			pStatement.setPa(set.getDouble(15));

			pStatement.setPartialWithdrawal(set.getDouble(16));
			pStatement.setAccumulatedFund(set.getDouble(17));
			pStatement.setSurrenderPenalty(set.getDouble(18));
			pStatement.setCashValue(set.getDouble(19));
			// pStatement.setPolicyNumber(set.getString(22));

			statements.add(pStatement);
		}
		query.close();
		set.close();
		conn.close();
		BeanItemContainer<PolicyStatement> policyStatements = new BeanItemContainer<PolicyStatement>(
				PolicyStatement.class);
		policyStatements.addAll(statements);
		return policyStatements;

	}

	public static ArrayList<Policy> getCustomerPolicyDetails(
			String customerNumber) throws SQLException {
		ArrayList<Policy> policies = new ArrayList<Policy>();
		String statement = "SELECT  customer_number, policy_type_id, policy_number, is_active, entry_date, "
				+ "activation_date, premium, initial_sum_assured, payment_frequency_id, payment_type_id, r"
				+ "egion_id,agent_number FROM insurance.customer_policy where customer_number=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, customerNumber);
		ResultSet set = query.executeQuery();
		while (set.next()) {
			Policy policy = new Policy();
			policy.setCustomerNumber(set.getString(1));
			policy.setPolicyType(set.getString(2));
			policy.setPolicyNumber(set.getString(3));
			policy.setStatus(set.getString(4));
			policy.setDateRegistered(set.getString(5));
			policy.setDateActivated(set.getString(6));
			policy.setPremium("" + set.getDouble(7));
			policy.setInitialSumAssured("" + set.getDouble(8));
			policy.setFrequency_id(set.getInt(9));
			policy.setPayment_id(set.getInt(10));
			policy.setRegion(set.getString(11));
			policy.setAgentID(set.getString(12));
			policies.add(policy);

		}
		query.close();
		set.close();
		conn.close();

		return policies;

	}

	public static Policy getPolicyDetails(String number) throws SQLException {
		Policy policy = null;
		String statement = "SELECT  customer_number, policy_type_id, policy_number, is_active, entry_date, "
				+ "activation_date, premium, initial_sum_assured, payment_frequency_id, payment_type_id, r"
				+ "egion_id FROM insurance.customer_policy where policy_number=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, number);
		ResultSet set = query.executeQuery();
		while (set.next()) {
			policy = new Policy();
			policy.setCustomerNumber(set.getString(1));
			policy.setPolicyType(set.getString(2));
			policy.setPolicyNumber(set.getString(3));
			policy.setStatus(set.getString(4));
			policy.setDateRegistered(set.getString(5));
			policy.setDateActivated(set.getString(6));
			policy.setPremium("" + set.getDouble(7));
			policy.setInitialSumAssured("" + set.getDouble(8));
			policy.setFrequency_id(set.getInt(9));
			policy.setPayment_id(set.getInt(10));
			policy.setRegion(set.getString(11));

		}
		query.close();
		set.close();
		conn.close();
		return policy;

	}

	public static BeanItemContainer<Policy> getUnApporvedPolicyDetails()
			throws SQLException {
		BeanItemContainer<Policy> policies = new BeanItemContainer<Policy>(
				Policy.class);
		String statement = "SELECT customer_number, p.policy_type_id, p.policy_number, is_active, entry_date, activation_date, premium, initial_sum_assured, payment_frequency_id, payment_type_id, region_id FROM insurance.customer_policy as p join medical as m on p.policy_number = m.policy_number  where is_approved='N' and m.is_verified='Y' and p.policy_number NOT IN (SELECT policy_number from reinsurance)";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);

		ResultSet set = query.executeQuery();
		while (set.next()) {
			Policy policy = new Policy();
			policy.setCustomerNumber(set.getString(1));
			policy.setPolicyType(set.getString(2));
			policy.setPolicyNumber(set.getString(3));
			policy.setStatus(set.getString(4));
			policy.setDateRegistered(set.getString(5));
			policy.setDateActivated(set.getString(6));
			policy.setPremium("" + set.getDouble(7));
			policy.setInitialSumAssured("" + set.getDouble(8));
			policy.setFrequency_id(set.getInt(9));
			policy.setPayment_id(set.getInt(10));
			policy.setRegion(set.getString(11));
			policies.addBean(policy);

		}
		query.close();
		set.close();
		conn.close();
		return policies;

	}

	public static ClientBank getBankPolicyBankDetails(String number)
			throws SQLException {
		ClientBank clientBank = null;
		String statement = "SELECT   bank_id, policy_number, account_number, branch, region_id, account_name FROM  policy_bank where policy_number=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, number);
		ResultSet set = query.executeQuery();
		if (set.next()) {
			clientBank = new ClientBank();
			clientBank.setBankID(set.getInt(1));
			clientBank.setPolicyNumber(set.getString(2));
			clientBank.setAccountNumber(set.getString(3));
			clientBank.setBranch(set.getString(4));
			clientBank.setRegion(set.getString(5));
			clientBank.setAccountName(set.getString(6));
		}
		query.close();
		set.close();
		conn.close();

		return clientBank;

	}

	public static Source getBankPolicySourceDetails(String number)
			throws SQLException {
		Source source = null;
		String statement = "SELECT  policy_number, policy_owner, company_id,staff_number FROM insurance.source where policy_number=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, number);
		ResultSet set = query.executeQuery();
		if (set.next()) {
			source = new Source();
			source.setPolicyNumber(set.getString(1));
			source.setCustomerNumber(set.getString(2));
			source.setCompanyID(set.getInt(3));
			source.setStaffNumber(set.getString(4));

		}
		query.close();
		set.close();
		conn.close();

		return source;

	}

	public static ArrayList<Beneficiary> getPolicyBeneficiariesDetails(
			Policy policy) throws SQLException {
		ArrayList<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
		String statement = "SELECT first_name,middle_name,last_name,address,percentage,relationship_id,mobile_number,email, dob,policy_number,"
				+ "date_added,nationality,title_id,sex FROM insurance.beneficiary where policy_number=?;";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, policy.getPolicyNumber());
		ResultSet set = query.executeQuery();
		while (set.next()) {
			Beneficiary beneficiary = new Beneficiary();
			beneficiary.setFirstName(set.getString(1));
			beneficiary.setMiddleName(set.getString(2));
			beneficiary.setLastName(set.getString(3));
			beneficiary.setAddress(set.getString(4));
			beneficiary.setPercentage(set.getDouble(5));
			beneficiary.setRelationship_id(set.getInt(6));
			beneficiary.setMobile(set.getString(7));
			beneficiary.setEmail(set.getString(8));
			beneficiary.setDob(set.getString(9));
			beneficiary.setPolicyNumber(set.getString(10));
			beneficiary.setDateRegistered(set.getString(11));
			beneficiary.setCountryOfBirth(set.getString(12));
			beneficiary.setTitle(set.getString(13));
			beneficiary.setSex(set.getString(14));
			beneficiaries.add(beneficiary);

		}
		query.close();
		set.close();
		conn.close();
		return beneficiaries;

	}

	public static Trustee getPolicyTrusteeDetails(Policy policy)
			throws SQLException {
		Trustee trustee = null;
		String statement = "SELECT first_name, middle_name, last_name, address, mobile_number, "
				+ "email, dob, policy_number,title_id, date_added, beneficiary_realtionship FROM insurance.trustee where policy_number=?;";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, policy.getPolicyNumber());
		ResultSet set = query.executeQuery();
		if (set.next()) {
			trustee = new Trustee();
			trustee.setFirstName(set.getString(1));
			trustee.setMiddleName(set.getString(2));
			trustee.setLastName(set.getString(3));
			trustee.setAddress(set.getString(4));

			trustee.setMobile(set.getString(5));
			trustee.setEmail(set.getString(6));
			trustee.setDob(set.getString(7));
			trustee.setPolicyNumber(set.getString(8));
			trustee.setTitle(set.getString(9));
			trustee.setDateRegistered(set.getString(10));
			trustee.setRelationship_id(set.getInt(11));

		}
		query.close();
		set.close();
		conn.close();

		return trustee;

	}


	
	
	public static Health getHolderHealth(String policyNumber){
		
		Health health = null;
		String statement = "SELECT * FROM insurance.medical where policy_number=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query;
		try {
			query = conn.prepareStatement(statement);
			query.setString(1, policyNumber);
			ResultSet set = query.executeQuery();
			if (set.next()) {
				health = new Health();
				health.setPolicyNumber(set.getString(2));
				health.setSmoker(set.getString(3));
				health.setDrinker(set.getString(4));
				health.setBeenAdmitted(set.getString(5));
				
				health.setDrinkingFrequency(set.getString(6));
				health.setSmokingFrequency(set.getString(7));
				health.setAdmissionReason(set.getString(8));
				
				health.setFamilySickness(set.getString(9));
				health.setFamilySicknessName(set.getString(10));
				health.setVerified(set.getString(11));
				health.setBpRate(set.getInt(12));
				
				health.setBowels(set.getString(13));
				health.setBowelsCondition(set.getString(14));
				health.setSTD(set.getString(15));
				health.setSTDCondition(set.getString(16));
				health.setSpecial(set.getString(17));
				health.setSpecialCondition(set.getString(18));
				health.setDisabled(set.getString(19));
				health.setDisabledConditon(set.getString(20));
				health.setHeart(set.getString(21));
				health.setHeartCondition(set.getString(22));
				health.setSlippedDisk(set.getString(23));
				health.setSlippedDdiskCondition(set.getString(24));
				health.setRespiratory(set.getString(25));
				health.setRespiratoryCondition(set.getString(26));
				
			}
			query.close();
			set.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		return health;


	}
	
	

	

	public static String getPolicyActivatedStatus(String policyNumber)
			throws SQLException {
		String data = "";
		String statement = "SELECT is_active FROM insurance.customer_policy where policy_number=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, policyNumber);

		ResultSet set = query.executeQuery();
		while (set.next()) {
			data = set.getString(1);

		}
		query.close();
		set.close();
		conn.close();
		return data;

	}


	


	public static class Pictures implements ColumnGenerator {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Object generateCell(Table source, Object itemId, Object columnId) {
			// TODO Auto-generated method stub
			Embedded image = new Embedded();
			Item item = source.getItem(itemId);
			@SuppressWarnings("unchecked")
			Property<String> pic = item.getItemProperty("picture");
			FileResource resource = new FileResource(new File(
					DBUtility.LOCATION + "/Pictures/" + pic.getValue()));
			image.setSource(resource);
			image.setStyleName("loancollector");
			image.setHeight("40px");
			image.setWidth("40px");
			return image;
		}

	}

	public static int policyNumberGeneration(Policy policy) {
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

	
	public static int verifyMedicalInformation(Health health) {
		int stat=0;
		
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;

		String state = "UPDATE medical SET is_smoker=?, is_drinker=?, has_been_admitted=?, drinking_frequency=?, smoking_frequency=?, reason_admission=?, has_family_sickness=?, family_sickness_name=?, is_verified='Y',bprate=?,has_bowel=?, bowel_condition=?, std_status=?, std_condition=?, has_special_condition=?, special_condition=?, is_disabled=?, disability=?, has_heart_condition=?, heart_condition=?, has_slipped_disk=?, slip_disk_condition=?, has_respiratory_issue=?, respiratory_condition=? WHERE policy_number=?";
		//String approvePolicy = "UPDATE client_policy set is_approved='Y' where policy_number=? and policy_number NOT IN(SELECT policy_number FROM reinsurance)";
		try {

			ps = connection.prepareStatement(state);
			ps.setString(1, health.getSmokingFrequency().equals("")|health.getSmokingFrequency()==null?"N":"Y");
			ps.setString(2, health.getDrinkingFrequency().equals("")|health.getDrinkingFrequency()==null?"N":"Y");
			ps.setString(3, health.getAdmissionReason().equals("")|health.getAdmissionReason()==null?"N":"Y");
			ps.setString(4, health.getDrinkingFrequency());
			ps.setString(5, health.getSmokingFrequency());
			ps.setString(6, health.getAdmissionReason());
			ps.setString(7, health.getFamilySicknessName().equals("")|health.getFamilySicknessName()==null?"N":"Y");
			ps.setString(8, health.getFamilySicknessName());
			ps.setInt(9, health.getBpRate());
			
			ps.setString(10, health.getBowelsCondition().equals("")|health.getBowelsCondition()==null?"N":"Y");
			ps.setString(11, health.getBowelsCondition());
			
			ps.setString(12, health.getSTDCondition().equals("")|health.getSTDCondition()==null?"N":"Y");
			ps.setString(13, health.getAdmissionReason());
			
			ps.setString(14, health.getSpecialCondition().equals("")|health.getSpecialCondition()==null?"N":"Y");
			ps.setString(15, health.getSpecialCondition());
			
			ps.setString(16, health.getDisabledConditon().equals("")|health.getDisabledConditon()==null?"N":"Y");
			ps.setString(17, health.getDisabledConditon());
			
			ps.setString(18, health.getHeartCondition().equals("")|health.getHeartCondition()==null?"N":"Y");
			ps.setString(19, health.getHeartCondition());
			
			ps.setString(20, health.getSlippedDdiskCondition().equals("")|health.getSlippedDdiskCondition()==null?"N":"Y");
			ps.setString(21, health.getSlippedDdiskCondition());
			
			ps.setString(22, health.getRespiratoryCondition().equals("")|health.getRespiratoryCondition()==null?"N":"Y");
			ps.setString(23, health.getRespiratoryCondition());
			
			
			ps.setString(24, health.getPolicyNumber());
			stat= ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return stat;
	}
	
	
	
	
	
	
	
	public static int claimApproval(int claimID, String policyNumber,
		double amount, String decision) {
		int stat=0;	
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;

		String state = "UPDATE policy_claims set status_id='A' ,approved_date=now(),approved_amount=?,status_id=? where claims_id=?";

		try {

			ps = connection.prepareStatement(state);
			ps.setString(2, decision);
			ps.setInt(3, claimID);
			ps.setDouble(1, amount);
		
			

			stat= ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return stat;
	}

	public static int ChequeApproval(PolicyPayment pay) {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		int stat =0;
		String state = "UPDATE policy_payment set is_cleared='Y' ,date_cleared=now() where policy_payment_id=?";

		try {

			ps = connection.prepareStatement(state);
			ps.setInt(1, pay.getPayID());
			stat= ps.executeUpdate();
			ps.close();
			connection.close();

			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return stat;
	}

	public static int PolicyApproval (String staffNumber, String policyNumber) {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		int stat =0;
		String state = "UPDATE insurance.customer_policy set is_approved='Y' ,staff_id=? where policy_number=?";

		try {

			ps = connection.prepareStatement(state);
			ps.setString(1, staffNumber);
			ps.setString(2, policyNumber);
		

			stat= ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return stat;
	}

	public static int insertPolicyOwner(PolicyOwner customer) {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		int status = 0;
		String state = "INSERT INTO customer (first_name, middle_name, last_name, address, region_id, "
				+ "mobile_number, email, dob,telephone, date_added,date_registered,marital_status_id, "
				+ " occupation_id, picture, signature, "
				+ "children, country, "
				+ "title_id, birthplace, age)"
				+ "VALUES ("
				+ "?,?,?,?,?,"
				+ "?,?,?,?,now(),now(),"
				+ "?,?,?,?,?," + "?,?,?,?)";
		try {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(state);
			ps.setString(1, customer.getLastName());
			ps.setString(2, customer.getMiddleName());
			ps.setString(3, customer.getFirstName());
			ps.setString(4, customer.getAddress());
			ps.setString(5, customer.getRegion());
			ps.setString(6, customer.getMobile());
			ps.setString(7, customer.getEmail());
			ps.setString(8, customer.getDob());
			ps.setString(9, customer.getTelephone());
			// ps.setString(10, customer.getDateRegistered());
			ps.setInt(10, customer.getMaritalStatusId());
			ps.setInt(11, customer.getOccupationId());
			ps.setString(12, customer.getPicture());
			ps.setString(13, customer.getSignature());
			ps.setInt(14, customer.getChildren());
			ps.setString(15, customer.getCountryOfBirth());
			ps.setString(16, customer.getTitle());
			ps.setString(17, customer.getPlaceOfBirth());
			ps.setInt(18, customer.getAge());

			if (ps.executeUpdate() == 1) {
				status = 1;
				System.out.println("Excwelend");
				connection.commit();
			} else {
				connection.rollback();
				System.out.println("not done");

			}
			ps.close();
			connection.close();

			return status;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}

	public static String insertPolicyOwner1(PolicyHolder policyHolder) {
		String status = null;
		Policy policy = policyHolder.getPolicy();
		PolicyOwner customer = policy.getCustomer();
		if (customer.getCustomerNumber() == null) {
			status = insertPolicyProcess1(policyHolder);
		} else {

			status = insertPolicyOwnerKnown(policyHolder);

		}
		return status;
	}

	public static String insertPolicyProcess1(PolicyHolder policyHolder) {
		Trustee trustee = policyHolder.getTrustee();
		Policy policy = policyHolder.getPolicy();
		PolicyOwner customer = policy.getCustomer();
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		String policyNumber =null;
		String customerNumber = "SELECT customer_number FROM insurance.customer where last_name=? and email=? and dob =?";
		String generateCustomerNumber = "UPDATE customer SET customer_number=CONCAT(YEAR(CURDATE()),lpad(customer_id,6,'0')) WHERE last_name='"
				+ customer.getLastName()
				+ "'AND email ='"
				+ customer.getEmail()
				+ "' AND dob ='"
				+ customer.getDob()
				+ "'";
		PreparedStatement makingCustomerNumber = null;
		PreparedStatement ps = null;
		Statement update = null;
		int status = 0;
		String state = "INSERT INTO customer (first_name, middle_name, last_name, address, region_id, "
				+ "mobile_number, email, dob,telephone, date_added,date_registered,marital_status_id, "
				+ " occupation_id, picture, signature, "
				+ "children, country, "
				+ "title_id, birthplace, age,sex)"
				+ "VALUES ("
				+ "?,?,?,?,?,"
				+ "?,?,?,?,now(),now(),"
				+ "?,?,?,?,?," + "?,?,?,?,?)";
		try {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(state);
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getMiddleName());
			ps.setString(3, customer.getLastName());
			ps.setString(4, customer.getAddress());
			ps.setString(5, customer.getRegion_id());
			ps.setString(6, customer.getMobile());
			ps.setString(7, customer.getEmail());
			ps.setString(8, customer.getDob());
			ps.setString(9, customer.getTelephone());
			ps.setInt(10, customer.getMaritalStatusId());
			ps.setInt(11, customer.getOccupationId());
			ps.setString(12, customer.getPicture());
			ps.setString(13, customer.getSignature());
			ps.setInt(14, customer.getChildren());
			ps.setString(15, customer.getCountryOfBirth());
			ps.setString(16, customer.getTitle());
			ps.setString(17, customer.getPlaceOfBirth());
			ps.setInt(18, customer.getAge());
			ps.setString(19, customer.getSex());
			ps.executeUpdate();
			update = connection.createStatement();
			status = update.executeUpdate(generateCustomerNumber);

			makingCustomerNumber = connection.prepareStatement(customerNumber);
			makingCustomerNumber.setString(1, customer.getLastName());
			makingCustomerNumber.setString(2, customer.getEmail());
			makingCustomerNumber.setString(3, customer.getDob());
			ResultSet set = makingCustomerNumber.executeQuery();
			if (set.next()) {
				customer.setCustomerNumber(set.getString(1));
				policy.setCustomer(customer);
				policy.setCustomerNumber(customer.getCustomerNumber());

				PreparedStatement policyInsertion = null;
				PreparedStatement policyNumberUpdate = null;
				PreparedStatement policyNumberVaue = null;
				String number = "UPDATE customer_policy SET policy_number = CONCAT(policy_type_id,YEAR(CURDATE()),lpad(client_policy_id,5,'0'),region_id) WHERE customer_number= ? AND premium= ?";
				
				//setting approval status dur to reinsurance or medicalCheck
				String approvedStats = (policy.isReinsurred()==true| policy.isMedicalCheckRequired()==true)?"N":"Y";
				String states = "INSERT INTO customer_policy (customer_number, policy_type_id, is_active, entry_date,  premium, "
						+ "initial_sum_assured, payment_frequency_id, payment_type_id,region_id,staff_id,"
						+ "agent_number,currency,is_approved) VALUES (?,?,'N',now(),?,?,?,?,?,?,?,?,?)";
				String policyNum = "SELECT policy_number FROM insurance.customer_policy where customer_number=? and premium=? and initial_sum_assured=?;";

				// connection.setAutoCommit(false);
				policyInsertion = connection.prepareStatement(states);
				policyInsertion.setString(1, policy.getCustomer().getCustomerNumber());
				policyInsertion.setString(2, policy.getPolicyType());

				policyInsertion.setDouble(3, policy.getPremium());
				policyInsertion.setDouble(4, policy.getInitialSumAssured());
				policyInsertion.setInt(5, policy.getFrequency_id());
				policyInsertion.setInt(6, policy.getPayment_id());
				policyInsertion.setString(7, policy.getRegion_id());
				policyInsertion.setString(8, policy.getStaffID());
				policyInsertion.setString(9, policy.getAgentID());
				policyInsertion.setString(10,policy.getCurrency());
				policyInsertion.setString(11, approvedStats);
				status = policyInsertion.executeUpdate();

				policyNumberUpdate = connection.prepareStatement(number);
				policyNumberUpdate.setString(1, policy.getCustomerNumber());
				policyNumberUpdate.setDouble(2, policy.getPremium());
				policyNumberUpdate.executeUpdate();

				policyNumberVaue = connection.prepareStatement(policyNum);
				policyNumberVaue.setString(1, policy.getCustomerNumber());
				policyNumberVaue.setDouble(3, policy.getInitialSumAssured());
				policyNumberVaue.setDouble(2, policy.getPremium());
				ResultSet sets = policyNumberVaue.executeQuery();

				if (sets.next()) {
					policy.setPolicyNumber(sets.getString(1));
					
					//switching to policy number
					policyNumber = sets.getString(1);
					System.out.println("00000000000000000000000000000000000");
					status = 1;
				}

				// Inserting agent Data sold
				if (!policy.getAgentID().equals(null)
						| !policy.getAgentID().equals("")) {
					if (insertAgentPolicySold(policy.getAgentID(),
							policy.getPolicyNumber()) == 1) {

						if (policy.getBank() != null) {
							status = insertClientBank(policy);

						} else if (policy.getSource() != null) {
							status = insertClientSource(policy);
						}

					}

				}
				// if policy bought directly

				else {
					if (policy.getBank() != (null)) {
						status = insertClientBank(policy);

					} else if (policy.getSource() != null) {
						status = insertClientSource(policy);
					}

				}

				if (status == 1) {
					if (policy.isReinsurred()==true){
						insertReinsurance(policy);
					}
					PreparedStatement insertHolder = null;
					PreparedStatement setHealth = null;

					String insertHolderStatement = "INSERT INTO policy_holder(first_name, middle_name, last_name, mailing_address, region_id, "
							+ "mobile_number, email, dob, policy_number, "
							+ "date_added, marital_status_id, occupation_id, portrait_picture, signature, sex, children, birthplace, nationality,title_id)"
							+ " VALUES("
							+ "?,?,?,?,?,"
							+ "?,?,?,?,now(),"
							+ "?,?,?,?,?,"
							+ "?,?,?,?)";
					insertHolder = connection.prepareStatement(insertHolderStatement);
					insertHolder.setString(1, policyHolder.getFirstName());
					insertHolder.setString(2, policyHolder.getMiddleName());
					insertHolder.setString(3, policyHolder.getLastName());
					insertHolder.setString(4, policyHolder.getAddress());
					insertHolder.setString(5, policyHolder.getRegion_id());
					insertHolder.setString(6, policyHolder.getMobile());
					insertHolder.setString(7, policyHolder.getEmail());
					insertHolder.setString(8, policyHolder.getDob());
					insertHolder.setString(9, policy.getPolicyNumber());
					insertHolder.setInt(10, policyHolder.getMaritalStatusId());
					insertHolder.setInt(11, policyHolder.getOccupationId());
					insertHolder.setString(12, policyHolder.getPicture());
					insertHolder.setString(13, policyHolder.getSignature());
					insertHolder.setString(14, policyHolder.getSex());
					insertHolder.setInt(15, policyHolder.getChildren());
					insertHolder.setString(16, policyHolder.getPlaceOfBirth());
					insertHolder.setString(17, policyHolder.getCountryOfBirth());
					insertHolder.setString(18, policyHolder.getTitle());
					insertHolder.executeUpdate();

					System.out.println("Inserted Holder --------------------------------->");
					String healthInsert = "INSERT INTO insurance.medical(is_smoker, is_drinker, has_been_admitted, drinking_frequency, smoking_frequency, reason_admission, has_family_sickness, family_sickness_name, bprate, has_bowel, bowel_condition, std_status, std_condition, has_special_condition, special_condition, is_disabled, disability, has_heart_condition, heart_condition, has_slipped_disk, slip_disk_condition, has_respiratory_issue, respiratory_condition,policy_number, is_verified)"
							+ "  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					
					String healthVerificaition = (policy.isMedicalCheckRequired()==true)?"N":"Y";
					setHealth = connection.prepareStatement(healthInsert);
					Health health = policyHolder.getHealth();
					setHealth.setString(1, health.getSmokingFrequency().equals("")|health.getSmokingFrequency()==null?"N":"Y");
					setHealth.setString(2, health.getDrinkingFrequency().equals("")|health.getDrinkingFrequency()==null?"N":"Y");
					setHealth.setString(3, health.getAdmissionReason().equals("")|health.getAdmissionReason()==null?"N":"Y");
					setHealth.setString(4, health.getDrinkingFrequency());
					setHealth.setString(5, health.getSmokingFrequency());
					setHealth.setString(6, health.getAdmissionReason());
					setHealth.setString(7, health.getFamilySicknessName().equals("")|health.getFamilySicknessName()==null?"N":"Y");
					setHealth.setString(8, health.getFamilySicknessName());
					setHealth.setInt(9, health.getBpRate());
					
					setHealth.setString(10, health.getBowelsCondition().equals("")|health.getBowelsCondition()==null?"N":"Y");
					setHealth.setString(11, health.getBowelsCondition());
					
					setHealth.setString(12, health.getSTDCondition().equals("")|health.getSTDCondition()==null?"N":"Y");
					setHealth.setString(13, health.getAdmissionReason());
					
					setHealth.setString(14, health.getSpecialCondition().equals("")|health.getSpecialCondition()==null?"N":"Y");
					setHealth.setString(15, health.getSpecialCondition());
					
					setHealth.setString(16, health.getDisabledConditon().equals("")|health.getDisabledConditon()==null?"N":"Y");
					setHealth.setString(17, health.getDisabledConditon());
					
					setHealth.setString(18, health.getHeartCondition().equals("")|health.getHeartCondition()==null?"N":"Y");
					setHealth.setString(19, health.getHeartCondition());
					
					setHealth.setString(20, health.getSlippedDdiskCondition().equals("")|health.getSlippedDdiskCondition()==null?"N":"Y");
					setHealth.setString(21, health.getSlippedDdiskCondition());
					
					setHealth.setString(22, health.getRespiratoryCondition().equals("")|health.getRespiratoryCondition()==null?"N":"Y");
					setHealth.setString(23, health.getRespiratoryCondition());
					setHealth.setString(24, policy.getPolicyNumber());
					setHealth.setString(25, healthVerificaition);
					status = setHealth.executeUpdate();
					
					
					System.out.println("Inserted health --------------------------------->");

					if (status == 1	& policyHolder.getBeneficiaries().size() > 0& policyHolder.getTrustee() == null) {

						PreparedStatement insertBeneficiary = null;
						String state5 = "INSERT INTO insurance.beneficiary (first_name, middle_name, last_name, address, percentage, "
								+ "relationship_id, mobile_number, email, dob, policy_number, "
								+ "nationality,date_added,title_id,sex) "
								+ "VALUES (?,?,?,?,?,"
								+ "?,?,?,?,?,"
								+ "now(),?,?,?)";
						for (Beneficiary ben : policyHolder.getBeneficiaries()) {
							insertBeneficiary = connection
									.prepareStatement(state5);
							insertBeneficiary.setString(1, ben.getFirstName());
							insertBeneficiary.setString(2, ben.getMiddleName());
							insertBeneficiary.setString(3, ben.getLastName());
							insertBeneficiary.setString(4, ben.getAddress());
							insertBeneficiary.setDouble(5, ben.getPercentage());
							insertBeneficiary.setInt(6,
									ben.getRelationship_id());
							insertBeneficiary.setString(7, ben.getMobile());
							insertBeneficiary.setString(8, ben.getEmail());
							insertBeneficiary.setString(9, ben.getDob());
							insertBeneficiary.setString(10,
									policy.getPolicyNumber());
							insertBeneficiary.setString(11,
									ben.getCountryOfBirth());
							insertBeneficiary.setString(12, ben.getTitle());
							insertBeneficiary.setString(13, ben.getSex());
							if (insertBeneficiary.executeUpdate() == 1) {
								System.out
										.println("Inserted Beneficiary --------------------------------->");
								status = 1;
							} else {
								status = 0;
							}

						}
						//if no trustee commit the data
						connection.commit();
					}

					//if there is a trustee
					if (status == 1	& policyHolder.getBeneficiaries().size() > 0 & policyHolder.getTrustee() != null) {

						PreparedStatement insertBeneficiary = null;
						String state5 = "INSERT INTO insurance.beneficiary (first_name, middle_name, last_name, address, percentage, "
								+ "relationship_id, mobile_number, email, dob, policy_number, "
								+ "nationality,date_added,title_id,sex) "
								+ "VALUES ("
								+ "?,?,?,?,?,"
								+ "?,?,?,?,?,"
								+ "now(),?,?,?)";
						for (Beneficiary ben : policyHolder.getBeneficiaries()) {
							insertBeneficiary = connection
									.prepareStatement(state5);
							insertBeneficiary.setString(1, ben.getFirstName());
							insertBeneficiary.setString(2, ben.getMiddleName());
							insertBeneficiary.setString(3, ben.getLastName());
							insertBeneficiary.setString(4, ben.getAddress());
							insertBeneficiary.setDouble(5, ben.getPercentage());
							insertBeneficiary.setInt(6,
									ben.getRelationship_id());
							insertBeneficiary.setString(7, ben.getMobile());
							insertBeneficiary.setString(8, ben.getEmail());
							insertBeneficiary.setString(9, ben.getDob());
							insertBeneficiary.setString(10,
									policy.getPolicyNumber());
							insertBeneficiary.setString(11,
									ben.getCountryOfBirth());
							insertBeneficiary.setString(12, ben.getTitle());
							insertBeneficiary.setString(13, ben.getSex());
							if (insertBeneficiary.executeUpdate() == 1) {
								String trusteeF = "INSERT INTO insurance.trustee (first_name, middle_name, last_name, address, mobile_number, "
										+ "email, dob, policy_number, date_added, beneficiary_realtionship,title_id) VALUES (?,?,?,?,?,?,?,?,now(),?,?)";

								PreparedStatement trusteePreparedStatement = null;
								trusteePreparedStatement = connection
										.prepareStatement(trusteeF);
								trusteePreparedStatement.setString(1,
										trustee.getFirstName());
								trusteePreparedStatement.setString(2,
										trustee.getMiddleName());
								trusteePreparedStatement.setString(3,
										trustee.getLastName());
								trusteePreparedStatement.setString(4,
										trustee.getAddress());
								trusteePreparedStatement.setString(5,
										trustee.getMobile());
								trusteePreparedStatement.setString(6,
										trustee.getEmail());
								trusteePreparedStatement.setString(7,
										trustee.getDob());
								trusteePreparedStatement.setString(8,
										policy.getPolicyNumber());
								trusteePreparedStatement.setInt(9,
										trustee.getRelationship_id());
								trusteePreparedStatement.setString(10,
										trustee.getTitle());
								if (trusteePreparedStatement.executeUpdate() == 1) {
									System.out
											.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
									status = 1;
								} else {
									System.out.println("no trustee inserted");
									status = 0;
								}
							} else {
								System.out
										.println("222222222222222222222222222222222222222222222");
								status = 0;
							}

						}

						
				
						connection.commit();
						
						
						
					}

					//

				}
				connection.close();
				ps.close();
				makingCustomerNumber.close();
				policyInsertion.closeOnCompletion();
				policyNumberUpdate.close();
				policyNumberVaue.close();

			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (status ==1){
			return policyNumber;
		}else{
			return null;
		}
	
	}

	public static String insertPolicyOwnerKnown(PolicyHolder policyHolder) {
		Policy policy = policyHolder.getPolicy();
		PolicyOwner customer = policy.getCustomer();
		Trustee trustee = policyHolder.getTrustee();
		String policyNumber = null;
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		int status = 0;

		policy.setCustomer(customer);
		policy.setCustomerNumber(customer.getCustomerNumber());

		PreparedStatement policyInsertion = null;
		PreparedStatement policyNumberUpdate = null;
		PreparedStatement policyNumberVaue = null;
		String number = "UPDATE customer_policy SET policy_number = CONCAT(policy_type_id,YEAR(CURDATE()),lpad(client_policy_id,5,'0'),region_id) WHERE customer_number= ? AND premium= ?";

		String approvedStats = (policy.isReinsurred()==true| policy.isMedicalCheckRequired()==true)?"N":"Y";
		
		
		
		String states = "INSERT INTO customer_policy (customer_number, policy_type_id, is_active, entry_date,  premium, initial_sum_assured, payment_frequency_id, payment_type_id,region_id,staff_id,agent_number,currency,is_approved) VALUES (?,?,'N',now(),?,?,?,?,?,?,?,?,?)";
		String policyNum = "SELECT policy_number FROM insurance.customer_policy where customer_number=? and premium=? and initial_sum_assured=?;";

		try {
			 connection.setAutoCommit(false);
			policyInsertion = connection.prepareStatement(states);
			policyInsertion.setString(1, policy.getCustomer()
					.getCustomerNumber());
			policyInsertion.setString(2, policy.getPolicyType());

			policyInsertion.setDouble(3, policy.getPremium());
			policyInsertion.setDouble(4, policy.getInitialSumAssured());
			policyInsertion.setInt(5, policy.getFrequency_id());
			policyInsertion.setInt(6, policy.getPayment_id());
			policyInsertion.setString(7, policy.getRegion_id());
			policyInsertion.setString(8, policy.getStaffID());
			policyInsertion.setString(9, policy.getAgentID());
			policyInsertion.setString(10,policy.getCurrency());
			policyInsertion.setString(11, approvedStats);
			status = policyInsertion.executeUpdate();

			policyNumberUpdate = connection.prepareStatement(number);
			policyNumberUpdate.setString(1, policy.getCustomerNumber());
			policyNumberUpdate.setDouble(2, policy.getPremium());
			policyNumberUpdate.executeUpdate();

			policyNumberVaue = connection.prepareStatement(policyNum);
			policyNumberVaue.setString(1, policy.getCustomerNumber());
			policyNumberVaue.setDouble(3, policy.getInitialSumAssured());
			policyNumberVaue.setDouble(2, policy.getPremium());
			ResultSet sets = policyNumberVaue.executeQuery();

			if (sets.next()) {
				policy.setPolicyNumber(sets.getString(1));
				//switching to policy number
				policyNumber = policy.getPolicyNumber();
				System.out.println("000000000000000 --- "+policyNumber);
				status = 1;
			}

			// Inserting agent Data sold
			if (policy.getAgentID() != null | !policy.getAgentID().equals("")) {
				if (insertAgentPolicySold(policy.getAgentID(),
						policy.getPolicyNumber()) == 1) {

					if (policy.getBank() != null) {
						status = insertClientBank(policy);

					} else if (policy.getSource() != null) {
						status = insertClientSource(policy);
					}

				}

			}
			// if policy bought directly

			else {
				if (policy.getBank() != (null)) {
					status = insertClientBank(policy);

				} else if (policy.getSource() != null) {
					status = insertClientSource(policy);
				}

			}

			if (status == 1) {
				System.out.println(" REsinsureance is "+ policy.isReinsurred());
				if (policy.isReinsurred()==true){
					insertReinsurance(policy);
				}
				PreparedStatement insertHolder = null;
				PreparedStatement setHealth = null;

				String insertHolderStatement = "INSERT INTO policy_holder(first_name, middle_name, last_name, mailing_address, region_id, "
						+ "mobile_number, email, dob, policy_number, "
						+ "date_added, marital_status_id, occupation_id, portrait_picture, signature, sex, children, birthplace, nationality,title_id)"
						+ " VALUES("
						+ "?,?,?,?,?,"
						+ "?,?,?,?,now(),"
						+ "?,?,?,?,?,"

						+ "?,?,?,?)";

				insertHolder = connection
						.prepareStatement(insertHolderStatement);
				insertHolder.setString(1, policyHolder.getFirstName());
				insertHolder.setString(2, policyHolder.getMiddleName());
				insertHolder.setString(3, policyHolder.getLastName());
				insertHolder.setString(4, policyHolder.getAddress());
				insertHolder.setString(5, policyHolder.getRegion_id());
				insertHolder.setString(6, policyHolder.getMobile());
				insertHolder.setString(7, policyHolder.getEmail());
				insertHolder.setString(8, policyHolder.getDob());
				insertHolder.setString(9, policy.getPolicyNumber());
				insertHolder.setInt(10, policyHolder.getMaritalStatusId());
				insertHolder.setInt(11, policyHolder.getOccupationId());
				insertHolder.setString(12, policyHolder.getPicture());
				insertHolder.setString(13, policyHolder.getSignature());
				insertHolder.setString(14, policyHolder.getSex());
				insertHolder.setInt(15, policyHolder.getChildren());
				insertHolder.setString(16, policyHolder.getPlaceOfBirth());
				insertHolder.setString(17, policyHolder.getCountryOfBirth());
				insertHolder.setString(18, policyHolder.getTitle());
				insertHolder.executeUpdate();

				System.out
						.println("Inserted Holder --------------------------------->");
				
				String healthInsert = "INSERT INTO insurance.medical(is_smoker, is_drinker, has_been_admitted, drinking_frequency, smoking_frequency, reason_admission, has_family_sickness, family_sickness_name, bprate, has_bowel, bowel_condition, std_status, std_condition, has_special_condition, special_condition, is_disabled, disability, has_heart_condition, heart_condition, has_slipped_disk, slip_disk_condition, has_respiratory_issue, respiratory_condition,policy_number, is_verified)"
						+ "  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				String healthVerificaition = (policy.isMedicalCheckRequired()==true)?"N":"Y";
				setHealth = connection.prepareStatement(healthInsert);
				Health health = policyHolder.getHealth();
				setHealth.setString(1, health.getSmokingFrequency().equals("")|health.getSmokingFrequency()==null?"N":"Y");
				setHealth.setString(2, health.getDrinkingFrequency().equals("")|health.getDrinkingFrequency()==null?"N":"Y");
				setHealth.setString(3, health.getAdmissionReason().equals("")|health.getAdmissionReason()==null?"N":"Y");
				setHealth.setString(4, health.getDrinkingFrequency());
				setHealth.setString(5, health.getSmokingFrequency());
				setHealth.setString(6, health.getAdmissionReason());
				setHealth.setString(7, health.getFamilySicknessName().equals("")|health.getFamilySicknessName()==null?"N":"Y");
				setHealth.setString(8, health.getFamilySicknessName());
				setHealth.setInt(9, health.getBpRate());
				
				setHealth.setString(10, health.getBowelsCondition().equals("")|health.getBowelsCondition()==null?"N":"Y");
				setHealth.setString(11, health.getBowelsCondition());
				
				setHealth.setString(12, health.getSTDCondition().equals("")|health.getSTDCondition()==null?"N":"Y");
				setHealth.setString(13, health.getAdmissionReason());
				
				setHealth.setString(14, health.getSpecialCondition().equals("")|health.getSpecialCondition()==null?"N":"Y");
				setHealth.setString(15, health.getSpecialCondition());
				
				setHealth.setString(16, health.getDisabledConditon().equals("")|health.getDisabledConditon()==null?"N":"Y");
				setHealth.setString(17, health.getDisabledConditon());
				
				setHealth.setString(18, health.getHeartCondition().equals("")|health.getHeartCondition()==null?"N":"Y");
				setHealth.setString(19, health.getHeartCondition());
				
				setHealth.setString(20, health.getSlippedDdiskCondition().equals("")|health.getSlippedDdiskCondition()==null?"N":"Y");
				setHealth.setString(21, health.getSlippedDdiskCondition());
				
				setHealth.setString(22, health.getRespiratoryCondition().equals("")|health.getRespiratoryCondition()==null?"N":"Y");
				setHealth.setString(23, health.getRespiratoryCondition());
				setHealth.setString(24, policy.getPolicyNumber());
				setHealth.setString(25, healthVerificaition);
				status = setHealth.executeUpdate();
									
				System.out
						.println("Inserted health --------------------------------->");

				if (status == 1 & policyHolder.getBeneficiaries().size() > 0
						& policyHolder.getTrustee() == null) {

					PreparedStatement insertBeneficiary = null;
					String state5 = "INSERT INTO insurance.beneficiary (first_name, middle_name, last_name, address, percentage, "
							+ "relationship_id, mobile_number, email, dob, policy_number, "
							+ "nationality,date_added,title_id,sex) "
							+ "VALUES ("
							+ "?,?,?,?,?,"
							+ "?,?,?,?,?,"
							+ "now(),?,?,?)";
					for (Beneficiary ben : policyHolder.getBeneficiaries()) {
						insertBeneficiary = connection.prepareStatement(state5);
						insertBeneficiary.setString(1, ben.getFirstName());
						insertBeneficiary.setString(2, ben.getMiddleName());
						insertBeneficiary.setString(3, ben.getLastName());
						insertBeneficiary.setString(4, ben.getAddress());
						insertBeneficiary.setDouble(5, ben.getPercentage());
						insertBeneficiary.setInt(6, ben.getRelationship_id());
						insertBeneficiary.setString(7, ben.getMobile());
						insertBeneficiary.setString(8, ben.getEmail());
						insertBeneficiary.setString(9, ben.getDob());
						insertBeneficiary.setString(10,
								policy.getPolicyNumber());
						insertBeneficiary
								.setString(11, ben.getCountryOfBirth());
						insertBeneficiary.setString(12, ben.getTitle());
						insertBeneficiary.setString(13, ben.getSex());
						if (insertBeneficiary.executeUpdate() == 1) {
							System.out
									.println("Inserted Beneficiary --------------------------------->");
							status = 1;
						} else {
							status = 0;
						}

					}

					connection.commit();
				}

				if (status == 1 & policyHolder.getBeneficiaries().size() > 0
						& policyHolder.getTrustee() != null) {

					PreparedStatement insertBeneficiary = null;
					String state5 = "INSERT INTO insurance.beneficiary (first_name, middle_name, last_name, address, percentage, "
							+ "relationship_id, mobile_number, email, dob, policy_number, "
							+ "nationality,date_added,title_id,sex) "
							+ "VALUES ("
							+ "?,?,?,?,?,"
							+ "?,?,?,?,?,"
							+ "now(),?,?,?)";
					for (Beneficiary ben : policyHolder.getBeneficiaries()) {
						insertBeneficiary = connection.prepareStatement(state5);
						insertBeneficiary.setString(1, ben.getFirstName());
						insertBeneficiary.setString(2, ben.getMiddleName());
						insertBeneficiary.setString(3, ben.getLastName());
						insertBeneficiary.setString(4, ben.getAddress());
						insertBeneficiary.setDouble(5, ben.getPercentage());
						insertBeneficiary.setInt(6, ben.getRelationship_id());
						insertBeneficiary.setString(7, ben.getMobile());
						insertBeneficiary.setString(8, ben.getEmail());
						insertBeneficiary.setString(9, ben.getDob());
						insertBeneficiary.setString(10,
								policy.getPolicyNumber());
						insertBeneficiary
								.setString(11, ben.getCountryOfBirth());
						insertBeneficiary.setString(12, ben.getTitle());
						insertBeneficiary.setString(13, ben.getSex());
						if (insertBeneficiary.executeUpdate() == 1) {
							String trusteeF = "INSERT INTO insurance.trustee (first_name, middle_name, last_name, address, mobile_number, "
									+ "email, dob, policy_number, date_added, beneficiary_realtionship,title_id) VALUES (?,?,?,?,?,?,?,?,now(),?,?)";
							PreparedStatement trusteePreparedStatement = null;
							trusteePreparedStatement = connection
									.prepareStatement(trusteeF);
							trusteePreparedStatement.setString(1,
									trustee.getFirstName());
							trusteePreparedStatement.setString(2,
									trustee.getMiddleName());
							trusteePreparedStatement.setString(3,
									trustee.getLastName());
							trusteePreparedStatement.setString(4,
									trustee.getAddress());
							trusteePreparedStatement.setString(5,
									trustee.getMobile());
							trusteePreparedStatement.setString(6,
									trustee.getEmail());
							trusteePreparedStatement.setString(7,
									trustee.getDob());
							trusteePreparedStatement.setString(8,
									policy.getPolicyNumber());
							trusteePreparedStatement.setInt(9,
									trustee.getRelationship_id());
							trusteePreparedStatement.setString(10,
									trustee.getTitle());
							if (trusteePreparedStatement.executeUpdate() == 1) {
								System.out
										.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
								status = 1;
							} else {
								System.out.println("no trustee inserted");
								status = 0;
							}
						} else {
							System.out
									.println("222222222222222222222222222222222222222222222");
							status = 0;
						}

					}

					connection.commit();
					
				}
				
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	policyNumber = policy.getPolicyNumber();
		
		if (status==1){
		return policyNumber;
		}else{
			return null;
		}
	
	}

	public static int insertClientBank(Policy policy) {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		int status = 0;
		String state = "INSERT INTO policy_bank (bank_id, policy_number, account_name,"
				+ "account_number,branch,region_id) VALUES (?, ?, ?,?,?,?);";
		try {
			// connection.setAutoCommit(false);
			ps = connection.prepareStatement(state);
			ClientBank cbank = policy.getBank();
			ps.setInt(1, cbank.getBankID());
			ps.setString(2, policy.getPolicyNumber());
			ps.setString(3, cbank.getAccountName());
			ps.setString(4, cbank.getAccountNumber());
			ps.setString(5, cbank.getBranch());
			ps.setString(6, cbank.getRegion());

			if (ps.executeUpdate() == 1) {
				status = 1;
				System.out.println("Excwelend");
				// connection.commit();
			} else {
				System.out.println("ahist");

			}
			ps.close();
			connection.close();
			return status;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}

	public static int insertAgentPolicySold(String agentNumber, String policy) {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		int status = 0;
		String state = "INSERT INTO insurance.agent_policy_sold(policy_number, agent_id) VALUES (?,?) ";
		try {

			ps = connection.prepareStatement(state);
			ps.setString(1, policy);
			ps.setString(2, agentNumber);
			if (ps.executeUpdate() == 1) {
				status = 1;
				System.out
						.println("Agent inserted ------------------------------------------------------->");
				// connection.commit();
			} else {
				System.out.println("ahist");

			}
			ps.close();
			connection.close();
			return status;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}

	public static int activatePolicyW(String policyNumber) {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		int status = 0;
		try {
			String[] details = getCustomerNumber(policyNumber);
			String customerNumbers = details[0];
			System.out.println("policy number -->" + policyNumber
					+ "  customer number to activate is " + customerNumbers);
			String email = details[1];
			String password = RandomStringUtils.randomAlphanumeric(6);
			String saltedPass = PasswordHash.createHash(password);
			String state = "UPDATE customer_policy set is_active='Y',activation_date=now() where policy_number=?";

			ps = connection.prepareStatement(state);
			ps.setString(1, policyNumber);

			if (ps.executeUpdate() == 1) {
				status = 1;
				// 6 for client

				status = insertWebUser(customerNumbers, saltedPass, 6);
				if (status == 1) {

					EmailUtility.sendActicationEmail(customerNumbers, password,
							email);
				}

				System.out.println("esesres");

			} else {
				System.out.println("ahist");

			}
			ps.close();
			connection.close();
		} catch (SQLException | NoSuchAlgorithmException
				| InvalidKeySpecException e1) {
			e1.printStackTrace();
			return 0;
		}
		return status;
	}
	
	public static int activatePolicy(String policyNumber,String datePaid)  {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		int status = 0;
		try {
			String[] details = getCustomerNumber(policyNumber);
			String customerNumbers = details[0];
			
			
			System.out.println("policy number -->" + policyNumber
					+ "  customer number to activate is " + customerNumbers);
			String email = details[1];
			String password = RandomStringUtils.randomAlphanumeric(6);
			String saltedPass = PasswordHash.createHash(password);
			String state = "UPDATE customer_policy set is_active='Y',activation_date=? where policy_number=?";
			System.out.println("Date activate is "+datePaid);
			ps = connection.prepareStatement(state);
			ps.setString(2, policyNumber);
			ps.setString(1, datePaid);
			if (ps.executeUpdate() == 1) {
				status = 1;
				// 6 for client
				if (!userExists(customerNumbers)){
				status = insertWebUser(customerNumbers, saltedPass, 6);
				if (status == 1) {

					EmailUtility.sendActicationEmail(customerNumbers, password,
							email);
				}
				ps.close();
				connection.close();
				System.out.println("esesres");

			} 
			}
			System.out.println(customerNumbers + "Already exist");
			
		} catch (SQLException | NoSuchAlgorithmException
				| InvalidKeySpecException e1) {
			e1.printStackTrace();
			return 0;
		}
		return status;
	}
	
	
	
	
	
	public static int updatePassword(User staff) {
		int stat = 0;
		String statement = "UPDATE users set password=? where staff_number=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		try {
			PreparedStatement query = conn.prepareStatement(statement);
			query.setString(2, staff.getStaffNumber());
			query.setString(1, staff.getPassword());
			stat = query.executeUpdate();

			query.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return stat;

	}	
	
	
	
	

	public static int insertWebUser(String customerNumber, String passwod,
			int role) {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		int status = 0;

		String state = "INSERT INTO insurance.users (staff_number, password, role_id) VALUES (?,?,?)";
		try {

			ps = connection.prepareStatement(state);
			ps.setString(1, customerNumber);
			ps.setString(2, passwod);
			ps.setInt(3, role);
			if (ps.executeUpdate() == 1) {
				status = 1;
				System.out.println("esesres");

			} else {
				System.out.println("ahist");

			}
			ps.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return status;
	}

	public static String[] getCustomerNumber(String policyNumber) {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;

		String[] customer = new String[2];
		String state = "SELECT c.customer_number,email FROM insurance.customer_policy as p join customer as c on p.customer_number=c.customer_number where p.policy_number=?";

		try {

			ps = connection.prepareStatement(state);
			ps.setString(1, policyNumber);
			ResultSet set = ps.executeQuery();
			if (set.next()) {
				customer[0] = set.getString(1);
				customer[1] = set.getString(2);
			}
			ps.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}
		System.out
				.println("Emitting customer number from getCustomer number arrya "
						+ customer[0]);
		return customer;
	}

	
	public static int insertClientSource(Policy policy) {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		int status = 0;
		String state = "INSERT INTO insurance.source (policy_number, policy_owner, company_id,staff_number, date_added) "
				+ "VALUES (?,?,?,?,now());";
		try {
			Source source = policy.getSource();

			ps = connection.prepareStatement(state);
			ps.setString(1, policy.getPolicyNumber());
			ps.setString(2, policy.getCustomer().getCustomerNumber());
			ps.setInt(3, source.getCompanyID());
			ps.setString(4, source.getStaffNumber());

			if (ps.executeUpdate() == 1) {
				status = 1;
				System.out.println("Inserted this shit");

			} else {
				System.out.println("ahist");

			}
			ps.close();
			connection.close();
			return status;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}

	public static int insertPolicyClaim(Claim claim) {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		int status = 0;
		String state = "INSERT INTO insurance.policy_claims "
				+ "(policy_number, amount, date_applied, claim_type_id, staff_id,status_id,branch_id ) VALUES (?,?,?,?,?,'N',?)";
		try {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(state);
			ps.setString(1, claim.getPolicyNumber());
			ps.setDouble(2, claim.getAmount());
			ps.setString(3,claim.getDateApplied());
			ps.setInt(4, claim.getClaimTypeId());
			ps.setString(5, claim.getStaffNumber());
			ps.setString(6, claim.getBranchID());

			if (ps.executeUpdate() == 1) {
				status = 1;
				System.out.println("Claim Inserted");
				connection.commit();
			} else {
				System.out.println("Claim not inserted");

			}
			ps.close();
			connection.close();
			return status;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}


	public static int insertBeneficiary(ArrayList<Beneficiary> beneficiaries)
			throws SQLException {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();

		int status = 1;
		PreparedStatement insertBeneficiary = null;
		String state = "INSERT INTO insurance.beneficiary (first_name, middle_name, last_name, address, percentage, "
				+ "relationship_id, mobile_number, email, dob, policy_number, nationality,date_added,title_id,sex) "
				+ "VALUES (" + "?,?,?,?,?," + "?,?,?,?,?," + "now(),?,?,?)";
		for (Beneficiary ben : beneficiaries) {
			insertBeneficiary = connection.prepareStatement(state);
			insertBeneficiary.setString(1, ben.getFirstName());
			insertBeneficiary.setString(2, ben.getMiddleName());
			insertBeneficiary.setString(3, ben.getLastName());
			insertBeneficiary.setString(4, ben.getAddress());
			insertBeneficiary.setDouble(5, ben.getPercentage());
			insertBeneficiary.setInt(6, ben.getRelationship_id());
			insertBeneficiary.setString(7, ben.getMobile());
			insertBeneficiary.setString(8, ben.getEmail());
			insertBeneficiary.setString(9, ben.getDob());
			insertBeneficiary.setString(10, ben.getPolicyNumber());
			insertBeneficiary.setString(11, ben.getCountryOfBirth());
			insertBeneficiary.setString(12, ben.getTitle());
			insertBeneficiary.setString(13, ben.getSex());
			if (insertBeneficiary.executeUpdate() == 1) {
				status = 0;
			}

		}

		return status;
	}

	public static BeanItemContainer<PolicyPayment> getPolicyPayments(
			Policy policy) throws SQLException {
		ArrayList<PolicyPayment> payments = new ArrayList<PolicyPayment>();
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();

		PreparedStatement ps = null;
		String state = "SELECT policy_number, payment_mode, amount, payer, date_paid, data_entry_staff FROM policy_payment as a inner join paymentmode as b on b.pay_id = a.payment_type_id where policy_number =?";
		ps = connection.prepareStatement(state);
		ps.setString(1, policy.getPolicyNumber());
		ResultSet set = ps.executeQuery();
		while (set.next()) {
			PolicyPayment pay = new PolicyPayment();
			pay.setPolicyNumber(set.getString(1));
			pay.setPaymnentType(set.getString(2));
			pay.setAmount(set.getDouble(3));
			pay.setPayee(set.getString(4));
			pay.setDatePaid(set.getString(5));
			pay.setStaffID(set.getString(6));
			payments.add(pay);
		}
		BeanItemContainer<PolicyPayment> pays = new BeanItemContainer<PolicyPayment>(
				PolicyPayment.class);
		pays.addAll(payments);
		ps.close();
		set.close();
		connection.close();
		return pays;
	}

	public static BeanItemContainer<PolicyPayment> getPolicyChequePayments(
			int payID, String stat) throws SQLException {
		ArrayList<PolicyPayment> payments = new ArrayList<PolicyPayment>();
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();

		PreparedStatement ps = null;
		String state = "SELECT policy_payment_id,cp.policy_number, cp.payment_type_id, amount, payer, date(date_paid), data_entry_staff,cheque_number,bank_id,p.policy_type_id ,insurance_type_id,is_cleared FROM insurance.policy_payment as cp join customer_policy  as p on cp.policy_number=p.policy_number join policy_type as t on t.policy_type_id=p.policy_type_id where cp.payment_type_id =? and is_cleared=?";
		ps = connection.prepareStatement(state);
		ps.setInt(1, payID);
		ps.setString(2, stat);
		ResultSet set = ps.executeQuery();
		while (set.next()) {
			PolicyPayment pay = new PolicyPayment(set.getInt(1));
			pay.setPolicyNumber(set.getString(2));
			pay.setPaymnentType(set.getString(3));
			pay.setAmount(set.getDouble(4));
			pay.setPayee(set.getString(5));
			pay.setDatePaid(set.getString(6));
			pay.setStaffID(set.getString(7));
			pay.setChequeNumber(set.getString(8));
			pay.setBranchID(set.getString(9));
			pay.setCleared(set.getString(12));
			// getting this figure to get the charges table and the type of
			// insurance for the statement
			pay.setPolicyType(set.getString(10));
			pay.setPolicyParentType(set.getInt(11));
			payments.add(pay);
		}
		ps.close();
		set.close();
		connection.close();
		pool.freeConnection();
		BeanItemContainer<PolicyPayment> pays = new BeanItemContainer<PolicyPayment>(
				PolicyPayment.class);
		pays.addAll(payments);

		return pays;
	}

	public static BeanItemContainer<PolicyPayment> getPolicyChequePaymentsMonthly(
			int payID, String stat, int year, int month) throws SQLException {
		ArrayList<PolicyPayment> payments = new ArrayList<PolicyPayment>();
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();

		PreparedStatement ps = null;
		String state = "SELECT policy_payment_id,cp.policy_number, cp.payment_type_id, amount, payer, date(date_paid), data_entry_staff,cheque_number,bank_id,p.policy_type_id ,insurance_type_id,is_cleared FROM insurance.policy_payment as cp join customer_policy  as p on cp.policy_number=p.policy_number join policy_type as t on t.policy_type_id=p.policy_type_id where cp.payment_type_id =? and is_cleared=? and month(date_paid)=? and year(date_paid)=?";
		ps = connection.prepareStatement(state);
		ps.setInt(1, payID);
		ps.setString(2, stat);
		ps.setInt(3, month);
		ps.setInt(4, year);
		ResultSet set = ps.executeQuery();
		while (set.next()) {
			PolicyPayment pay = new PolicyPayment(set.getInt(1));
			pay.setPolicyNumber(set.getString(2));
			pay.setPaymnentType(set.getString(3));
			pay.setAmount(set.getDouble(4));
			pay.setPayee(set.getString(5));
			pay.setDatePaid(set.getString(6));
			pay.setStaffID(set.getString(7));
			pay.setChequeNumber(set.getString(8));
			pay.setBranchID(set.getString(9));
			pay.setCleared(set.getString(12));
			// getting this figure to get the charges table and the type of
			// insurance for the statement
			pay.setPolicyType(set.getString(10));
			pay.setPolicyParentType(set.getInt(11));
			payments.add(pay);
		}
		BeanItemContainer<PolicyPayment> pays = new BeanItemContainer<PolicyPayment>(
				PolicyPayment.class);
		pays.addAll(payments);
		ps.close();
		set.close();
		connection.close();
		pool.freeConnection();
		return pays;
	}

	public static int insertBeneficiaryAndTrustee(
			ArrayList<Beneficiary> beneficiaries, Trustee trustee)
			throws SQLException {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();

		PreparedStatement cus = null;
		PreparedStatement ps = null;
		int status = 0;
		String state = "INSERT INTO insurance.beneficiary (first_name, middle_name, last_name, address, percentage, "
				+ "relationship_id, mobile_number, email, dob, policy_number, date_added) "
				+ "VALUES (" + "?,?,?,?,?," + "?,?,?,?,?," + "now())";

		String trusteeF = "INSERT INTO insurance.trustee (first_name, middle_name, last_name, address, mobile_number, "
				+ "email, dob, policy_number, date_added, beneficiary_realtionship,title_id) VALUES (?,?,?,?,?,?,?,?,now(),?,?)";
		for (Beneficiary ben : beneficiaries) {
			ps = connection.prepareStatement(state);
			ps.setString(1, ben.getFirstName());
			ps.setString(2, ben.getMiddleName());
			ps.setString(3, ben.getLastName());
			ps.setString(4, ben.getAddress());
			ps.setDouble(5, ben.getPercentage());
			ps.setInt(6, ben.getRelationship_id());
			ps.setString(7, ben.getMobile());
			ps.setString(8, ben.getEmail());
			ps.setString(9, ben.getDob());
			ps.setString(10, ben.getPolicyNumber());

			ps.executeUpdate();

		}
		cus = connection.prepareStatement(trusteeF);
		cus.setString(1, trustee.getFirstName());
		cus.setString(2, trustee.getMiddleName());
		cus.setString(3, trustee.getLastName());
		cus.setString(4, trustee.getAddress());
		cus.setString(5, trustee.getMobile());
		cus.setString(6, trustee.getEmail());
		cus.setString(7, trustee.getDob());
		cus.setString(8, trustee.getPolicyNumber());
		cus.setInt(9, trustee.getRelationship_id());
		cus.setString(10, trustee.getTitle());
		status = cus.executeUpdate();
		ps.close();
		cus.close();
		connection.close();
		return status;
	}

	public static int insertPolicy(Policy policy) {
		int status = 0;
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement policyInsertion = null;
		PreparedStatement policyNumberUpdate = null;
		PreparedStatement policyNumberVaue = null;
		String number = "UPDATE customer_policy SET policy_number = CONCAT(policy_type_id,YEAR(CURDATE()),lpad(client_policy_id,5,'0'),region_id) WHERE customer_number= ? AND premium= ?";

		String state = "INSERT INTO customer_policy (customer_number, policy_type_id, is_active, entry_date,  premium, initial_sum_assured, payment_frequency_id, payment_type_id,region_id) VALUES (?,?,'N',now(),?,?,?,?,?)";
		String policyNum = "SELECT policy_number FROM insurance.customer_policy where customer_number=? and premium=? and initial_sum_assured=?;";

		try {
			connection.setAutoCommit(false);
			policyInsertion = connection.prepareStatement(state);
			policyInsertion.setString(1, policy.getCustomer()
					.getCustomerNumber());
			policyInsertion.setString(2, policy.getPolicyType());

			policyInsertion.setDouble(3, policy.getPremium());
			policyInsertion.setDouble(4, policy.getInitialSumAssured());
			policyInsertion.setInt(5, policy.getFrequency_id());
			policyInsertion.setInt(6, policy.getPayment_id());
			policyInsertion.setString(7, policy.getRegion_id());
			policyInsertion.executeUpdate();

			policyNumberUpdate = connection.prepareStatement(number);
			policyNumberUpdate.setString(1, policy.getCustomerNumber());
			policyNumberUpdate.setDouble(2, policy.getPremium());
			policyNumberUpdate.executeUpdate();

			policyNumberVaue = connection.prepareStatement(policyNum);
			policyNumberVaue.setString(1, policy.getCustomerNumber());
			policyNumberVaue.setDouble(3, policy.getInitialSumAssured());
			policyNumberVaue.setDouble(2, policy.getPremium());
			ResultSet set = policyNumberVaue.executeQuery();
			if (set.next()) {
				policy.setPolicyNumber(set.getString(1));
				System.out.println("00000000000000000000000000000000000");

			}
			if (!policy.getBank().equals(null)) {
				status = insertClientBank(policy);

			} else if (policy.getSource() != null) {
				status = insertClientSource(policy);
			}

			// connection.commit();
			else {
				status = 1;
			}

			policyInsertion.close();
			set.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return status;
	}

	public static int insertPolicy(Policy policy, String agentId) {
		int status = 0;
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement policyInsertion = null;
		PreparedStatement policyNumberGenerate = null;
		PreparedStatement pol = null;
		PreparedStatement agent = null;
		String number = "UPDATE customer_policy SET policy_number = CONCAT(policy_type_id,YEAR(CURDATE()),lpad(client_policy_id,5,'0'),region_id) WHERE customer_number= ? AND premium= ?";
		String agentInsert = "INSERT INTO insurance.agent_policy_sold (policy_number,agent_id) VALUES (?,?)";
		String state = "INSERT INTO customer_policy (customer_number, policy_type_id, is_active, entry_date,  premium, initial_sum_assured, payment_frequency_id, payment_type_id,region_id) VALUES (?,?,'N',now(),?,?,?,?,?)";
		String policyNum = "SELECT policy_number FROM insurance.customer_policy where customer_number=? and premium=? and initial_sum_assured=?;";

		try {
			// connection.setAutoCommit(false);
			policyInsertion = connection.prepareStatement(state);
			policyInsertion.setString(1, policy.getCustomerNumber());
			policyInsertion.setString(2, policy.getPolicyType());

			policyInsertion.setDouble(3, policy.getPremium());
			policyInsertion.setDouble(4, policy.getInitialSumAssured());
			policyInsertion.setInt(5, policy.getFrequency_id());
			policyInsertion.setInt(6, policy.getPayment_id());
			policyInsertion.setString(7, policy.getRegion_id());
			policyInsertion.executeUpdate();

			policyNumberGenerate = connection.prepareStatement(number);
			policyNumberGenerate.setString(1, policy.getCustomerNumber());
			policyNumberGenerate.setDouble(2, policy.getPremium());
			policyNumberGenerate.executeUpdate();

			pol = connection.prepareStatement(policyNum);
			pol.setString(1, policy.getCustomerNumber());
			pol.setDouble(3, policy.getInitialSumAssured());
			pol.setDouble(2, policy.getPremium());
			ResultSet set = pol.executeQuery();
			if (set.next()) {
				policy.setPolicyNumber(set.getString(1));
				// connection.commit();

			}

			agent = connection.prepareStatement(agentInsert);
			agent.setString(1, policy.getPolicyNumber());
			agent.setString(2, agentId);
			status = agent.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return status;
	}

	public static int getAge(String date) {
		int age;

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();

		int thisYear = calendar.get(Calendar.YEAR);
		int thisYearMonth = calendar.get(Calendar.MONTH);
		int thisYearDay = calendar.get(Calendar.DAY_OF_MONTH);

		Calendar cal2 = Calendar.getInstance();
		try {
			cal2.setTime(df.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int dobYear = cal2.get(Calendar.YEAR);
		int dobMonth = cal2.get(Calendar.MONTH);
		int dobDay = cal2.get(Calendar.DAY_OF_MONTH);
		age = thisYear - dobYear;

		if (dobMonth > thisYearMonth) {
			age -= 1;

		} else if (dobMonth == thisYearMonth && dobDay > thisYearDay) {
			age -= 1;
		}
		return (age);
	}

	public static Date getDate(String g) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = df.parse(g);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;

	}

	public static int insertPolicyHolder(PolicyHolder policyHolder) {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		PreparedStatement setHealth = null;

		String state = "INSERT INTO policy_holder(first_name, middle_name, last_name, mailing_address, region_id, "
				+ "mobile_number, email, dob, policy_number, "
				+ "date_added, marital_status_id, occupation_id, portrait_picture, signature, sex, children, birthplace, nationality)"
				+ " VALUES(" + "?,?,?,?,?," + "?,?,?,?,now()," + "?,?,?,?,?,"

				+ "?,?,?)";
		try {
			ps = connection.prepareStatement(state);
			ps.setString(1, policyHolder.getFirstName());
			ps.setString(2, policyHolder.getMiddleName());
			ps.setString(3, policyHolder.getLastName());
			ps.setString(4, policyHolder.getAddress());
			ps.setString(5, policyHolder.getRegion_id());
			ps.setString(6, policyHolder.getMobile());
			ps.setString(7, policyHolder.getEmail());
			ps.setString(8, policyHolder.getDob());
			ps.setString(9, policyHolder.getPolicyNumber());
			ps.setInt(10, policyHolder.getMaritalStatusId());
			ps.setInt(11, policyHolder.getOccupationId());
			ps.setString(12, policyHolder.getPicture());
			ps.setString(13, policyHolder.getSignature());
			ps.setString(14, policyHolder.getSex());
			ps.setInt(15, policyHolder.getChildren());
			ps.setString(16, policyHolder.getPlaceOfBirth());
			ps.setString(17, policyHolder.getCountryOfBirth());
			ps.executeUpdate();

			String health = "INSERT INTO insurance.medical(policy_number, is_smoker, is_drinker, has_been_admitted,  has_family_sickness,"
					+ "drinking_frequency, smoking_frequency, reason_admission, family_sickness_name)"
					+ "  VALUES (?,?,?,?,?,?,?,?,?)";

			setHealth = connection.prepareStatement(health);
			setHealth.setString(1, policyHolder.getPolicyNumber());
			setHealth.setString(2, policyHolder.getIsSmoker());
			setHealth.setString(3, policyHolder.getIsDrinker());
			setHealth.setString(4, policyHolder.getHasBeenAdmitted());
			setHealth.setString(5, policyHolder.getHasFamilySickness());
			setHealth.setString(6, policyHolder.getDrinkingFrequency());
			setHealth.setString(7, policyHolder.getSmokingFrequency());
			setHealth.setString(8, policyHolder.getAdmissionReason());
			setHealth.setString(9, policyHolder.getFamilySicknessName());
			return setHealth.executeUpdate();

		} catch (SQLException e) {
			System.out.println("shit");
			e.printStackTrace();
			return 0;
		}
	}

	public static int insertHlderHealthCustomer(Policy policy) {
		int status = 0;
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		Statement ps = null;

		String state = "INSERT INTO insurance.medical(policy_number, is_smoker, is_drinker, has_been_admitted, drinking_frequency, smoking_frequency, reason_admission, has_family_sickness, family_sickness_name) VALUES(?,?,?,?,?,?,?,?,?)";

		System.out.println(state);
		try {
			ps = connection.createStatement();

			status = ps.executeUpdate(state);
			if (status == 1) {
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
	
	
	
	public static int insertReinsurance(Policy policy) {
		int status = 0;
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;

		String state = "INSERT INTO reinsurance(policy_number, amount, is_approved) VALUES(?,?,?)";

		try {
			ps = connection.prepareStatement(state);
			ps.setString(1, policy.getPolicyNumber());
			ps.setDouble(2, policy.getInitialSumAssured());
			ps.setString(3,"N");
			status = ps.executeUpdate();
			if (status == 1) {
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
	
	
	
	
	
	

public static boolean userExists(String customerNumber) {
	boolean status = false;
	ConnectionPool pool = new ConnectionPool();
	Connection connection = pool.getConnectionFromPool();
	PreparedStatement ps = null;

	String state = "SELECT staff_number from users where staff_number =?";

	System.out.println(state);
	try {
		ps = connection.prepareStatement(state);
		ps.setString(1,customerNumber);
		ResultSet set = ps.executeQuery();
		if (set.next()) {
			status=true;
			System.out.println("exists");

		} 
	} catch (SQLException e) {
		e.printStackTrace();

	}

	return status;
}
}

