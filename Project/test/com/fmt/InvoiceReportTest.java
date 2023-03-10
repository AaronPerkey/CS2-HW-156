package com.fmt;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;


public class InvoiceReportTest {

	
	public void SummeryReportTotal() {
		double arr[] = {133439.37, 11174.15, 9475.38, 0.00};
		double expected = 154088.90;
		double resut = Calculatons.getTotal(arr);
		Assertions.assertEquals(expected, result);

	}
	
	public void SummeryReportTax() {
		double arr[] = {736.54, 372.65, 625.38, 0.00};
		double expected = 1734.57;
		double resut = Calculatons.getTax(arr);
		Assertions.assertEquals(expected, result);
	}
	
	
	public static void main(String args[]) {
		SummeryReportTotal();
		SummeryReportTax();
		
	}
	
}
