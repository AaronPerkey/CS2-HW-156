package com.fmt;

/**
 * models a purchase
 * 
 * @author Kyle Gann, Aaron Perkey
 *
 */

public class Purchase extends Equipment {
	
	private Double price;

	public Purchase(Integer itemId, String itemCode, String name, String model, Double price, String invoiceItem) {
		super(itemId, itemCode, name, invoiceItem, model);
		this.price = price;
	}
	
	public Purchase(String code) {
		super(code);
	}
	
	@Override
	public Double getUnitPrice() {
		return price;
	}
	public Double getCost() {
		return price;
	}
	
	
	public Double getTax() {
		return 0.0;
	}
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append(this.getItemCode() + "  ");
		String model = this.getModel();
		String product = this.getName();
		
		String type = "Purchase";
		
		string.append("   (" + type + ") " + product + "-" + model);
		Double purchaseCost = (this.getUnitPrice());
		
		string.append(String.format(
				"\n                                                             $%10f \n",
				purchaseCost));
		return string.toString();
	}
	
}