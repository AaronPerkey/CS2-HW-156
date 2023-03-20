package com.fmt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;
/**
 * Prints the different invoices to the console.
 * 
 * 
 * @author aaron, emma
 *
 */
public class InvoiceReport {



	public static void main(String[] args) {
		Map<String, Items> itemsMap = new HashMap<>();
		Map<String, Store> storeMap = new HashMap<>();
		Map<String, Person> personMap = new HashMap<>();
		Map<String, Invoice> invoiceMap = new HashMap<>();
		List<String> itemsComparator = new ArrayList<>();
		for(Items it : DataConverter.loadItemsData()) {
		itemsMap.put(it.getItemsQRCode(), it);
	}
		for(Store it : DataConverter.loadStoreData()) {
		storeMap.put(it.getStoreIdentification(), it);
	}
		for(Person it : DataConverter.loadPersonData()) {
		personMap.put(it.getIdentification(), it);
	}
		for(Invoice it : DataConverter.loadInvoiceData()) {
		invoiceMap.put(it.getInvoiceCode(), it);
	}
		for(Items it : DataConverter.loadInvoiceItemsData(DataConverter.loadItemsData())) {
		itemsComparator.add(it.getItemsQRCode());
	}
		Set<String> keySet = invoiceMap.keySet();
		for(String key : keySet) {
			Invoice value = invoiceMap.get(key);
			//System.out.print(value);
		}
//		comparator<String> cmp = new Comparator<String>() {
//		public in compare(String o1, String o2) {
//			return List.compareTo(Integer.valueOf(o2));
//		}
//		}
		String output = """
				+----------------------------------------------------------------------------------------+
				| Summary Report - By Total                                                              |
				+----------------------------------------------------------------------------------------+
				""";
		String summeryReportTitle = "Invoice #    Store    Customer            Num Items        Tax        Total";
		String SummeryReportInformation = output + "\n" + summeryReportTitle ;
		System.out.println(SummeryReportInformation);
		}
}
		



