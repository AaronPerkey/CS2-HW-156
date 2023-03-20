package com.fmt;
/**
 * 
 * Models the Structure of the overall sale of Equipment.
 * 
 * @author aaron, emma
 *
 */

public class Equipment extends Items{
	
	private String model;

	public Equipment(String itemsQRCode, String typeOfSale, String itemName, String model) {
		super(itemsQRCode, typeOfSale, itemName);
		this.model = model;
	}

	public String getModel() {
		return model;
	}
}
