package com.cinco.payroll;

public class Staff extends HourlyEmployee {
	
	private static final double TAX_RATE = 0.15;

	public Staff(String id, String firstName, String lastName, String title, double hourlyPayRate, double hoursWorked) {
		super(id, lastName, firstName, title, hourlyPayRate, hoursWorked);
	}
	public double getTaxes() {
		return TAX_RATE * getGrossPay();
	}
	public String getType() {
		return "Staff";
	}
}