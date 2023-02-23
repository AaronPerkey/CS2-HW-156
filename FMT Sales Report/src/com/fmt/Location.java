package com.fmt;
/**
 * 
 * Models the location of a store or person
 * within the company's system.
 *
 * @author aaron, emma
 *
 */
public class Location {
	
	private final String address;
	private final String city;
	private final String state;
	private final String zipcode;
	private final String country;
	
	public Location(String address, String city, String state, String zipcode, String country) {
		super();
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public String getCountry() {
		return country;
	}
	
	
}
