package com.fmt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * Login to the CSE server to connect to the SQL database
 * 
 * @author Kyle Gann, Aaron Perkey
 *
 */

public class DatabaseInfo {

	private static final Logger LOGGER = LogManager.getLogger(DatabaseInfo.class);
	
	/**
	 * Connection parameters that are necessary for CSE's configuration
	 */
	public static final String PARAMETERS = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowMultiQueries=true";

	public static final String USERNAME = "aperkey";
	public static final String PASSWORD = "zCCVkoB4";
	public static final String URL = "jdbc:mysql://cse.unl.edu/" + USERNAME + PARAMETERS;

	public static Connection openConnectSQL() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			LOGGER.error("Incorrect Password", e);
			throw new RuntimeException(e);
		}

		return conn;
	}
	
	public static void closeConnection(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: Could not close connection, something is very wrong");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}	
}
