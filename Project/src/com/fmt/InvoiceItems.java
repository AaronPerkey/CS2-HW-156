package com.fmt;

import java.time.LocalDate;

/**
 * 
 * Models a Invoices products purchased
 * 
 * @author aaron, emma
 *
 */
public class InvoiceItems {
	
	private final String invoiceCode;
	private final String itemsQRCode;
	private final String typeOfSale;
	private final double cost;
	private final double quantity;
	private final LocalDate startDate;
	private final LocalDate endDate;
	
	public InvoiceItems(String invoiceCode, String itemsQRCode, String typeOfSale, double cost, double quantity,
			LocalDate startDate, LocalDate endDate) {
		super();
		this.invoiceCode = invoiceCode;
		this.itemsQRCode = itemsQRCode;
		this.typeOfSale = typeOfSale;
		this.cost = cost;
		this.quantity = quantity;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public String getItemsQRCode() {
		return itemsQRCode;
	}

	public String getTypeOfSale() {
		return typeOfSale;
	}

	public double getCost() {
		return cost;
	}

	public double getQuantity() {
		return quantity;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}
	
	

}