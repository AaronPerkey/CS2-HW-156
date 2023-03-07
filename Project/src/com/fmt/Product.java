package com.fmt;

public class Product extends Items{

	private final String unit;
	private final double unitPrice;
	
	public Product(String itemsQRCode, String typeOfSale, String name, String unit, double unitPrice) {
		super(itemsQRCode, typeOfSale, name);
		this.unit = unit;
		this.unitPrice = unitPrice;
	}
	public String getUnit() {
		return unit;
	}
	public double getUnitPrice() {
		return unitPrice;
	}

}
