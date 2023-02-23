package com.cinco.payroll;

public class Supplier implements Payable {
	
	private final String companyName;
	private final double ammountDue;
	
	public Supplier(String companyName, double ammountDue) {
		super();
		this.companyName = companyName;
		this.ammountDue = ammountDue;
	}

	public double getNetPay() {
		return ammountDue;
	}

}
