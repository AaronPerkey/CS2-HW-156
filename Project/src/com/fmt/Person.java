package com.fmt;


/**
 * 
 * Models a individual person.
 * 
 * @author aaron, emma
 *
 */
public class Person {
	
	private String identification;
	private FullName name;
	private Location address;
	private String email1;
	private String email2;
	private String email3;
	private String email4;
	
	public Person(String identification, FullName name, Location address, String email1, String email2, String email3,
			String email4) {
		super();
		this.identification = identification;
		this.name = name;
		this.address = address;
		this.email1 = email1;
		this.email2 = email2;
		this.email3 = email3;
		this.email4 = email4;
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

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getEmail3() {
		return email3;
	}

	public void setEmail3(String email3) {
		this.email3 = email3;
	}

	public String getEmail4() {
		return email4;
	}

	public void setEmail4(String email4) {
		this.email4 = email4;
	}	
	
	
	
}
