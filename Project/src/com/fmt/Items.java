package com.fmt;

import java.time.LocalDate;

/**
 * 
 * Models items the company sells.
 * 
 * @author aaron, emma
 *
 */
public class Items {

	private String itemsQRCode;
	private String typeOfSale;
	private String itemName;
	private String invoiceCode;

	public Items(String itemsQRCode, String typeOfSale, String itemName) {
		super();
		this.itemsQRCode = itemsQRCode;
		this.typeOfSale = typeOfSale;
		this.itemName = itemName;
	}
	
	public Items(String invoiceCode, String itemsQRCode) {
		super();
		this.invoiceCode = invoiceCode;
		this.itemsQRCode = itemsQRCode;
	}

	public String getItemsQRCode() {
		return itemsQRCode;
	}

	public String getTypeOfSale() {
		return typeOfSale;
	}

	public String getItemName() {
		return itemName;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}	
}

