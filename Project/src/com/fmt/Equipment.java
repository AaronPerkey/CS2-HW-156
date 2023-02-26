package com.fmt;

public class Equipment extends Items{
	private final String model;

	public Equipment(String itemsQRCode, String typeOfSale, String name, String model) {
		super(itemsQRCode, typeOfSale, name);
		this.model = model;
	}

	public String getModel() {
		return model;
	}

}
