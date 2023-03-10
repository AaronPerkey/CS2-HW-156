package com.fmt;

import java.time.LocalDate;

/**
 * 
 * Models items company sells.
 * 
 * @author aaron, emma
 *
 */
public class Items {

	private final String itemsQRCode;
	private final String typeOfSale;
	private final String name;

	public Items(String itemsQRCode, String typeOfSale, String name) {
		super();
		this.itemsQRCode = itemsQRCode;
		this.typeOfSale = typeOfSale;
		this.name = name;
	}

	public String getItemsQRCode() {
		return itemsQRCode;
	}

	public String getTypeOfSale() {
		return typeOfSale;
	}

	public String getName() {
		return name;
	}

	
	
}

