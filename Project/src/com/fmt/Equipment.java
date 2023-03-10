package com.fmt;

import java.time.LocalDate;
import java.time.Period;

public class Equipment extends Items{
	
	private final String model;

	public Equipment(String itemsQRCode, String typeOfSale, String name, String model) {
		super(itemsQRCode, typeOfSale, name);
		this.model = model;
	}

	public String getModel() {
		return model;
	}
	
	public double getSales() {
		double subtotal = 0.00;
		double tax = 0.00;
		double total = 0.00;
		if(getTypeOfSale().equals("P")) {
			total =  getCost();
		}else if (getTypeOfSale().equals("L")) {
			Period period = Period.between(getStartDate(), getEndDate());
			int days = period.getDays();
			subtotal = getCost() * (days/30);
		}if((getCost() <= 10000) || (getCost() < 100000)) {
			tax = 500.00;
		}else if(getCost() > 100000) {
			tax = 15000;
		}
		total = tax + subtotal;
		return total;
	}

}
