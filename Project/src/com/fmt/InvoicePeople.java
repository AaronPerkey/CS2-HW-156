package com.fmt;

import java.time.LocalDate;
/**
 * 
 * Models a invoices store and customer identification
 * 
 * @author aaron, emma
 *
 */
public class InvoicePeople extends Invoice{

	private final String storeIdentification;
	private final String customerID;
	private final String salesPersonID;
	private final LocalDate invoiceDate;
	
	public InvoicePeople(String invoiceCode, String storeIdentification, String customerID, String salesPersonID,
			LocalDate invoiceDate) {
		super(invoiceCode);
		this.storeIdentification = storeIdentification;
		this.customerID = customerID;
		this.salesPersonID = salesPersonID;
		this.invoiceDate = invoiceDate;
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
