package com.cinco.payroll;

public class Temporary extends HourlyEmployee {
	
	public Temporary(String id, String firstName, String lastName, String title, double hourlyPayRate, double hoursWorked) {
		super(id, lastName, firstName, title, hourlyPayRate, hoursWorked);
		// TODO Auto-generated constructor stub
	}
	public double getTaxes() {
		return 0.00;
	}
	public String getType() {
		return "Temporary";
	}
}