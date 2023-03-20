package com.fmt;
/**
 * Models the selling of Equipment.
 * 
 * 
 * @author aaron, emma
 *
 */
public class Purchase extends Equipment{

	private final double unitPrice;



	public Purchase(String itemsQRCode, String typeOfSale, String itemName, String model, double unitPrice) {
		super(itemsQRCode, typeOfSale, itemName, model);
		this.unitPrice = unitPrice;
	}


	public double getUnitPrice() {
		return unitPrice;
	}
	
	public double getSubtotal() {
		return unitPrice;
	}
	
	public double getTax() {
		return unitPrice;
	}
	
	public double getTotal() {
		return unitPrice;
	}
}
