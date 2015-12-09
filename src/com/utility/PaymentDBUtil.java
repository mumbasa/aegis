package com.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.data.Claim;
import com.data.LPStatement;
import com.data.PolicyPayment;

public class PaymentDBUtil {
	public static int insertPolicyPayment(PolicyPayment pay) {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		int status = 0;
		String state = "INSERT INTO insurance.policy_payment (policy_number, payment_type_id, amount, "
				+ "payer, date_paid, data_entry_staff) VALUES( ?, ?, ?, ?, now(), ?);";
		try {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(state);
			ps.setString(1, pay.getPolicyNumber());
			ps.setInt(2, pay.getPaymentModeId());
			ps.setDouble(3, pay.getAmount());
			ps.setString(4, pay.getPayee());
			ps.setString(5, pay.getStaffID());

			if (ps.executeUpdate() == 1) {
				status = 1;
				System.out.println("Excwelend");
				connection.commit();
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

	public static double getSurrenderValue(int age, int duration) {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		String table = "y" + duration;
		double amount = 0;
		String state = "SELECT " + table + " from Surrender_rates where age=?";

		try {

			ps = connection.prepareStatement(state);
			ps.setInt(1, age);
			ResultSet set = ps.executeQuery();
			if (set.next()) {
				amount = set.getDouble(1);
			}
			ps.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return amount;
	}

	public static double[] getPolicySurrender(String policyNumber) {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;

		double[] data = new double[3];
		String state = "SELECT age(date(dob)),age(date(activation_date))+1 FROM insurance.policy_holder as h join customer_policy as c on h.policy_number=c.policy_number where h.policy_number=?";
		double amount = 0;
		try {

			ps = connection.prepareStatement(state);
			ps.setString(1, policyNumber);
	
			ResultSet set = ps.executeQuery();
			if (set.next()) {
				data[0] = set.getDouble(1);
				data[1] = set.getDouble(2);

			}
			amount = getSurrenderValue((int) data[0], (int) data[1]);
			data[2] = amount;
			ps.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return data;
	}

	

	public static double getPolicyAgeDuration(String policyNumber) {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;

		int[] data = new int[2];
		String state = "SELECT age(date(dob)),age(date(activation_date))+1 FROM insurance.policy_holder as h join customer_policy as c on h.policy_number=c.policy_number where h.policy_number=?";
		double amount = 0;
		try {

			ps = connection.prepareStatement(state);
			ps.setString(1, policyNumber);
			ResultSet set = ps.executeQuery();
			if (set.next()) {
				data[0] = set.getInt(1);
				data[1] = set.getInt(2);

			}
			amount = getSurrenderValue(data[0], data[1]);
			ps.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return amount;
	}

	

	public static LPStatement getVariablesLifePolicy(PolicyPayment pay) {
		LPStatement statementInfo = null;
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement getInfo = null;
		System.err.println("pay amount ====>"+pay.getAmount());
		String getDetail = "SELECT  (SELECT accumulated_fund from policy_statement as a where a.policy_number=h.policy_number order by statement_id desc limit 1 ) as prevbal, LAST_DAY(curdate())as EOM, age(dob)as age, initial_sum_assured,agent_number, mortality_charge,(SELECT rate/100 FROM insurance.interest_rate where policy_type_id= cp.policy_type_id order by date_changed desc limit 1) as interest ,year_1_commision, next_years_commission,expense_charges,age(date(activation_date))+1 ,datediff(last_Day(now()),now()), (SELECT factor from death_benefits where policy_type_id=cp.policy_type_id order by id desc limit 1) as factor from customer_policy as cp join  policy_holder as h on h.policy_number=cp.policy_number join lp_charges as c on c.age=age(dob)  where cp.policy_type_id=? and h.policy_number=?  and c.policy_type_id=cp.policy_type_id order by c.lp_charges_id desc limit 1;";
		try {

			getInfo = connection.prepareStatement(getDetail);
		
			getInfo.setString(1, pay.getPolicyType());
			getInfo.setString(2, pay.getPolicyNumber());
			//
			ResultSet set = getInfo.executeQuery();
			if (set.next()) {

				System.out.println(set.getString(6)
						+ "<----ageent sssssssssssssssssssssssssage ->"
						+ set.getInt(3));
				statementInfo = new LPStatement();
				statementInfo.setAmountPaid(pay.getAmount());
				statementInfo.setPrevbal(set.getDouble(1));
				statementInfo.setEom(set.getString(2));
				statementInfo.setAge(set.getInt(3));
				statementInfo.setInitialSum(set.getDouble(4));
				statementInfo.setMortatilyCharge(set.getDouble(6));
				if (set.getString(5) == null) {
					statementInfo.setAgentNumer("");
				} else {
					statementInfo.setAgentNumer(set.getString(5));
				}
				statementInfo.setInterestRate(set.getDouble(7));
				statementInfo.setY1Commision(set.getDouble(8));
				statementInfo.setNextCommission(set.getDouble(9));
				statementInfo.setInsuranceCost(set.getDouble(10));
				statementInfo.setDuration(set.getInt(11));

				statementInfo.setDateDifference(set.getInt(12));
				statementInfo.setDeathBenefitFactor(set.getDouble(13));
				statementInfo.setFinalCommission();
				statementInfo.setTotalDeduction();
				statementInfo.setNetPremium();
				statementInfo.setInterestAmount();

				statementInfo.setTotal();
				statementInfo.setAccumulated();
				statementInfo.setDeathBenefit();
				statementInfo.setSurrender(getSurrenderValue(
						statementInfo.getAge(), statementInfo.getDuration()));

				System.out.println("this is net"
						+ statementInfo.getNetPremium());
				System.out.println("this is totad "
						+ statementInfo.getTotalDeduction());
				System.out.println("this is am"
						+ statementInfo.getInterestAmount());
				System.out.println("this is acc "
						+ statementInfo.getAccumulated());

				System.out.println("Mortality "
						+ statementInfo.getMortatilyCharge());

			}
			getInfo.close();
			set.close();
			connection.close();
		}

		catch (SQLException e) {
			e.printStackTrace();

		}
		return statementInfo;
	}

	public static LPStatement getVariablesLifePolicyCheque(PolicyPayment pay) {
		LPStatement statementInfo = null;
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement getInfo = null;
		System.out.println("Amount ------------>" + pay.getAmount());
		System.out.println("Date ------------>" + pay.getDatePaid());
		System.out
				.println("PolicyNumber ------------>" + pay.getPolicyNumber());

		String getDetail = "SELECT  (SELECT accumulated_fund from policy_statement as a where a.policy_number=? order by statement_id desc limit 1 ) as prevbal,"
				+ "LAST_DAY(?)as EOM, age(dob)as age, initial_sum_assured, mortality_charge,"
				+ "agent_number, "
				+ "(SELECT rate/100 FROM insurance.interest_rate where policy_type_id= cp.policy_type_id order by date_changed desc limit 1) as interest ,"
				+ " year_1_commision, next_years_commission,expense_charges,age(date(activation_date))+1 ,datediff(last_Day(?),?),"
				+ "(SELECT factor from death_benefits where policy_type_id=policy_type_id order by id desc limit 1) as factor "
				+ "from lp_charges as c join policy_holder as p on age(dob)=age join customer_policy as cp on p.policy_number=cp.policy_number where cp.policy_type_id=? and c.policy_type_id =cp.policy_type_id and p.policy_number=? order by c.lp_charges_id desc limit 1;";
		try {

			getInfo = connection.prepareStatement(getDetail);
			getInfo.setString(1, pay.getPolicyNumber());

			getInfo.setString(2, pay.getDatePaid());
			getInfo.setString(3, pay.getDatePaid());
			getInfo.setString(4, pay.getDatePaid());
			getInfo.setString(5, pay.getPolicyType());
			getInfo.setString(6, pay.getPolicyNumber());
			//
			ResultSet set = getInfo.executeQuery();
			if (set.next()) {

				System.out.println(set.getString(6)
						+ "<----ageent sssssssssssssssssssssssssage ->"
						+ set.getInt(3));
				statementInfo = new LPStatement();
				statementInfo.setAmountPaid(pay.getAmount());
				statementInfo.setPrevbal(set.getDouble(1));
				statementInfo.setEom(set.getString(2));
				statementInfo.setAge(set.getInt(3));
				statementInfo.setInitialSum(set.getDouble(4));
				statementInfo.setMortatilyCharge(set.getDouble(5));
				if (set.getString(6) == null) {
					statementInfo.setAgentNumer("");
				} else {
					statementInfo.setAgentNumer(set.getString(6));
				}
				statementInfo.setInterestRate(set.getDouble(7));
				statementInfo.setY1Commision(set.getDouble(8));
				statementInfo.setNextCommission(set.getDouble(9));
				statementInfo.setInsuranceCost(set.getDouble(10));
				statementInfo.setDuration(set.getInt(11));

				statementInfo.setDateDifference(set.getInt(12));
				statementInfo.setDeathBenefitFactor(set.getDouble(13));
				statementInfo.setFinalCommission();
				statementInfo.setTotalDeduction();
				statementInfo.setNetPremium();
				statementInfo.setInterestAmount();

				statementInfo.setTotal();
				statementInfo.setAccumulated();
				statementInfo.setDeathBenefit();
				statementInfo.setSurrender(getSurrenderValue(
						statementInfo.getAge(), statementInfo.getDuration()));

				System.out.println("this is net"
						+ statementInfo.getNetPremium());
				System.out.println("this is totad "
						+ statementInfo.getTotalDeduction());
				System.out.println("this is am"
						+ statementInfo.getInterestAmount());
				System.out.println("this is acc "
						+ statementInfo.getAccumulated());

				System.out.println("Mortality "
						+ statementInfo.getMortatilyCharge());

			}

		}

		catch (SQLException e) {
			e.printStackTrace();

		}
		return statementInfo;
	}

	public static LPStatement getVariablesWithDatePaidForAge(PolicyPayment pay) {
		LPStatement statementInfo = null;
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement getInfo = null;
		System.out.println("Amount ------------>" + pay.getAmount());
		System.out.println("Date ------------>" + pay.getDatePaid());
		System.out.println("PolicyNumber ------------>" + pay.getPolicyNumber());
		System.out.println("PolicyType ------------>" + pay.getPolicyType());
		String getDetail = "SELECT (SELECT accumulated_fund from policy_statement as a where a.policy_number=? order by statement_id desc limit 1 ) as prevbal, LAST_DAY(date(?))as EOM, any_age(dob,date(?))as age, "
				+ "initial_sum_assured, mortality_charge, agent_number,(SELECT rate/100 FROM insurance.interest_rate where policy_type_id= cp.policy_type_id order by date_changed desc limit 1) as interest , year_1_commision, next_years_commission,expense_charges,any_age(date(activation_date),date(?))+1 ,"
				
				//7
				+ "datediff(last_Day(?),date(?)), (SELECT factor from death_benefits where policy_type_id=? order by id desc limit 1) as factor from lp_charges as c join policy_holder as p on any_age(dob,date(?))=age join customer_policy as cp on p.policy_number=cp.policy_number where cp.policy_type_id=? and p.policy_number=? and c.policy_type_id=? order by c.date_added desc limit 1;";
		try {

			getInfo = connection.prepareStatement(getDetail);
			getInfo.setString(1, pay.getPolicyNumber());

			getInfo.setString(2, pay.getDatePaid().trim());
			getInfo.setString(3, pay.getDatePaid());
			getInfo.setString(4, pay.getDatePaid());
			getInfo.setString(5, pay.getDatePaid());
			getInfo.setString(6, pay.getDatePaid());
			getInfo.setString(7, pay.getPolicyType());
			getInfo.setString(8, pay.getDatePaid());
			getInfo.setString(9, pay.getPolicyType());
			getInfo.setString(10, pay.getPolicyNumber());
			getInfo.setString(11, pay.getPolicyType());
			//
			System.out.println();
			ResultSet set = getInfo.executeQuery();
			if (set.next()) {

				System.out.println(set.getString(6)+ "<----ageent sssssssssssssssssssssssssage ->"
						+ set.getInt(3));
				statementInfo = new LPStatement();
				statementInfo.setAmountPaid(pay.getAmount());
				statementInfo.setPrevbal(set.getDouble(1));
				statementInfo.setEom(set.getString(2));
				statementInfo.setAge(set.getInt(3));
				statementInfo.setInitialSum(set.getDouble(4));
				statementInfo.setMortatilyCharge(set.getDouble(5));
				if (set.getString(6) == null) {
					statementInfo.setAgentNumer("");
				} else {
					statementInfo.setAgentNumer(set.getString(6));
				}
				statementInfo.setInterestRate(set.getDouble(7));
				statementInfo.setY1Commision(set.getDouble(8));
				statementInfo.setNextCommission(set.getDouble(9));
				statementInfo.setInsuranceCost(set.getDouble(10));
				statementInfo.setDuration(set.getInt(11));

				statementInfo.setDateDifference(set.getInt(12));
				statementInfo.setDeathBenefitFactor(set.getDouble(13));
				statementInfo.setFinalCommission();
				statementInfo.setTotalDeduction();
				statementInfo.setNetPremium();
				statementInfo.setInterestAmount();

				statementInfo.setTotal();
				statementInfo.setAccumulated();
				statementInfo.setDeathBenefit();
				statementInfo.setSurrender(getSurrenderValue(
						statementInfo.getAge(), statementInfo.getDuration()));

				System.out.println("this is net"
						+ statementInfo.getNetPremium());
				System.out.println("this is totad "
						+ statementInfo.getTotalDeduction());
				System.out.println("this is am"
						+ statementInfo.getInterestAmount());
				System.out.println("this is acc "
						+ statementInfo.getAccumulated());

				System.out.println("Mortality "
						+ statementInfo.getMortatilyCharge());

			}
			getInfo.close();
			set.close();
			connection.close();
		}

		catch (SQLException e) {
			e.printStackTrace();

		}
		return statementInfo;
	}

	

	public static int insertPolicyProductPayment2(PolicyPayment pay) {

		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		PreparedStatement insertStatements = null;
		int status = 0;

		String state = "INSERT INTO insurance.policy_payment (policy_number, payment_type_id, amount, "
				+ "payer, date_paid, data_entry_staff,is_cleared,date_cleared) VALUES( ?, ?, ?, ?, now(), ?,'Y',curdate());";

		try {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(state);

			ps.setString(1, pay.getPolicyNumber());
			ps.setInt(2, pay.getPaymentModeId());
			ps.setDouble(3, pay.getAmount());
			ps.setString(4, pay.getPayee());
			ps.setString(5, pay.getStaffID());

			if (ps.executeUpdate() == 1) {

				LPStatement statements = getVariablesLifePolicy(pay);
				System.out.println("Excwelend  " + pay.getAmount());
				String insertState = "INSERT INTO policy_statement "
						+ "(policy_duration, previous_balance, payment_date, EOM, age, "
						+ "premium_paid, mortality_rate, expenses_charge, commission, total_deduction, "
						+ "net_premium, interest_rate, interest_amount, death_benefit,pa, "
						+ " partial_withdrawal, accumulated_fund, surrender_penalty, surrender_cash_value, "
						+ "policy_number)  " + "VALUES "
						+ "(?,?,now(),LAST_DAY(now()),?," + "?,?,?,?,?,"
						+ "?,?,?,?,0," + "0,?,?,?,?)";

				insertStatements = connection.prepareStatement(insertState);
				insertStatements.setInt(1, statements.getDuration());
				insertStatements.setDouble(2, statements.getPrevbal());
				insertStatements.setInt(3, statements.getAge());

				insertStatements.setDouble(4, pay.getAmount());
				insertStatements.setDouble(5, statements.getMortatilyCharge());
				insertStatements.setDouble(6, statements.getInsuranceCost());
				insertStatements.setDouble(7, statements.getFinalCommission());
				insertStatements.setDouble(8, statements.getTotalDeduction());

				insertStatements.setDouble(9, statements.getNetPremium());
				insertStatements.setDouble(10,
						statements.getInterestRate() * 100);
				insertStatements.setDouble(11, statements.getInterestAmount());
				insertStatements.setDouble(12, statements.getDeathBenefit());

				insertStatements.setDouble(13, statements.getAccumulated());
				insertStatements.setDouble(14, statements.getSurrender());
				insertStatements.setDouble(15, statements.getAccumulated()
						- statements.getSurrender());
				insertStatements.setString(16, pay.getPolicyNumber());
				status = insertStatements.executeUpdate();

				if (status == 1) {
					System.out.println("Excellent " + statements.getEom());
					connection.commit();
				}

			} else {
				System.out.println("ahist");

			}
			insertStatements.close();
			ps.close();
			connection.close();
		}

		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return status;
	}

	public static int insertPolicyStandingOrderPayment(PolicyPayment pay) {

		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		PreparedStatement insertStatements = null;
		int status = 0;

		String state = "INSERT INTO insurance.policy_payment (policy_number, payment_type_id, amount, "
				+ "payer, date_paid, data_entry_staff,is_cleared,date_cleared) VALUES( ?, ?, ?, ?, ?, ?,'Y',curdate());";

		try {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(state);

			ps.setString(1, pay.getPolicyNumber());
			ps.setInt(2, pay.getPaymentModeId());
			ps.setDouble(3, pay.getAmount());
			ps.setString(4, pay.getPayee());
			ps.setString(5, pay.getDatePaid());
			ps.setString(6, pay.getStaffID());
			// ps.setString(7,pay.getBranchID());

			if (ps.executeUpdate() == 1) {
				// LPStatement statements = getVariablesLifePolicyCheque(pay);
				LPStatement statements = getVariablesWithDatePaidForAge(pay);
				System.out.println("Excwelend  " + pay.getAmount());
				String insertState = "INSERT INTO policy_statement "
						+ "(policy_duration, previous_balance, payment_date, EOM, age, "
						+ "premium_paid, mortality_rate, expenses_charge, commission, total_deduction, "
						+ "net_premium, interest_rate, interest_amount, death_benefit,pa, "
						+ " partial_withdrawal, accumulated_fund, surrender_penalty, surrender_cash_value, "
						+ "policy_number)  " + "VALUES "
						+ "(?,?,?,LAST_DAY(payment_date),?," + "?,?,?,?,?,"
						+ "?,?,?,?,0," + "0,?,?,?,?)";

				insertStatements = connection.prepareStatement(insertState);
				System.out.println(statements.getDuration()+"----------> Duation ");
				insertStatements.setInt(1, statements.getDuration());
				insertStatements.setDouble(2, statements.getPrevbal());
				insertStatements.setString(3, pay.getDatePaid());
				insertStatements.setInt(4, statements.getAge());

				insertStatements.setDouble(5, pay.getAmount());
				insertStatements.setDouble(6, statements.getMortatilyCharge());
				insertStatements.setDouble(7, statements.getInsuranceCost());
				insertStatements.setDouble(8, statements.getFinalCommission());
				insertStatements.setDouble(9, statements.getTotalDeduction());

				insertStatements.setDouble(10, statements.getNetPremium());
				insertStatements.setDouble(11,
						statements.getInterestRate() * 100);
				insertStatements.setDouble(12, statements.getInterestAmount());
				insertStatements.setDouble(13, statements.getDeathBenefit());

				insertStatements.setDouble(14, statements.getAccumulated());
				insertStatements.setDouble(15, statements.getSurrender());
				insertStatements.setDouble(16, statements.getAccumulated()
						- statements.getSurrender());
				insertStatements.setString(17, pay.getPolicyNumber());
				status = insertStatements.executeUpdate();

				if (status == 1) {
					System.out.println("Excellent " + statements.getEom());
					connection.commit();
				}

			} else {
				System.out.println("ahist");

			}
			ps.close();

			insertStatements.close();
			connection.close();
		}

		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return status;
	}

	public static int insertPolicyChequeStatement(PolicyPayment pay) {

		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();

		PreparedStatement insertStatements = null;
		int status = 0;

		try {
			LPStatement statements = getVariablesLifePolicyCheque(pay);
			System.out.println("Excwelend  " + pay.getAmount());
			String insertState = "INSERT INTO policy_statement "
					+ "(policy_duration, previous_balance, payment_date, EOM, age, "
					+ "premium_paid, mortality_rate, expenses_charge, commission, total_deduction, "
					+ "net_premium, interest_rate, interest_amount, death_benefit,pa, "
					+ " partial_withdrawal, accumulated_fund, surrender_penalty, surrender_cash_value, "
					+ "policy_number)  " + "VALUES "
					+ "(?,?,?,LAST_DAY(payment_date),?," + "?,?,?,?,?,"
					+ "?,?,?,?,0," + "0,?,?,?,?)";

			insertStatements = connection.prepareStatement(insertState);
			insertStatements.setInt(1, statements.getDuration());
			insertStatements.setDouble(2, statements.getPrevbal());
			insertStatements.setString(3, pay.getDatePaid());

			insertStatements.setInt(4, statements.getAge());

			insertStatements.setDouble(5, pay.getAmount());
			insertStatements.setDouble(6, statements.getMortatilyCharge());
			insertStatements.setDouble(7, statements.getInsuranceCost());
			insertStatements.setDouble(8, statements.getFinalCommission());
			insertStatements.setDouble(9, statements.getTotalDeduction());

			insertStatements.setDouble(10, statements.getNetPremium());
			insertStatements.setDouble(11, statements.getInterestRate() * 100);
			insertStatements.setDouble(12, statements.getInterestAmount());
			insertStatements.setDouble(13, statements.getDeathBenefit());

			insertStatements.setDouble(14, statements.getAccumulated());
			insertStatements.setDouble(15, statements.getSurrender());
			insertStatements.setDouble(16, statements.getAccumulated()
					- statements.getSurrender());
			insertStatements.setString(17, pay.getPolicyNumber());
			status = insertStatements.executeUpdate();

			if (status == 1) {
				System.out.println("Excellent " + statements.getEom());

			}
			insertStatements.close();
			connection.close();
		}

		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

		return status;
	}

	public static int insertPolicyChequePayment(PolicyPayment pay) {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		int status = 0;
		String state = "INSERT INTO insurance.policy_payment "
				+ "(policy_number, payment_type_id, amount, "
				+ "payer, date_paid, data_entry_staff,cheque_number,bank_id,is_cleared) "
				+ "VALUES( ?, ?, ?, ?, now(),?, ?,?,'N');";

		try {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(state);

			ps.setString(1, pay.getPolicyNumber());
			ps.setInt(2, pay.getPaymentModeId());
			ps.setDouble(3, pay.getAmount());
			ps.setString(4, pay.getPayee());
			ps.setString(5, pay.getStaffID());

			ps.setString(6, pay.getChequeNumber());
			ps.setInt(7, pay.getBank_id());
			if (ps.executeUpdate() == 1) {
				status = 1;
				System.out.println("Excwelend");
				connection.commit();
			} else {
				System.out.println("ahist");

			}

			return status;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}

	public static int insertPolicyClaimStatement(Claim claim) {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		int status = 0;

		String state = "INSERT INTO statement(policy_number,previous_balance,payment_date,"
				+ "partial_withdrawal,accumulated_fund,surrender_penalty,cash_value,age,policy_duration) "
				+ " VALUES (?,?,now(),?,?,?,?,?,?) ";
		try {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(state);
			double[] data = getPolicySurrender(claim.getPolicyNumber());
			double pre = getPolicyAccumulatedFund(claim.getPolicyNumber());
			ps.setString(1, claim.getPolicyNumber());
			ps.setDouble(2, pre);
			ps.setDouble(3, claim.getAmount());
			ps.setDouble(4, pre - claim.getAmount());
			ps.setDouble(5, getPolicySurrender(claim.getPolicyNumber())[2]);
			ps.setDouble(6, pre - claim.getAmount() - data[2]);
			ps.setInt(7, (int) data[0]);
			ps.setInt(8, (int) data[1]);
			if (ps.executeUpdate() == 1) {
				status = 1;
				System.out.println("Excwelend");
				connection.commit();
			} else {
				System.out.println("bot done");

			}
			ps.close();
			connection.close();
			return status;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}

	// PolicyEntryforthe policystatement_table
	public static int insertPolicyClaimStatement2(Claim claim) {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		int status = 0;

		String state = "INSERT INTO policy_statement(policy_number,previous_balance,payment_date,"
				+ "partial_withdrawal,accumulated_fund,surrender_penalty,surrender_cash_value,age,policy_duration,death_benefit) "
				+ " VALUES (?,?,now(),?,?,?,?,?,?,?) ";
		try {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(state);
			double[] data = getPolicySurrender(claim.getPolicyNumber());
			double[] statementData = getPolicyAccumulatedFund2(claim
					.getPolicyNumber());

			ps.setString(1, claim.getPolicyNumber());
			ps.setDouble(2, statementData[0]);
			ps.setDouble(3, claim.getAmount());
			ps.setDouble(4, statementData[0] - claim.getAmount());
			ps.setDouble(5, getPolicySurrender(claim.getPolicyNumber())[2]);
			ps.setDouble(6, statementData[0] - claim.getAmount() - data[2]);
			ps.setInt(7, (int) data[0]);
			ps.setInt(8, (int) data[1]);
			ps.setDouble(
					9,
					Math.max(statementData[1],
							(statementData[0] - claim.getAmount()) * 1.2));
			if (ps.executeUpdate() == 1) {
				status = 1;
				System.out.println("Excwelend");
				connection.commit();
			} else {
				System.out.println("bot done");

			}
			ps.close();
			connection.close();
			return status;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}

	public static int insertApprovedClaimsStatement(String policyNumber,
			double approvedAmount) {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnectionFromPool();
		PreparedStatement ps = null;
		int status = 0;

		String state = "INSERT INTO policy_statement(policy_number,previous_balance,payment_date,"
				+ "partial_withdrawal,accumulated_fund,surrender_penalty,surrender_cash_value,age,policy_duration,death_benefit,EOM) "
				+ " VALUES (?,?,now(),?,?,?,?,?,?,?,last_day(now())) ";
		try {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(state);
			double[] data = getPolicySurrender(policyNumber);
			double[] statementData = getPolicyAccumulatedFund2(policyNumber);

			ps.setString(1, policyNumber);
			ps.setDouble(2, statementData[0]);
			ps.setDouble(3, approvedAmount);
			ps.setDouble(4, statementData[0] - approvedAmount);
			ps.setDouble(5, getPolicySurrender(policyNumber)[2]);
			ps.setDouble(6, statementData[0] - approvedAmount - data[2]);
			ps.setInt(7, (int) data[0]);
			ps.setInt(8, (int) data[1]);
			ps.setDouble(9, Math.max(statementData[1],
					(statementData[0] - approvedAmount) * 1.2));
			if (ps.executeUpdate() == 1) {
				status = 1;
				System.out.println("Excwelend");
				connection.commit();
			} else {
				System.out.println("bot done");

			}
			ps.close();
			connection.close();
			return status;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}
	public static double getPolicyAccumulatedFund(String policyNumber)
			throws SQLException {
		double data = 0;
		String statement = "SELECT accumulated_fund from statement where policy_number = ? order by statement_id desc limit 1 ";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, policyNumber);

		ResultSet set = query.executeQuery();
		if (set.next()) {
			data = set.getDouble(1);

		}
		query.close();
		set.close();
		conn.close();

		return data;

	}

	public static double[] getPolicyAccumulatedFund2(String policyNumber)
			throws SQLException {
		double[] data = new double[2];
		String statement = "SELECT accumulated_fund,initial_sum_assured from policy_statement as s join customer_policy as p on s.policy_number=p.policy_number where s.policy_number=? order by statement_id desc limit 1 ";
		ConnectionPool pool = new ConnectionPool();
		Connection conn = pool.getConnectionFromPool();
		PreparedStatement query = conn.prepareStatement(statement);
		query.setString(1, policyNumber);

		ResultSet set = query.executeQuery();
		if (set.next()) {
			data[0] = set.getDouble(1);
			data[1] = set.getDouble(2);
		}
		query.close();
		set.close();
		conn.close();
		;

		return data;

	}
}
