package com.fmt;
/**
 * 
 * Models the name of both customers
 * and managers in the company's system.
 * 
 * @author aaron, emma
 *
 */
public class FullName {

	private final String lastName;
	private final String firstName;
	
	public FullName(String lastName, String firstName) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	
}
