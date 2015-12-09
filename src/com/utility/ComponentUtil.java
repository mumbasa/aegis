package com.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class ComponentUtil {

	
	public static HashMap<Integer, String> getOccupation() throws SQLException {
		HashMap<Integer, String> data = new HashMap<Integer, String>();
		String statement = "Select occupation_id,occupation FROM occupation";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getInt(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();
		return data;

	}

	
	public static HashMap<Integer, String> getOccupationClass(){
		HashMap<Integer, String> data = new HashMap<Integer, String>();
		String statement = "SELECT occ_id,occupation_class FROM insurance.occupation_class";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query;
		try {
			query = conn.createStatement();
			ResultSet set = query.executeQuery(statement);
			while (set.next()) {
				data.put(set.getInt(1), set.getString(2));

			}
			query.close();
			set.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return data;

	}

	public static HashMap<String, Double> getOccupationClassRates(){
		HashMap<String, Double> data = new HashMap<String, Double>();
		String statement = "SELECT occupation_class,adjustment FROM insurance.occupation_class";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query;
		try {
			query = conn.createStatement();
			ResultSet set = query.executeQuery(statement);
			while (set.next()) {
				data.put(set.getString(1), set.getDouble(2));

			}
			query.close();
			set.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return data;

	}
	
	
	public static HashMap<Integer, String> getClaimTypes() throws SQLException {
		HashMap<Integer, String> data = new HashMap<Integer, String>();
		String statement = "Select claim_type_id, claim_type FROM claims_type";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getInt(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();

		return data;

	}
	public static HashMap<String, String> getCurrencies() throws SQLException {
		HashMap<String, String> data = new HashMap<String, String>();
		String statement = "Select * FROM currency";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getString(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();

		return data;

	}

	public static HashMap<Integer, String> getMaritalStatuses()
			throws SQLException {
		HashMap<Integer, String> data = new HashMap<Integer, String>();
		String statement = "Select marital_status_id, marital_status FROM marital_status";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getInt(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();

		return data;

	}

	public static HashMap<Integer, String> getRelationships()
			throws SQLException {
		HashMap<Integer, String> data = new HashMap<Integer, String>();
		String statement = "Select relationship_id, relationship FROM relationships";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getInt(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();

		return data;

	}

	public static HashMap<Integer, String> getBanks() throws SQLException {
		HashMap<Integer, String> data = new HashMap<Integer, String>();
		String statement = "Select bank_id, bank_name FROM bank";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getInt(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();

		return data;

	}

	public static HashMap<String, String> getAllCustomers() throws SQLException {
		HashMap<String, String> data = new HashMap<String, String>();
		String statement = "SELECT  customer_number FROM insurance.customer";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getString(1), set.getString(1));

		}
		query.close();
		set.close();
		conn.close();

		return data;

	}
	
	
	public static HashMap<String, String> getAllCustomersNames() throws SQLException {
		HashMap<String, String> data = new HashMap<String, String>();
		String statement = "SELECT  customer_number,concat (first_name,' ',middle_name,' ',last_name) FROM insurance.customer";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getString(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();

		return data;

	}

	
	
	public static HashMap<String, String> getAllGroups() throws SQLException {
		HashMap<String, String> data = new HashMap<String, String>();
		String statement = "SELECT group_number,company FROM insurance.group_client;";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getString(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();

		return data;

	}
	
	
	
	public static HashMap<Integer, String> getCompanies() throws SQLException {
		HashMap<Integer, String> data = new HashMap<Integer, String>();
		String statement = "Select company_id, company_name FROM company";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getInt(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();
		return data;

	}

	public static HashMap<Integer, String> getPaymentMode() throws SQLException {
		HashMap<Integer, String> data = new HashMap<Integer, String>();
		String statement = "SELECT * FROM insurance.paymentmode;";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getInt(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();
		return data;

	}

	public static HashMap<String, String> getPolicyTypes() throws SQLException {
		HashMap<String, String> data = new HashMap<String, String>();
		String statement = "Select policy_type_id, policy_type FROM policy_type";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getString(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();

		return data;

	}

	public static HashMap<String, String[]> getPolicyTypes2()
			throws SQLException {
		HashMap<String, String[]> data = new HashMap<String, String[]>();
		String statement = "Select policy_type_id, policy_type,insurance_type_id FROM policy_type";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			String[] datas = new String[2];
			datas[0] = set.getString(2);
			datas[1] = set.getString(3);
			data.put(set.getString(1), datas);

		}
		query.close();
		set.close();
		conn.close();

		return data;

	}

	public static HashMap<Integer, String> getInsuranceTypes()
			throws SQLException {
		HashMap<Integer, String> data = new HashMap<Integer, String>();
		String statement = "SELECT * FROM insurance.insurance_types";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getInt(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();
		return data;

	}

	public static HashMap<Integer, String> getPolicyTypes(String policyNumber)
			throws SQLException {
		HashMap<Integer, String> data = new HashMap<Integer, String>();
		String statement = "SELECT insurance_type_id,c.policy_type_id FROM insurance.customer_policy as c join policy_type as t on c.policy_type_id =t.policy_type_id where policy_number=?";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, policyNumber);

		ResultSet set = query.executeQuery();
		while (set.next()) {
			data.put(set.getInt(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();
		return data;

	}
	
	
	public static HashMap<String, String> getGroupPolicyTypes()
			throws SQLException {
		HashMap<String, String> data = new HashMap<String, String>();
		String statement = "SELECT policy_type_id,policy_type FROM insurance.policy_type where is_group='Y'";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
	
		ResultSet set = query.executeQuery();
		while (set.next()) {
			data.put(set.getString(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();
		return data;

	}
	
	
	
	public static HashMap<String, String> getAgents() throws SQLException {
		HashMap<String, String> data = new HashMap<String, String>();
		String statement = "Select agent_number, concat(last_name,' ',first_name) as t FROM agent";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getString(1), set.getString(2));

		}
		set.close();
		conn.close();

		return data;

	}


	public static HashMap<Integer, String> getPaymentTypes()
			throws SQLException {
		HashMap<Integer, String> data = new HashMap<Integer, String>();
		String statement = "Select payment_type_id, payment_type FROM payment_type";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getInt(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();

		return data;

	}

	public static HashMap<Integer, String> getPaymentFrequency()
			throws SQLException {
		HashMap<Integer, String> data = new HashMap<Integer, String>();
		String statement = "SELECT * FROM insurance.payment_frequency;";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getInt(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();

		return data;

	}

	public static HashMap<String, String> getProductClass(int a)
			throws SQLException {
		HashMap<String, String> data = new HashMap<String, String>();
		String statement = "SELECT * FROM insurance.policy_type where insurance_type_id="
				+ a;
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getString(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();
		return data;

	}

	public static HashMap<String, String> getPolicyProductClass(String a)
			throws SQLException {
		HashMap<String, String> data = new HashMap<String, String>();
		String statement = "SELECT * FROM insurance.policy_type where insurance_type_id="
				+ a;
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getString(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();
		return data;

	}

	public static HashMap<String, String> getRegion() throws SQLException {
		HashMap<String, String> data = new HashMap<String, String>();
		String statement = "SELECT * FROM insurance.regions;";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getString(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();
		return data;

	}

	public static HashMap<String, String> getPolicies() throws SQLException {
		HashMap<String, String> data = new HashMap<String, String>();
		String statement = "SELECT policy_number ,is_active FROM insurance.customer_policy;";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getString(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();

		return data;

	}
	
	public static HashMap<String, String> getApprovedPolicies() throws SQLException {
		HashMap<String, String> data = new HashMap<String, String>();
		String statement = "SELECT p.policy_number ,is_active FROM insurance.customer_policy as p join medical as m on p.policy_number = m.policy_number where p.is_approved='Y' and m.is_verified='Y' and p.is_active='Y'";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getString(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();

		return data;

	}
	
	
	public static HashMap<String, String> getApprovedPoliciesForClaim() throws SQLException {
		HashMap<String, String> data = new HashMap<String, String>();
		String statement = "SELECT p.policy_number ,is_active FROM insurance.customer_policy as p join medical as m on p.policy_number = m.policy_number where p.is_approved='Y' and m.is_verified='Y' and p.is_active='Y'";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		Statement query = conn.createStatement();

		ResultSet set = query.executeQuery(statement);
		while (set.next()) {
			data.put(set.getString(1), set.getString(2));

		}
		query.close();
		set.close();
		conn.close();

		return data;

	}
	
}
