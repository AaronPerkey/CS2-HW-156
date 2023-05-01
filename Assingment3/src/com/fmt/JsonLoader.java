package com.fmt;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Loads CSV data into Json files.
 * 
 * @author Kyle Gann, Aaron Perkey
 *
 */

public class JsonLoader {

	public static void writeJSONPersons(List<Person> invoice, String file) throws IOException {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		Gson gson = builder.create();
		FileWriter writer = new FileWriter(file);
			writer.write("{\n");
			writer.write("\"Persons\":[\n");
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
