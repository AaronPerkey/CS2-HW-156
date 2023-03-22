package com.fmt;
/**
 * Models the selling of Equipment.
 * 
 * 
 * @author aaron
 *
 */
public class Purchase extends Equipment{

	private final double unitPrice;



	public Purchase(String itemsQRCode, String typeOfSale, String itemName, String model, double unitPrice) {
		super(itemsQRCode, typeOfSale, itemName, model);
		this.unitPrice = unitPrice;
	}


	@Override
	public double getCost() {
		return unitPrice;
	}
	
	public double getSubtotal() {
		return unitPrice;
	}
	
	@Override
	public double getTaxRate() {
		return 0;
	}
}
