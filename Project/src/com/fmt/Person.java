package com.fmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 
 * models a person who might be a customer manager or salesperson
 *
 * @author Kyle Gann, Aaron Perkey
 * 
 */

public class Person {

	private Integer personId;
	private String personCode;
	private String firstName;
	private String lastName;
	private Address address;
	private List<String> emails;
	
	public Person(String personCode, String firstName, String lastName, Address address, List<String> email) {
		this.personCode = personCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.emails = email;
	}
	
	//parseing person from sql
	public Person(Integer personId, String personCode, String firstName, String lastName, Address address,
			List<String> emails) {
		this.personId = personId;
		this.personCode = personCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.emails = emails;
	}
	
	public Person(Integer personId, String personCode, String firstName, String lastName, Address address) {
		this.personId = personId;
		this.personCode = personCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}
	
	//TODO CHANGED CODE
	public Person(Person person, List<String> emails) {
		this.personId = person.getPersonId();
		this.personCode = person.getPersonCode();
		this.firstName = person.getFirstName();
		this.lastName = person.getLastName();
		this.address = person.getAddress();
		this.emails = emails;
	}

	public Person(String personCode) {
		this.personCode = personCode;
	}

	public Integer getPersonId() {
		return personId;
	}

	public String getPersonCode() {
		return personCode;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getFullName() {
		return firstName + ", " + lastName;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public List<String> getEmail() {
		return emails;
	}
	
	public void addEmail(String email) {
		this.emails.add(email);
	}
	
	public static Person getPerson(Integer personId) {
		
		List<Person> persons = DatabaseLoader.loadPersons();
		Person person = null;
		for (Person dude : persons) {
			if (dude.getPersonId().equals(personId)) {
				person = dude;
			}
		}
		return person;
	}
	
	public static Integer getPerson(String personCode) {
		
		int personId = -1;
		
		try {
			Connection conn = DatabaseInfo.openConnectSQL();
			String query0 = "select personId from Person where personCode = ?;";
			PreparedStatement ps0 = null;
			ps0 = conn.prepareStatement(query0);
			ps0.setString(1, personCode);
			ResultSet rs0 = ps0.executeQuery();
			if(rs0.next()) {
				personId = rs0.getInt("personId");
			}
			DatabaseInfo.closeConnection(conn, ps0, rs0);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return personId;
	}
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		String customer = this.getFullName();
		String customerCode = this.getPersonCode();

		int len = 0;
		if (this.getEmail() != null) {
			len = this.getEmail().size();
		}
		
		string.append(customer + "(" + customerCode + " : ");
		for (int j = 0; j < len; j++) {
			
			string.append(this.getEmail().get(j));
			if (j != len - 1) {
			
				string.append(", ");
			}
		}
	
		string.append(")");

		String customerAddress = this.getAddress().getFullAddress();
		
		string.append("\n" + customerAddress + "\n");
		return string.toString();
	}
	
	
	
}
