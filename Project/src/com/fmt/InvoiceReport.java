package com.fmt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

import unl.cse.library.Library;

//for(Library i, Integer> entry : lib.getInvoicePeopleMap().enteryset()) {
//
//System.out.println(i[0]);
//}
public class InvoiceReport {
	
	private static Library lib;
	
	public InvoiceReport(com.fmt.Library lib) {
		super();
		this.lib = lib;
	}

	public static List<InvoicePeople> getSummeryReport() {
		
		for (Map.Entry<String, InvoicePeople> entry : lib.getInvoicePeopleMap().entrySet()) {
			InvoicePeople value = entry.getValue();
			System.out.println(value);
		}

		return null;
	}

	public static void main(String[] args) {
		Map<String, Items> itemsMap = new HashMap<>();
		Map<String, Store> storeMap = new HashMap<>();
		Map<String, Person> personMap = new HashMap<>();
		Map<String, InvoiceItems> invoiceItemsMap = new HashMap<>();
		Map<String, InvoicePeople> invoicePeopleMap = new HashMap<>();
		for(Items it : DataConverter.loadItemsData()) {
		itemsMap.put(it.getItemsQRCode(), it);
	}
		for(Store it : DataConverter.loadStoreData()) {
		storeMap.put(it.getStoreIdentification(), it);
	}
		for(Person it : DataConverter.loadPersonData()) {
		personMap.put(it.getIdentification(), it);
	}
		for(InvoiceItems it : DataConverter.loadInvoiceItemsData(DataConverter.loadItemsData())) {
		invoiceItemsMap.put(it.getInvoiceCode(), it);
	}
		for(InvoicePeople it : DataConverter.loadInvoicePeopleData()) {
		invoicePeopleMap.put(it.getInvoiceCode(), it);
	}
		String output = """
				+----------------------------------------------------------------------------------------+
				| Summary Report - By Total                                                              |
				+----------------------------------------------------------------------------------------+
				""";
		String summeryReportTitle = "Invoice #    Store    Customer            Num Items        Tax        Total";
		String SummeryReportInformation = output + "\n" + summeryReportTitle ;
		System.out.println(SummeryReportInformation);
		getSummeryReport();
		}
}
		



