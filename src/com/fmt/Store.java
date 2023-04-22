package com.fmt;
/**
 * Models a store
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Store {

	private Integer storeId;
	private String storeCode;
	private Person managerCode;
	private Address address;
	private List<Invoice> invoice;

	
	public Store(String storeCode, Person managerCode, Address address, List<Invoice> invoice) {
		super();
		this.storeCode = storeCode;
		this.managerCode = managerCode;
		this.address = address;
		this.invoice = invoice;
	}

	//For binary search
	public Store(String storeCode) {
		super();
		this.storeCode = storeCode;
	}
	
	
	

	public Integer getStoreId() {
		return storeId;
	}

	//for adding a store to sql
	public Store(Integer storeId, String storeCode, Person managerCode, Address address, List<Invoice> invoice) {
		super();
		this.storeId = storeId;
		this.storeCode = storeCode;
		this.managerCode = managerCode;
		this.address = address;
		this.invoice = invoice;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public Person getManagerCode() {
		return managerCode;
	}


	public Address getAddress() {
		return address;
	}
	

	public List<Invoice> getInvoice() {
		return invoice;
	}


	public static void writeJSONStore(List<Store> invoice, String file) throws IOException {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		Gson gson = builder.create();
		FileWriter writer = new FileWriter(file);
			writer.write("{\n");
			writer.write("\"Stores\":[\n");
		for (int i = 0; i < invoice.size(); i++) {
			writer.write(gson.toJson(invoice.get(i)));
			if (i != invoice.size()-1) {
				writer.write(",\n");
			} else {
				writer.write("\n]\n}");
			}
		}
		writer.close();
	}
	
	public String toString () {
		// Writing the store summary report
		StringBuilder string = new StringBuilder();

		Double totalGrandTotal = 0.0;


			String store = this.getStoreCode();
			String manager = this.getManagerCode().getFullName();
			Double grandTotal = this.getGrandTotal();
			
			int sales = this.getInvoice().size();

			totalGrandTotal += grandTotal;
			
			string.append(String.format("%s %20s %15d         $%11.2f \n", store, manager, sales, grandTotal));

			return string.toString();
	}
	
	public Double getGrandTotal() {
		Double grandTotal = 0.0;
		
		for (int j = 0; j < this.getInvoice().size(); j++) {
			grandTotal += this.getInvoice().get(j).getInvoiceTotal();
		}
		return grandTotal;
	}
	
	
}
