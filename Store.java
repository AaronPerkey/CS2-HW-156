package com.fmt;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Store {

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


	public Store(String storeCode) {
		super();
		this.storeCode = storeCode;
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
	
}
