package com.fmt;

public class Services extends Items{
	private final double hourlyRate;

	public Services(String itemsQRCode, String typeOfSale, String name, double hourlyRate) {
		super(itemsQRCode, typeOfSale, name);
		this.hourlyRate = hourlyRate;
	}

	public double getHourlyRate() {
		return hourlyRate;
	}
	
	
}
