package com.fmt;

import java.util.List;

/**
 * 
 * Models an invoice
 * 
 * @author Kyle Gann, Aaron Perkey
 *
 */

public class Invoice {

	private Integer invoiceId;
	private String invoiceCode;
	private List<Item> itemList;
	private String storeCode;
	private Person customer;
	private Person salesperson;
	private String date;

	public Invoice(String invoiceCode, Person customer, Person salesperson, String date) {
		super();
		this.invoiceCode = invoiceCode;
		this.customer = customer;
		this.salesperson = salesperson;
		this.date = date;
	}

	//for adding to SQL
	public Invoice(Integer invoiceId, String invoiceCode, String storeCode,  Person customer,
			Person salesperson, String date) {
		super();
		this.invoiceId = invoiceId;
		this.invoiceCode = invoiceCode;
		this.customer = customer;
		this.storeCode = storeCode;
		this.salesperson = salesperson;
		this.date = date;
	}
	// for adding item
	public Invoice(Invoice invoice1, List<Item> itemList) {
		this.invoiceId = invoice1.getInvoiceId();
		this.invoiceCode = invoice1.getInvoiceCode();
		this.customer = invoice1.getCustomer();
		this.storeCode = invoice1.getStoreCode();
		this.salesperson = invoice1.getSalesperson();
		this.date = invoice1.getDate();
		this.itemList = itemList;
	}
	
	public Integer getInvoiceId() {
		return invoiceId;
	}

	public List<Item> getItemList() {
		return itemList;
	}
	
	

	public String getStoreCode() {
		return storeCode;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public Person getCustomer() {
		return customer;
	}

	public Person getSalesperson() {
		return salesperson;
	}

	public String getDate() {
		return date;
	}

public Double getInvoiceTotal() {
	if (this.getItemList().size() > 0) {
		int numItems = this.getItemList().size();
		Double total = 0.0;
		Double serviceCost = 0.0;
		Double productCost = 0.0;
		Double purchaseCost = 0.0;
		Double leaseCost = 0.0;
		Double sumTax = 0.0;
		for (int j = 0; j < numItems; j++) {
			// Service
			serviceCost += (this.getItemList().get(j).getCost());
			sumTax += (serviceCost * this.getItemList().get(j).getTaxRate());

			leaseCost += this.getItemList().get(j).getFee();

			if (leaseCost > 10000 && leaseCost < 100000.0) {
				sumTax += 500.0;
			} else if (leaseCost > 100000) {
				sumTax += 1500.0;
			}
		}

		total += serviceCost + productCost + purchaseCost + leaseCost + sumTax;
		total = ((Math.round(total * 100.0)) / 100.0);
		return total;

	} else {
		// if they didn't buy anything the invoice is invalid
		return 0.0;
	}
}

public Double getCostWithNoTax() {
	if (this.getItemList().size() > 0) {
		int numItems = this.getItemList().size();
		Double total = 0.0;
		Double costs = 0.0;
		Double productCost = 0.0;
		Double purchaseCost = 0.0;
		Double leaseCost = 0.0;

		for (int j = 0; j < numItems; j++) {
			// Service
			costs += (this.getItemList().get(j).getCost());

			leaseCost += (this.getItemList().get(j).getFee());
		}

		total = costs + productCost + purchaseCost + leaseCost;
		total = ((Math.round(total * 100.0)) / 100.0);
		return total;

	} else {
		// if they didn't buy anything the invoice is invalid
		return 0.0;
	}
}

public Double getTax() {
	return this.getInvoiceTotal() - this.getCostWithNoTax();
}

public String toString () {
	StringBuilder string = new StringBuilder();
	//
	
	String invoiceCode = this.getInvoiceCode();
	string.append("\nInvoice  #" + invoiceCode);

		String storeCode = this.getStoreCode();
		
		string.append("\nStore    #" + storeCode);
		

		String date = this.getDate();
		
		string.append("\nDate     " + date);

		// customer
		string.append("\nCustomer:\n" + this.getCustomer().toString());

		// salesperson
		string.append("\nSalesperson:\n" + this.getSalesperson().toString());
		
		string.append("\r\n" + "Item                                                               Total\r\n"
				+ "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-                          -=-=-=-=-=-\n");
		int numItems = this.getItemList().size();
		for (int j = 0; j < numItems; j++) {
			string.append(this.getItemList().get(j).toString());
		}
		
		string.append(String.format(
				"                                                             -=-=-=-=-=-\n"
						+ "                                                    Subtotal $ %.2f\r\n"
						+ "                                                         Tax $   %.2f\r\n"
						+ "                                                 Grand Total $ %.2f\n",
				this.getCostWithNoTax(), this.getTax(), this.getInvoiceTotal()));

	string.append("\n");

	return string.toString();
}


}
