package com.fmt;

import java.util.List;

/**
 * 
 * Models a individual person.
 * 
 * @author aaron, emma
 *
 */
public class Person {
	
	private final String identification;
	private final FullName name;
	private final Location address;
	private final List<Email> email;
	
	public Person(String identification, FullName name, Location address, List<Email> email) {
		super();
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

	public List<Email> getEmail() {
		return email;
	}

	

}
