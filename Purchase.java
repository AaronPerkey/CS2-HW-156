package com.fmt;

public class Purchase extends Equipment {
	
	private Double price;

	
	public Purchase(String code, String itemCode, String model, Double price) {
		super(code, itemCode, model);
		this.price = price;
	}
	
	public Purchase(String code) {
		super(code);
	}
	@Override
	public Double getPrice() {
		return price;
	}
	public Double getTax() {
		return 0.0;
	}
	
}
