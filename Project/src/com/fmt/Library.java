package com.fmt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Library {
	
	public final List<InvoicePeople> invoicePeopleList;
	public final List<InvoiceItems> invoiceItemsList;
	public final List<Items> itemsList;
	public final List<Equipment> equipmentList;
	public final List<Product> productList;
	public final List<Services> servicesList;
	public final List<Person> personList;
	public final List<Store> storeList;
	
	public Library() {
		this.invoicePeopleList = new ArrayList<InvoicePeople>();
		this.invoiceItemsList = new ArrayList<InvoiceItems>();
		this.itemsList = new ArrayList<Items>();
		this.equipmentList = new ArrayList<Equipment>();
		this.productList = new ArrayList<Product>();
		this.servicesList = new ArrayList<Services>();
		this.personList = new ArrayList<Person>();
		this.storeList = new ArrayList<Store>();
	}

	public List<InvoicePeople> getInvoicePeopleList() {
    	return Collections.unmodifiableList(this.invoicePeopleList);
    }
    
    public List<InvoiceItems> getInvoiceItemsList() {
    	return Collections.unmodifiableList(this.invoiceItemsList);
    }
    
    public List<Items> getItemsList() {
    	return Collections.unmodifiableList(this.itemsList);
    }
    
    public List<Equipment> getEquipmentList() {
    	return Collections.unmodifiableList(this.equipmentList);
    }
    
    public List<Product> getProductList() {
    	return Collections.unmodifiableList(this.productList);
    }
    
    public List<Services> getServicesList() {
    	return Collections.unmodifiableList(this.servicesList);
    }
    
    public List<Person> getPersonList() {
    	return Collections.unmodifiableList(this.personList);
    }
    
    public List<Store> getStoreList() {
    	return Collections.unmodifiableList(this.storeList);
    }
    
    public void addInvoicePeopleList(InvoicePeople newInvoicePerson) {
        this.invoicePeopleList.add(newInvoicePerson);
    }
    
    public void addInvoiceItemsList(InvoiceItems newInvoiceItems) {
        this.invoiceItemsList.add(newInvoiceItems);
    }
    
    public void addItemsList(Items newItems) {
        this.itemsList.add(newItems);
    }
    
    public void addEquipmentList(Equipment newEquipment) {
        this.equipmentList.add(newEquipment);
    }
    
    public void addProductList(Product newProduct) {
        this.productList.add(newProduct);
    }
    
    public void addServicesList(Services newService) {
        this.servicesList.add(newService);
    }
    
    public void addPersonList(Items newItems) {
        this.itemsList.add(newItems);
    } 
    
    public void addStoreList(Store newStore) {
        this.storeList.add(newStore);
    }
    
}
