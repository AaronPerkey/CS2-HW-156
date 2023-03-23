package com.fmt;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class Item {

	private String code;
	private String type;
	private String itemCode;
	private String invoiceCode;
	
	public Item(String code, String type, String itemCode, String invoiceCode) {
		super();
		this.code = code;
		this.type = type;
		this.itemCode = itemCode;
		this.invoiceCode = invoiceCode;
	}

	public Item(String code, String itemCode) {
		this.code = code;
	}
	
	public Item(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	public String getItemCode() {
		return itemCode;
	}
	public String getType() {
		return type;
	}
	
	public double getFee() {
		return -1.0;
	}
	
	public String getInvoiceCode() {
		return invoiceCode;
	}
	
	public abstract Double getTaxRate();
	
	public abstract Double getCost();
	
	public double getTotal() {
		double total = this.getCost() * this.getTaxRate();
		return total;
	}
	
	
	public Double getHourlyRate() {
		return -1.0;
	}
	public Double getHoursBilled() {
		return -1.0;
	}


	public String getUnit() {
		return null;
	}

	public Double getPrice() {
		return -1.0;
	}

	public int getQuantity() {
		return -1;
	}
	
	public String getModel() {
		return null;
	}
	
	public LocalDate getStartDate() {
		return null;
	}
	public LocalDate getEndDate() {
		return null;
	}
	
	public static void writeJSONItems(List<Item> invoice, String file) throws IOException {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		Gson gson = builder.create();
		FileWriter writer = new FileWriter(file);
			writer.write("{\n");
			writer.write("\"items\":[\n");
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
