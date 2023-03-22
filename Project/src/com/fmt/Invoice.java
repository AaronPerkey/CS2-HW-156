package com.fmt;

import java.time.LocalDate;
/**
 * 
 * Models a invoices store and customer identification
 * 
 * @author aaron
 *
 */
public class Invoice {

	private final String invoiceCode;
	private final Store store;
	private final Person customer;
	private final Person salesPerson;
	private final LocalDate invoiceDate;
	
	public Invoice(String invoiceCode, Store store, Person customer, Person salesPerson,
			LocalDate invoiceDate) {
		this.invoiceCode = invoiceCode;
		this.store = store;
		this.customer = customer;
		this.salesPerson = salesPerson;
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public Store getStore() {
		return store;
	}

	public Person getCustomer() {
		return customer;
	}

	public Person getSalesPerson() {
		return salesPerson;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}
	
	public String tostring() {
		
		return null;
	}
}
