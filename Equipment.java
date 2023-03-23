package com.fmt;

import java.time.LocalDate;

public class Equipment extends Item{

	
	private String model;

	public Equipment(String code, String itemCode, String model) {
		super(code, itemCode);
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
	public double getFee() {
		return -1.0;
	}
	
	public Double getTaxRate() {
		return -1.0;
	}
	public Double getCost() {
		return -1.0;
	}
	
	public Double getPrice() {
		return -1.0;
	}
	public LocalDate getStartDate() {
		return null;
	}
	public LocalDate getEndDate() {
		return null;
	}

	
}
