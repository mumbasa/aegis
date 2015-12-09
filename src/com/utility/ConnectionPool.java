package com.utility;

import java.util.*;
import java.sql.*;

class ConnectionPool {

	String databaseUrl = "jdbc:mysql://localhost/insurance?allowMultiQueries=true";
	String userName = "root";
	String password = "creeks86";
	Connection connection;
	Vector<Connection> connectionPool = new Vector<Connection>();

	public ConnectionPool() {
		try {
			initialize();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ConnectionPool(
	// String databaseName,
			String databaseUrl, String userName, String password) throws SQLException {
		this.databaseUrl = databaseUrl;
		this.userName = userName;
		this.password = password;
		initialize();
	}

	private void initialize() throws SQLException {
		// Here we can initialize all the information that we need
		initializeConnectionPool();
	}

	private void initializeConnectionPool() throws SQLException {
		while (!checkIfConnectionPoolIsFull()) {
			System.out
					.println("Connection Pool is NOT full. Proceeding with adding new connections");
			// Adding new connection instance until the pool is full
			connectionPool.addElement(createNewConnectionForPool());
		}
		System.out.println("Connection Pool is full.");
	}

	private synchronized boolean checkIfConnectionPoolIsFull() {
		final int MAX_POOL_SIZE = 10;

		// Check if the pool size
		if (connectionPool.size() < MAX_POOL_SIZE) {
			return false;
		}

		return true;
	}

	// Creating a connection
	private Connection createNewConnectionForPool() throws SQLException {
	

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(databaseUrl, userName,
					password);
			System.out.println("Connection: " + connection);
		} catch (SQLException sqle) {
			System.err.println("SQLException: " + sqle);
			return null;
		} catch (ClassNotFoundException cnfe) {
			connection.close();
			System.err.println("ClassNotFoundException: " + cnfe);
			return null;
		}

		return connection;
	}

	public synchronized Connection getConnectionFromPool() {
		Connection connection = null;

		// Check if there is a connection available. There are times when all
		// the connections in the pool may be used up
		if (connectionPool.size() > 0) {
			connection = (Connection) connectionPool.firstElement();
			connectionPool.removeElementAt(0);
		}
		// Giving away the connection from the connection pool
		return connection;
	}

	public synchronized void returnConnectionToPool(Connection connection) {
		// Adding the connection from the client back to the connection pool
		connectionPool.addElement(connection);
	}

	public static void main(String args[]) {
		@SuppressWarnings("unused")
		ConnectionPool ConnectionPool = new ConnectionPool();
	}

	public void freeConnection() {
		try {
		connection.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
}