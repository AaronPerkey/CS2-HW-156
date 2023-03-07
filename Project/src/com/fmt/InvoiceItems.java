package com.fmt;
/**
 * 
 * Models a Invoices products purchased
 * 
 * @author aaron, emma
 *
 */
public class InvoiceItems extends Invoice {
	
	private final String itemsQRCode;
	private final String cost;
	private final String rent;
	private final String quantity;
	private final String hoursBilled;
	
	public InvoiceItems(String invoiceCode, String itemsQRCode, String cost, String rent, String quantity,
			String hoursBilled) {
		super(invoiceCode);
		this.itemsQRCode = itemsQRCode;
		this.cost = cost;
		this.rent = rent;
		this.quantity = quantity;
		this.hoursBilled = hoursBilled;
	}
	
	public String getItemsQRCode() {
		return itemsQRCode;
	}
	public String getCost() {
		return cost;
	}
	public String getRent() {
		return rent;
	}
	public String getQuantity() {
		return quantity;
	}
	public String getHoursBilled() {
		return hoursBilled;
	}
	
	

}
