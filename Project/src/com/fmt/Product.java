package com.fmt;

public class Product extends Items{

	private final String unit;
	private final double unitPrice;
	private final double quantity;	

	public Product(String itemsQRCode, String typeOfSale, String name, String unit, double unitPrice) {
		super(itemsQRCode, typeOfSale, name);
		this.unit = unit;
		this.unitPrice = unitPrice;
		this.quantity = 0.00;
	}
	public Product(Product p, double quantity) {
		super(p.getItemsQRCode(), p.getTypeOfSale(), p.getName());
		this.unit = p.unit;
		this.unitPrice = p.unitPrice;
		this.quantity = quantity;
	}
	
	public String getUnit() {
		return unit;
	}
	public double getunitPrice() {
		return unitPrice;
	}
	
	public double getSubtotal() {
		double subtotal = unitPrice * quantity;
		return subtotal;
	}
	
	public double getTax() {
		double tax =  0.0345 * getSubtotal();
		return tax;
	}
	
	public double getTotal() {
		double total = getTax() + getSubtotal();
		return total;
	}
	
}