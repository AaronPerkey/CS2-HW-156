package com.cinco.payroll;

public abstract class HourlyEmployee extends Employee {

	private final double hourlyPayRate;
	private final double hoursWorked;
	
	public HourlyEmployee(String id, String firstName, String lastName, String title, double hourlyPayRate, double hoursWorked) {
		super(id, firstName, lastName, title);
		this.hourlyPayRate = hourlyPayRate;
		this.hoursWorked = hoursWorked;
	}
	public double getHourlyPayRate() {
		return hourlyPayRate;
	}
	public double getHoursWorked() {
		return hoursWorked;
	}
	public double getGrossPay() {
		return hourlyPayRate * hoursWorked;
	}
}