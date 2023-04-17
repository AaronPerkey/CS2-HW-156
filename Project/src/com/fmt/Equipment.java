package com.fmt;

import java.time.LocalDate;

/**
 * 
 * Models equipment
 * 
 * @author Kyle Gann, Aaron Perkey
 *
 */

public class Equipment extends Item{

	private String model;
	public Equipment(Integer itemId, String itemCode, String name, String model) {
		super(itemId, itemCode, name);
		this.model = model;
	}
	
	public Equipment(Integer itemId, String itemCode, String name, String invoiceCode, String model) {
		super(itemId, itemCode, name, invoiceCode);
		this.model = model;
	}
	
	public Equipment(String code) {
		super(code);
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
