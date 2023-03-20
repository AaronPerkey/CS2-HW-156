package com.fmt;

import java.time.LocalDate;
/**
 * 
 * Models a invoices store and customer identification
 * 
 * @author aaron, emma
 *
 */
public class Invoice {

	private final String invoiceCode;
	private final String storeIdentification;
	private final String customerID;
	private final String salesPersonID;
	private final LocalDate invoiceDate;
	
	public Invoice(String invoiceCode, String storeIdentification, String customerID, String salesPersonID,
			LocalDate invoiceDate) {
		super();
		this.invoiceCode = invoiceCode;
		this.storeIdentification = storeIdentification;
		this.customerID = customerID;
		this.salesPersonID = salesPersonID;
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public String getStoreIdentification() {
		return storeIdentification;
	}

	public String getCustomerID() {
		return customerID;
	}

	public String getSalesPersonID() {
		return salesPersonID;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}
}
