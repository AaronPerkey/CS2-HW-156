package com.fmt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseLoader {

	public static Address getAddress(int addressId, Connection conn) {

		String street = null;
		String city = null;
		String state = null;
		String zipCode = null;
		String country = null;
		try {
		//getting the address
		PreparedStatement psj = null;
		ResultSet rsj = null;
		String addressQuery = "Select streetId, cityId, stateId, zipCodeId, countryId from Address where addressId = ?;";
		psj = conn.prepareStatement(addressQuery);
		psj.setInt(1, addressId);
		rsj = psj.executeQuery();
		if (rsj.next()) {
			int streetId = rsj.getInt("streetId");
			int cityId = rsj.getInt("cityId");
			int stateId = rsj.getInt("stateId");
			int zipCodeId = rsj.getInt("zipCodeId");
			int countryId = rsj.getInt("countryId");
			PreparedStatement psk = null;
			ResultSet rsk = null;
			//street
			String Query = "Select street from Street where streetId = ?;";
			psk = conn.prepareStatement(Query);
			psk.setInt(1, streetId);
			rsk = psk.executeQuery();
			if (rsk.next()) {
				street = rsk.getString("street");
			}
			rsk.close();
			psk.close();
			//city
			Query = "Select city from City where cityId = ?;";
			psk = conn.prepareStatement(Query);
			psk.setInt(1, cityId);
			rsk = psk.executeQuery();
			if (rsk.next()) {
				city = rsk.getString("city");
			}
			rsk.close();
			psk.close();
			//state
			Query = "Select state from State where stateId = ?;";
			psk = conn.prepareStatement(Query);
			psk.setInt(1, stateId);
			rsk = psk.executeQuery();
			if (rsk.next()) {
				state = rsk.getString("state");
			}
			rsk.close();
			psk.close();
			//zipCode
			Query = "Select zipCode from ZipCode where zipCodeId = ?;";
			psk = conn.prepareStatement(Query);
			psk.setInt(1, zipCodeId);
			rsk = psk.executeQuery();
			if (rsk.next()) {
				zipCode = rsk.getString("zipCode");
			}
			rsk.close();
			psk.close();
			//country
			Query = "Select country from Country where countryId = ?;";
			psk = conn.prepareStatement(Query);
			psk.setInt(1, countryId);
			rsk = psk.executeQuery();
			if (rsk.next()) {
				country = rsk.getString("country");
			}
			rsk.close();
			psk.close();
			
		}
		rsj.close();
		psj.close();
		Address address = new Address(street, city, state, zipCode, country);
		return address;
		} catch (SQLException e) {
			System.out.println("SQLException: Cannot find address with addressid" + addressId);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static List<Person> loadPersons() {
		List<Person> persons = new ArrayList<>();
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		//now get all the songs
		String personQuery = "Select personId, addressId, personCode, lastName, firstName from Person;";
		try {
			ps = conn.prepareStatement(personQuery);
			rs = ps.executeQuery();
			while(rs.next()) {
				Person e = null;
				String personCode = rs.getString("personCode");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				int personId = rs.getInt("personId");
				int addressId = rs.getInt("addressId");
				
				String street = null;
				String city = null;
				String state = null;
				String zipCode = null;
				String country = null;
				
				Address address = getAddress(addressId, conn);
				List<String> email = new ArrayList<>();

				email.add("Bob@gmail.com");


				e = new Person(personCode, firstName, lastName, address, email);
				persons.add(e);
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

		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return persons;
		
	}
	
}
