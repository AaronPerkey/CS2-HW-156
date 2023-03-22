package com.fmt;

/**
 * Models the sold services in the company.
 * @author aaron
 *
 */

public class Services extends Items{
	
	private final double hourlyRate;
	private double hoursWorked;
	
	public Services(String itemsQRCode, String typeOfSale, String itemName, double hourlyRate) {
		super(itemsQRCode, typeOfSale, itemName);
		this.hourlyRate = hourlyRate;
	}

	public Services(Services s, double hoursWorked) {
		super(s.getItemsQRCode(), s.getTypeOfSale(), s.getItemName());
		this.hourlyRate = s.hourlyRate;
		this.hoursWorked = hoursWorked;
	}
	
	public double getHourlyRate() {
		return hourlyRate;
	}
	
	@Override
	public double getCost() {
		double subtotal = hourlyRate * hoursWorked;
		return subtotal;
	}
	
	@Override
	public double getTaxRate() {
		double tax = 0.0345;
		return tax;
	}
}