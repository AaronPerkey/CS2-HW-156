package com.fmt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseLoader {

	public List<Person> loadPersons() throws SQLException {
		List<Person> persons = new ArrayList<>();
		
		String url = DatabaseInfo.URL;
		String username = DatabaseInfo.USERNAME;
		String password = DatabaseInfo.PASSWORD;
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		//now get all the songs
		String songQuery = "SELECT * FROM Person;";
		try {
			ps = conn.prepareStatement(songQuery);
			rs = ps.executeQuery();
			while(rs.next()) {
				Person person = new Person(rs.getString("personCode"));
				persons.add(person);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		conn.close();
		return persons;
		
	}
}
