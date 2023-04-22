package com.fmt;

import java.time.LocalDate;

public class Equipment extends Item{

	
	private String model;
	public Equipment(String code, String name, String model) {
		super(code, name);
		this.model = model;
	}
	
	public Equipment(String code, String name, String model, String invoiceItem) {
		super(code, name, invoiceItem);
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
