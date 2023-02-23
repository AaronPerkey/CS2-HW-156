package com.cinco.payroll;

import java.util.List;

/**
 * This is the main driver program that parses the payroll
 * data file and produces a payroll report.
 *
 */
public class PayrollReport {
	
	public static void main(String args[]) {

		//get the employees from the data file
		List<Employee> payroll = DataLoader.loadEmployees();

		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("%-8s %-20s %-10s %-30s %9s %9s %9s\n", 
				"ID", "Name", "Type", "Title", "Gross", "Taxes", "Net"));

		//for each employee
		for(Employee e : payroll) {
			//format their information
			sb.append(String.format("%-8s %-20s %-10s %-30s $%8.2f $%8.2f $%8.2f\n", 
					e.getId() + e.getLastName() +  "," + e.getFirstName() + e.getType() + e.getTitle() + e.getGrossPay() + e.getTaxes())); //TODO: replace these
		}
		
		System.out.println(sb);
	}
	
}
