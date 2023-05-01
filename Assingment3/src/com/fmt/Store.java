package com.fmt;

import java.util.List;

/**
 * models a store.
 * 
 * @author Kyle Gann, Aaron Perkey
 *
 */

public class Store {

	private String storeCode;
	private Person managerCode;
	private Address address;
	private List<Invoice> invoiceList;

	public Store(String storeCode, Person managerCode, Address address) {
		super();
		this.storeCode = storeCode;
		this.managerCode = managerCode;
		this.address = address;
	}

	public Store(Store store, List<Invoice> invoiceList) {
		this.storeCode = store.getStoreCode();
		this.managerCode = store.getManagerCode();
		this.address = store.getAddress();
		this.invoiceList = invoiceList;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public Person getManagerCode() {
		return managerCode;
	}


	public Address getAddress() {
		return address;
	}
	

	public List<Invoice> getInvoiceList() {
		return invoiceList;
	}
	
	public String toString () {
		// Writing the store summary report
		StringBuilder string = new StringBuilder();

		Double totalGrandTotal = 0.0;


			String store = this.getStoreCode();
			String manager = this.getManagerCode().getFullName();
			Double grandTotal = this.getGrandTotal();
			
			int sales = this.getInvoiceList().size();

			totalGrandTotal += grandTotal;
			
			string.append(String.format("%s %20s %15d         $%11.2f \n", store, manager, sales, grandTotal));

			return string.toString();
	}
	
	public Double getGrandTotal() {
		Double grandTotal = 0.0;
		
		for (int j = 0; j < this.getInvoiceList().size(); j++) {
			grandTotal += this.getInvoiceList().get(j).getInvoiceTotal();
		}
		return grandTotal;
	}
	
}
