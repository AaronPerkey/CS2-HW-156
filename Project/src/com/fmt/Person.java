package com.fmt;

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
	
	// for adding a person to sql
	public Person(Integer personId, String personCode, String firstName, String lastName, Address address,
			List<String> emails) {
		this.personId = personId;
		this.personCode = personCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
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
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		String customer = this.getFullName();
		String customerCode = this.getPersonCode();

		int len = this.getEmail().size();
		
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
