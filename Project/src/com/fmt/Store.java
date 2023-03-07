package com.fmt;
/**
 * 
 * Models an individual store.
 * 
 * @author aaron, emma
 *
 */
public class Store {
	
	private String storeIdentification;
	private String identification;
	private Location location;
	
	public Store(String storeIdentification, String identification, Location location) {
		super();
		this.storeIdentification = storeIdentification;
		this.identification = identification;
		this.location = location;
	}

	public String getStoreIdentification() {
		return storeIdentification;
	}

	public String getIdentification() {
		return identification;
	}

	public Location getLocation() {
		return location;
	}

	public void setStoreIdentification(String storeIdentification) {
		this.storeIdentification = storeIdentification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	

	
}
