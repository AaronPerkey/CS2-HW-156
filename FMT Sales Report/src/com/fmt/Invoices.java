package com.fmt;

import java.time.LocalDate;

/**
 * 
 * Models invoice with person and store.
 * 
 * @author aaron,emma
 *
 */
public class Invoices {
	
	private final String invoiceNumber;
	private final Store store;
	private final Person person1;
	private final Person person2;
	private final LocalDate dateOfPurchace;
	
	public Invoices(String invoiceNumber, Store store, Person person1, Person person2, LocalDate dateOfPurchace) {
		super();
		this.invoiceNumber = invoiceNumber;
		this.store = store;
		this.person1 = person1;
		this.person2 = person2;
		//TODO LOOK AT LOCALDATE
		this.dateOfPurchace = LocalDate.parse(dateOfPurchace);
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public Store getStore() {
		return store;
	}

	public Person getPerson1() {
		return person1;
	}

	public Person getPerson2() {
		return person2;
	}

	public LocalDate getDateOfPurchace() {
		return dateOfPurchace;
	}
	
	
}
