package com.fmt;
/**
 * 
 * Models an individual customer and their information.
 * 
 * @author aaron, emma
 *
 */
public class Person {
	
	private final String identification;
	private final FullName name;
	private final Location address;
	//TODO LOOK AT HOW TO DO EMAILS!!!
	private final String email;
	
	public Person(String identification, FullName name, Location address, String email) {
		super();
		this.identification = identification;
		this.name = name;
		this.address = address;
		this.email = email;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public FullName getName() {
		return name;
	}

	public void setName(FullName name) {
		this.name = name;
	}

	public Location getAddress() {
		return address;
	}

	public void setAddress(Location address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
