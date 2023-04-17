package com.fmt;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * 
 * Models a lease
 * 
 * @author Kyle Gann, Aaron Perkey
 *
 */

public class Lease extends Equipment{

	private Double fee;
	private LocalDate StartDate;
	private LocalDate endDate;
	
	public Lease(Integer itemId, String code, String name, String model, Double fee, LocalDate startDate, LocalDate endDate, String invoiceItem) {
		super(itemId, code, name, invoiceItem, model);
		this.fee = fee;
		StartDate = startDate;
		this.endDate = endDate;
	}

	public Lease(String code) {
		super(code);
	}
	
	public Double getFee() {
		int daysBetween = (int) (this.getStartDate().until(this.getEndDate(), ChronoUnit.DAYS));
		double subtotal = fee * (daysBetween/30.0);
		return subtotal;
	}

	@Override
	public Double getTaxRate() {
		double tax = 0.00;
		if(this.fee < 10000) {
			tax = 0.00;
		}else if((this.fee >= 10000) || (fee < 100000)) {
			tax = 500.00;
		}else if(this.fee >= 100000) {
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
	
	public int getTimeUsed() {
		
		int daysBetween = (int) (this.getStartDate().until(this.getEndDate(), ChronoUnit.DAYS));
		
		return daysBetween;
	}
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append(this.getItemCode() + "  ");
		String model = this.getModel();
		String product = this.getName();
		
		String type = "Lease";
		
		string.append("   (" + type + ") " + product + "-" + model);
		
		Double leaseCost = (this.getFee());
	
		string.append(String.format(
				"\n     %d days (%s -> %s) @ $%6f / 30 days\n"
						+ "                                                             $%10.2f \n",
						this.getTimeUsed(), this.getStartDate(),
				this.getEndDate(),
				this.getFee(), leaseCost));
		return string.toString();
	}
	
}
