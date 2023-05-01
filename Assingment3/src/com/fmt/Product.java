package com.fmt;

/**
 * 
 * Models a product
 * 
 * @author Kyle Gann, Aaron Perkey
 *
 */

public class Product extends Item{
	
	private String unit;
	private Double unitPrice;
	private int quantity;

	public Product(String itemCode, String name, String unit, Double unitPrice) {
		super(itemCode, name);
		this.unit = unit;
		this.unitPrice = unitPrice;
	}

	public Product(Product p, int quanity, String invoiceCode) {
		super(p.getItemCode(), p.getName(), invoiceCode);
		this.unit = p.getUnit();
		this.unitPrice = p.getUnitPrice();
		this.quantity = quanity;
	}
	
	@Override
	public String getUnit() {
		return unit;
	}
	
	@Override
	public Double getUnitPrice() {
		return unitPrice;
	}
	
	public Double getTaxRate() {
		return unitPrice * 0.075;
	}
	
	@Override
	public int getQuantity() {
		return quantity;
	}
	
	@Override
	public Double getCost() {
		double cost = this.getUnitPrice() * this.getQuantity();
		return cost;
	}
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append(this.getItemCode() + "  ");
		
		String product = this.getName();
		
		string.append("   (Product)  " + product);
		Double productCost = this.getCost();
		
		string.append(String.format(
				"\n                       Quantity: %d                         $%10.2f \n",
				this.getQuantity(),productCost));
		
		return string.toString();
	}
	
}