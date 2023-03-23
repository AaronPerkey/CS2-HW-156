package com.fmt;

public class Product extends Item{
	
	private String unit;
	private Double price;
	private int quantity;
	


	public Product(String code, String itemCode, String unit, Double price) {
		super(code, itemCode);
		this.unit = unit;
		this.price = price;
	}
	public Product(Product p, int quanity) {
		super(p.getCode(), p.getItemCode());
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
	public Double getTax() {
		return price * 0.075;
	}
	
	@Override
	public int getQuantity() {
		return quantity;
	}
	
	@Override
	public Double getCost() {
		double cost = price * quantity;
		return cost;
	}
	
	@Override
	public Double getTaxRate() {
		double taxRate =  0.0345;
		return taxRate;		
	}
	
	
}
