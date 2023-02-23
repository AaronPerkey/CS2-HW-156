package com.fmt;
/**
 * 
 * Models the information of the
 * each item in the company's system.
 * 
 * @author aaron, emma
 *
 */
public class Items {

	private final String itemQRCode;
	private final String typeOfSale;
	private final String item;
	private final String nameOfItem;
	private final double quantity;
	
	public Items(String itemQRCode, String typeOfSale, String item, String nameOfItem, double quantity) {
		super();
		this.itemQRCode = itemQRCode;
		this.typeOfSale = typeOfSale;
		this.item = item;
		this.nameOfItem = nameOfItem;
		this.quantity = quantity;
	}

	public String getItemQRCode() {
		return itemQRCode;
	}

	public void setItemQRCode(String itemQRCode) {
		this.itemQRCode = itemQRCode;
	}

	public String getTypeOfSale() {
		return typeOfSale;
	}
	
	public void setTypeOfSale(String typeOfSale) {
		this.typeOfSale = typeOfSale;
	}
	
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getNameOfItem() {
		return nameOfItem;
	}

	public void setNameOfItem(String nameOfItem) {
		this.nameOfItem = nameOfItem;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	
}
