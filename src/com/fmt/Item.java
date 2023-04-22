package com.fmt;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import java.util.List;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * models an item
 * @author kyleg
 *
 */
public abstract class Item {

	private Integer itemId;
	private String itemCode;
	private String name;
	private String invoiceCode;
	
	public Item(String itemCode, String name, String invoiceCode) {
		this.itemCode = itemCode;
		this.name = name;
		this.invoiceCode = invoiceCode;
	}
	
	public Item(Integer itemId, String itemCode, String name, String invoiceCode) {
		this.itemId = itemId;
		this.itemCode = itemCode;
		this.name = name;
		this.invoiceCode = invoiceCode;
	}



	public Item(String code, String name) {
		this.itemCode = code;
		this.name = name;
	}
	
	public Item(String code) {
		this.itemCode = code;
	}

	public Integer getItemId() {
		return itemId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public String getName() {
		return name;
	}
	
	public Double getFee() {
		return 0.0;
	}
	
	
	
	public abstract Double getTaxRate();
	
	public abstract Double getCost();
	
	public double getTotal() {
		double total = this.getCost() * this.getTaxRate();
		return total;
	}
	
	
	public Double getHourlyRate() {
		return 0.0;
	}
	public Double getHoursBilled() {
		return 0.0;
	}


	public String getUnit() {
		return null;
	}

	public Double getUnitPrice() {
		return 0.0;
	}

	public int getQuantity() {
		return 0;
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
