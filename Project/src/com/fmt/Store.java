package com.fmt;

import java.util.List;

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
	private Person manager;
	private List<Invoice> invoices;
	
	public Store(String storeIdentification, String identification, Location location, Person manager) {
		super();
		this.storeIdentification = storeIdentification;
		this.identification = identification;
		this.location = location;
	}
	
	public Store(String storeIdentification, String identification, Location location, Person manager, List<Invoice> invoices) {
		super();
		this.storeIdentification = storeIdentification;
		this.identification = identification;
		this.location = location;
		this.invoices = invoices;
	}

	public Person getManager() {
		return manager;
	}

	public List<Invoice> getInvoices() {
		return invoices;
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
	
	public String tostring() {
		String storeSalesSummeryReport = String.format("", null)
		return null;
	}
	
}
