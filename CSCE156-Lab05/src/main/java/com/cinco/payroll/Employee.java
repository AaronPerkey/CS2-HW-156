package com.cinco.payroll;

public abstract class Employee implements Payable {
	
	private final String id;
	private final String firstName;
	private final String lastName;
	private final String title;
	
	public Employee(String id, String firstName, String lastName, String title) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getTitle() {
		return title;
	}
	public double getNetPay() {
		return getGrossPay() - getTaxes();
	}
	public double getGrossPay() {
		return 0.00;
	}
	public double getTaxes() {
		return 0.00;
	}
	public String getType() {
		return "Employee";
	}
	
	/**
	 * Returns a string representation (for printing) of the employee.
	 * For example, for a {@link SalaryEmployee} this method should return 
	 * <code>"Salary"</code>.
	 * 
	 * This method has been commented out to ensure the initial project
	 * compiles.  Uncomment it once you make this class abstract.
	 * 
	 * @return
	 */
	//public abstract String getType();
}