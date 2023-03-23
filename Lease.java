package com.fmt;

import java.time.LocalDate;
import java.time.Period;

public class Lease extends Equipment{

	private Double fee;
	private LocalDate StartDate;
	private LocalDate endDate;
	
	public Lease(String code, String itemCode, String model, Double fee, LocalDate startDate,
			LocalDate endDate) {
		super(code, itemCode, model);
		this.fee = fee;
		StartDate = startDate;
		this.endDate = endDate;
	}
	
	
	public Lease(String code) {
		super(code);
	}
	
	@Override
	public double getFee() {
		Period period = Period.between(getStartDate(), getEndDate());
		int days = period.getDays();
		double subtotal = fee * (days/30);
		return subtotal;
	}

	@Override
	public Double getTaxRate() {
		double tax = 0.00;
		if(fee < 10000) {
			tax = 0.00;
		}else if((fee >= 10000) || (fee < 100000)) {
			tax = 500.00;
		}else if(fee >= 100000) {
			tax = 1500.00;
		}
		return tax;
	}
	
	public LocalDate getStartDate() {
		return StartDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	
	
	
	
}
