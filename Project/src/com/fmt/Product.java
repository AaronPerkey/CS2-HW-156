package com.fmt;
/**
 * 
 * Models the Sale and Structure of individual products.
 * 
 * @author aaron
 *
 */
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
		super(p.getItemsQRCode(), p.getTypeOfSale(), p.getItemName());
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
	
	@Override
	public double getCost() {
		double cost = unitPrice * quantity;
		return cost;
	}
	
	@Override
	public double getTaxRate() {
		double taxRate =  0.0345;
		return taxRate;		
	}
	

}