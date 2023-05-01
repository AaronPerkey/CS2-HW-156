package com.fmt;

/**
 * A class to model an address
 * 
 * @author Kyle Gann, Aaron Perkey
 *
 */

public class Address {

	private String street;
	private String city;
	private String state;
	private String zipCode;
	private String country;
	
	public Address(String street, String city, String state, String zipCode, String country) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getCountry() {
		return country;
	}
	
	public String getFullAddress() {
		return this.street + ", \n" + this.city + ", " + this.state + ", " + this.zipCode + ", " + this.country;
	}
	
}
