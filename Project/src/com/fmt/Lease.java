package com.fmt;

/**
 * Models the renting of different equipment.
 * 
 * author: aaron
 */

import java.time.LocalDate;
import java.time.Period;

public class Lease extends Equipment{
	
	private final double cost;
	private final LocalDate startDate;
	private final LocalDate endDate;
	
		public Lease(String itemsQRCode, String typeOfSale, String itemName, String model,
				double cost, LocalDate startDate, LocalDate endDate) {
		super(itemsQRCode, typeOfSale, itemName, model);
		this.cost = cost;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	@Override
	public double getCost() {
		Period period = Period.between(getStartDate(), getEndDate());
		int days = period.getDays();
		double subtotal = cost * (days/30);
		return subtotal;
	}

	@Override
	public double getTaxRate() {
		double tax = 0.00;
		if(cost < 10000) {
			tax = 0.00;
		}else if((cost >= 10000) || (cost < 100000)) {
			tax = 500.00;
		}else if(cost >= 100000) {
			tax = 1500.00;
		}
		return tax;
	}
}
