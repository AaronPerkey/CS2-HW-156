package com.fmt;

import java.io.FileWriter;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

/**
 * 
 * Reports rummry report, store report, and individual invoices
 * 
 * @author Kyle Gann, Aaron Perkey
 * 
 */

public class InvoiceReport {

	public static void outputSummary(List<Invoice> invoice, List<Store> stores, String file) throws IOException {
		FileWriter writer = new FileWriter(file);

		if (invoice.size() < 1 || stores.size() < 1) {
			System.out.print("\r\n" + "+----------------------------------------------------------------+\r\n"
					+ "| Database Cleared                                               |\r\n"
					+ "+----------------------------------------------------------------+\r\n"
					+ "Store               Manager               # Sales    Grand Total    \n");
		}

		if (invoice == null || stores == null) {
			System.out.print("\r\n" + "+----------------------------------------------------------------+\r\n"
					+ "| Invalid Data                                                   |\r\n"
					+ "+----------------------------------------------------------------+\r\n"
					+ "Store               Manager               # Sales    Grand Total    \n");
		}

		// Writing the summary report
		writer.write("+----------------------------------------------------------------------------------------+\r\n"
				+ "| Summary Report - By Total                                                              |\r\n"
				+ "+----------------------------------------------------------------------------------------+\r\n"
				+ "Invoice #  Store             Customer                Num Items          Tax       Total\n");
		System.out
				.print("+----------------------------------------------------------------------------------------+\r\n"
						+ "| Summary Report - By Total                                                              |\r\n"
						+ "+----------------------------------------------------------------------------------------+\r\n"
						+ "Invoice #  Store             Customer                Num Items          Tax       Total\n");
		Double finalTax = 0.0;
		int finalNumItem = 0;
		Double finalSales = 0.0;

		for (int i = 0; i < invoice.size(); i++) {
			Double total = 0.0;
			Double sumTax = 0.0;

			String invoiceCode = invoice.get(i).getInvoiceCode();
			String store = invoice.get(i).getStoreCode();
			String customer = invoice.get(i).getCustomer().getFullName();

			int numItems = invoice.get(i).getItemList().size();

			total += invoice.get(i).getInvoiceTotal();
			sumTax += invoice.get(i).getTax();

			finalTax += sumTax;
			finalNumItem += numItems;
			finalSales += total;
			writer.write(String.format("%s %10s %19s %16d        $%13.2f $%11.2f \n", invoiceCode, store, customer,
					numItems, sumTax, total));
			System.out.print(String.format("%s %10s %19s %16d        $%13.2f $%11.2f \n", invoiceCode, store, customer,
					numItems, sumTax, total));
		}
		writer.write(String.format(
				"+----------------------------------------------------------------------------------------+\r\n"
						+ "                                                     %d       $   %9.2f  $ %10.2f",
				finalNumItem, finalTax, finalSales));
		System.out.print((String.format(
				"+----------------------------------------------------------------------------------------+\r\n"
						+ "                                                     %d       $   %9.2f  $ %10.2f",
				finalNumItem, finalTax, finalSales)));
		// Writing the store summary report
		writer.write("\r\n" + "+----------------------------------------------------------------+\r\n"
				+ "| Store Sales Summary Report                                     |\r\n"
				+ "+----------------------------------------------------------------+\r\n"
				+ "Store               Manager               # Sales    Grand Total    \n");
		System.out.print("\r\n" + "+----------------------------------------------------------------+\r\n"
				+ "| Store Sales Summary Report                                     |\r\n"
				+ "+----------------------------------------------------------------+\r\n"
				+ "Store               Manager               # Sales    Grand Total    \n");

		int totalSales = 0;
		Double totalGrandTotal = 0.0;

		for (int i = 0; i < stores.size(); i++) {
			Double grandTotal = stores.get(i).getGrandTotal();
			int sales = stores.get(i).getInvoiceList().size();
			totalSales += sales;
			totalGrandTotal += grandTotal;

			writer.write(stores.get(i).toString());
			System.out.print(stores.get(i).toString());

		}
		writer.write(String.format("+----------------------------------------------------------------+\r\n"
				+ "                            %15d         $ %11.2f\n", totalSales, totalGrandTotal));
		System.out.print(String.format("+----------------------------------------------------------------+\r\n"
				+ "                            %15d         $ %11.2f\n", totalSales, totalGrandTotal));

		// write invoice info
		for (int i = 0; i < invoice.size(); i++) {
			System.out.print(invoice.get(i).toString());
			writer.write(invoice.get(i).toString());
		}

		writer.close();
	}

	public static void main(String[] args) {

		Configurator.initialize(new DefaultConfiguration());
		Configurator.setRootLevel(Level.INFO);

		List<Invoice> invoices = DatabaseLoader.loadInvoice();
		List<Store> stores = DatabaseLoader.loadStore();

		try {
			outputSummary(invoices, stores, "data/output.txt");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}