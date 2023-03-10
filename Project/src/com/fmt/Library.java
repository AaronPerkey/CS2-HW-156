package com.fmt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
// TODO create hashmaps to connect everything

	public final Map<String, Store> storeMap;
	public final Map<String, Person> personMap;
	public final Map<String, InvoiceItems> invoiceItemsMap;
	public final Map<String, InvoicePeople> invoicePeopleMap;
	
	public Library(Map<String, Items> itemsMap, Map<String, Store> storeMap, Map<String, Person> personMap, Map<String, InvoiceItems> invoiceItemsMap, Map<String, InvoicePeople> invoicePeopleMap) {
		super();
		this.itemsMap = new HashMap<>();
		this.storeMap = new HashMap<>();
		this.personMap = new HashMap<>();
		this.invoiceItemsMap = new HashMap<>();
		this.invoicePeopleMap = new HashMap<>();
	}

	public Map<String, Items> getItemsMap() {

		}

	public Map<String, Store> getStoreMap() {
		for(Store it : DataConverter.loadStoreData()) {
		storeMap.put(it.getStoreIdentification(), it);
	}
		return storeMap;
	}

	public Map<String, Person> getPersonMap() {

		return personMap;
	}

	public Map<String, InvoiceItems> getInvoiceItemsMap() {

		return invoiceItemsMap;
		}

	public Map<String, InvoicePeople> getInvoicePeopleMap() {

		return invoicePeopleMap;
	}

	
	

	
}
