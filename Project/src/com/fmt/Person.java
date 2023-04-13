package com.fmt;
/**
 * models a person who might be a customer manager or salesperson
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Person {

	private Integer personId;
	private String personCode;
	private String firstName;
	private String lastName;
	private Address address;
	private List<String> emails;
	
	public Person(String personCode, String firstName, String lastName, Address address, List<String> email) {
		this.personCode = personCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.emails = email;
	}
	
	
	
	
	public Person(String personCode) {
		this.personCode = personCode;
	}
	
	

	// for adding a person to sql
	public Person(Integer personId, String personCode, String firstName, String lastName, Address address,
			List<String> emails) {
		this.personId = personId;
		this.personCode = personCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.emails = emails;
	}

	



	public Integer getPersonId() {
		return personId;
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
		return emails;
	}
	
	public void addEmail(String email) {
		this.emails.add(email);
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
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		String customer = this.getFullName();
		String customerCode = this.getPersonCode();

		int len = this.getEmail().size();
		
		string.append(customer + "(" + customerCode + " : ");
		for (int j = 0; j < len; j++) {
			
			string.append(this.getEmail().get(j));
			if (j != len - 1) {
			
				string.append(", ");
			}
		}
	
		string.append(")");

		String customerAddress = this.getAddress().getFullAddress();
		
		string.append("\n" + customerAddress + "\n");
		return string.toString();
	}
	
	
	
}
