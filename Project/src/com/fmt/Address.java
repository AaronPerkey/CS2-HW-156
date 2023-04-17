package com.fmt;

/**
 * 
 * A class to model an address
 * 
 * @author Kyle Gann, Aaron Perkey
 *
 */

public class Address {

	private Integer addressId;
	private String street;
	private String city;
	private String zipCode;
	private String state;
	private String country;
	
	public Address(String street, String city, String zipCode, String state, String country) {
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
		this.state = state;
		this.country = country;
	}

	public Address(Integer addressId, String street, String city, String zipCode, String state, 
			String country) {
		this.addressId = addressId;
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
		this.state = state;
		this.country = country;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getState() {
		return state;
	}

	public String getCountry() {
		return country;
	}

	public String getFullAddress() {
		return this.street + ", \n" + this.city + ", " + this.state + ", " + this.zipCode + ", " + this.country;
	}
	
}
