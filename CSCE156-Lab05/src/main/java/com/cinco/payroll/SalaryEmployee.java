package com.cinco.payroll;

public class SalaryEmployee extends Employee {

	private final double annualSalary;
	
	public SalaryEmployee(String id, String firstName, String lastName, String title, double annualSalary) {
		super(id, firstName, lastName, title);
		this.annualSalary = annualSalary;
	}
	public double getNetPay() {
		return getGrossPay() - getTaxes() + 100;
	}
	public double getGrossPay() {
		return annualSalary / 52;
	}
	public double getTaxes() {
		return getGrossPay() * .2;
	}
	public String getType() {
		return "Salary";
	}
	
}