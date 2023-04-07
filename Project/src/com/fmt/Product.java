package com.fmt;

public class Product extends Item{
	
	private String unit;
	private Double price;
	private int quantity;

	
	public Product(String itemCode, String name, String unit, Double price) {
		super(itemCode, name);
		this.unit = unit;
		this.price = price;
	}

	public Product(Product p, int quanity, String invoiceCode) {
		super(p.getItemCode(), p.getName(), invoiceCode);
		this.unit = p.getUnit();
		this.price = p.getPrice();
		this.quantity = quanity;
	}
	
	public Product(String code) {
		super(code);
	}

	@Override
	public String getUnit() {
		return unit;
	}
	@Override
	public Double getPrice() {
		return price;
	}
	
	@Override
	public int getQuantity() {
		return quantity;
	}
	
	public Double getTax() {
		return price * 0.075;
	}
	
	@Override
	public Double getCost() {
		double cost = this.price * this.quantity;
		return cost;
	}
	
	@Override
	public Double getTaxRate() {
		double taxRate =  0.0345;
		return taxRate;		
	}
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append(this.getItemCode() + "  ");
		
		String product = this.getName();
		
		string.append("   (Product)  " + product);
		Double productCost = (this.getPrice()
				* this.getQuantity());
		
		
		string.append(String.format(
				"\n                                                             $%10.2f \n",
				productCost));
		
		return string.toString();
	}
	
}
