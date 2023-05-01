package com.fmt;

import java.time.LocalDate;

/**
 * Models equipment.
 * 
 * @author Kyle Gann, Aaron Perkey
 *
 */

public class Equipment extends Item{

	private String model;
	
	public Equipment(String itemCode, String name, String model) {
		super(itemCode, name);
		this.model = model;
	}
	
	public Equipment(String itemCode, String name, String model, String invoiceCode) {
		super(itemCode, name, invoiceCode);
		this.model = model;
	}

	@Override
	public String getModel() {
		return model;
	}
	
	@Override
	public Double getFee() {
		return 0.0;
	}
	
	public Double getTaxRate() {
		return 0.0;
	}
	
	public Double getCost() {
		return 0.0;
	}
	
	public Double getUnitPrice() {
		return 0.0;
	}
	
	public LocalDate getStartDate() {
		return null;
	}
	
	public LocalDate getEndDate() {
		return null;
	}

}
