package com.fmt;

import java.util.List;

/**
 * 
 * Models a individual person.
 * 
 * @author aaron
 *
 */
public class Person {
	
	private final String identification;
	private final FullName name;
	private final Location address;
	private final List<String> email;
	
	public Person(String identification, FullName name, Location address, List<String> email) {
		this.identification = identification;
		this.name = name;
		this.address = address;
		this.email = email;
	}

	public String getIdentification() {
		return identification;
	}

	public FullName getName() {
		return name;
	}

	public Location getAddress() {
		return address;
	}

	public List<String> getEmail() {
		return email;
	}

	

}
