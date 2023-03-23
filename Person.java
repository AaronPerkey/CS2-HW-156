package com.fmt;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Person {

	private String personCode;
	private String firstName;
	private String lastName;
	private Address address;
	private List<String> email;
	
	public Person(String personCode, String firstName, String lastName, Address address, List<String> email) {
		this.personCode = personCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
	}
	
	
	public Person(String personCode) {
		this.personCode = personCode;
	}


	public String getPersonCode() {
		return personCode;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getFullName() {
		return firstName + ", " + lastName;
	}
	public Address getAddress() {
		return address;
	}
	public List<String> getEmail() {
		return email;
	}
	
	public static void writeJSONPerson(List<Person> invoice, String file) throws IOException {
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
	
}
