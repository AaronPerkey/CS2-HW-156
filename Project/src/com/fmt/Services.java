package com.fmt;

public class Services extends Items{
	
	private final double hourlyRate;
	private final double quantity;
	
	public Services(String itemsQRCode, String typeOfSale, String name, double hourlyRate, double quantity) {
		super(itemsQRCode, typeOfSale, name);
		this.hourlyRate = hourlyRate;
		this.quantity = quantity;
	}

	public Services(Services s, double quantity) {
		super(s.getItemsQRCode(), s.getTypeOfSale(), s.getName());
		this.hourlyRate = s.hourlyRate;
		this.quantity = quantity;
	}
	
	public double getHourlyRate() {
		return hourlyRate;
	}
	public double getSubtotal() {
		double subtotal = hourlyRate * quantity;
		return subtotal;
	}
	public double getTax() {
		double tax = getSubtotal() * 0.0345;
		return tax;
	}
	public double getTotal() {
		double Total = getSubtotal() + getTax();
		return Total;
	}
}