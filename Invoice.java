package com.fmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	public static Integer getInvoice(String invoiceCode) {
		
		Integer invoiceId = -1;
		
		try {
			Connection conn = DatabaseInfo.openConnectSQL();
			String query0 = "select invoiceId from Invoice where invoiceCode = ?;";
			PreparedStatement ps0 = null;
			ps0 = conn.prepareStatement(query0);
			ps0.setString(1, invoiceCode);
			ResultSet rs0 = ps0.executeQuery();
			if(rs0.next()) {
				invoiceId = rs0.getInt("invoiceId");
			}
			DatabaseInfo.closeConnection(conn, ps0, rs0);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return invoiceId;
	}
	
	/**
	 * Complete the implementation of this method that will be used for sorting
	 * collections of names from invoices.
	 * 
	 * @param i
	 * @return
	 */
	public int compareToLastName(Invoice i) {
			return this.customer.getLastName().compareTo(i.getCustomer().getLastName());
	}
	
	/**
	 * Complete the implementation of this method that will be used for sorting
	 * collections of invoice totals from invoices.
	 * 
	 * @param i
	 * @return
	 */
	public int compareToInvoiceTotal(Invoice i) {
			return this.getInvoiceTotal().compareTo(i.getInvoiceTotal());
	}
	
	/**
	 * Complete the implementation of this method that will be used for sorting
	 * collections of Stores from invoices.
	 * 
	 * @param i
	 * @return
	 */
	public int compareToStore(Invoice i) {
		
		int comparedStore = this.storeCode.compareTo(i.getStoreCode());
		
		if( comparedStore == 0) {
				return compareToLastName(i);
		} else {
				return comparedStore;
		}
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
			
			sumTax += ((this.getItemList().get(j).getCost()) * this.getItemList().get(j).getTaxRate());

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

		for (int j = 0; j < numItems; j++) {
			// Service
			costs += (this.getItemList().get(j).getCost());
		}

		total = costs;
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