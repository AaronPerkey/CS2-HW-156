package com.fmt;
//TODO why is the gson import not working!!!!!
import com.google.gson.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/**
 * 
 * Processes a comma-separated value (CSV) file of items, stores, and persons. It will
 * convert csv files into json files.
 * 
 * @author aaron, emma
 *
 */
public class DataConverter {

	public static final String FILE_ITEMS = "data/Items.csv";
	public static final String FILE_STORE = "data/Stores.csv";
	public static final String FILE_PERSON = "data/Persons.csv";
	public static final String FILE_INVOICEPEOPLE = "data/Invoices.csv";
	public static final String FILE_INVOICEITEMS = "data/InvoiceItems.csv";
	


	public static List<Items> loadItemsData() {

		List<Items> result = new ArrayList<Items>();
		
		String line = null;

		try (Scanner s = new Scanner(new File(FILE_ITEMS))) {

			int numRecords = Integer.parseInt(s.nextLine());
			for (int i = 0; i < numRecords; i++) {

				line = s.nextLine();
				if (!line.trim().isEmpty()) {
					Items e = null;
					String tokens[] = line.split(",");
					String itemQRCode = tokens[0];
					String typeOfSale = tokens[1];
					String name = tokens[2];
					String model = null;
					String unit = null;
					double unitPrice  = 0;
					double hourlyRate = 0;
					if (tokens[1].equals("E")) {
						model = tokens[3];
						// 
						e = new Equipment(invoiceCode, itemQRCode, typeOfSale, cost, quantity, startDate, endDate,  name, model);
					} else if (tokens[1].equals("P")) {
						unit = tokens[3];
						unitPrice = Double.parseDouble(tokens[4]);
						// 
						e = new Product(itemQRCode, typeOfSale, name, unit, unitPrice);
					} else if (tokens[1].equals("S")) {
						hourlyRate = Double.parseDouble(tokens[3]);
						// 
						e = new Services(itemQRCode, typeOfSale, name, hourlyRate);
					}

					result.add(e);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Encountered Error on line " + line, e);
		}
		return result;
	}	

	public static List<Store> loadStoreData() {

		List<Store> result = new ArrayList<Store>();
		
		String line = null;

		try (Scanner s = new Scanner(new File(FILE_STORE))) {

			int numRecords = Integer.parseInt(s.nextLine());
			for (int i = 0; i < numRecords; i++) {

				line = s.nextLine();
				if (!line.trim().isEmpty()) {
					Store e = null;
					Location l = null;
					String tokens[] = line.split(",");
					String storeIdentification = tokens[0];
					String identification = tokens[1];
					String address = tokens[2];
					String city = tokens[3];
					String state = tokens[4];
					String zipcode = tokens[5];
					String country = tokens[6];
					l = new Location(address, city, state, zipcode, country);
					e = new Store(storeIdentification, identification, l);
					result.add(e);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Encountered Error on line " + line, e);
		}

		return result;
	}
	
	public static List<Person> loadPersonData() {

		List<Person> result = new ArrayList<Person>();
		
		String line = null;

		try (Scanner s = new Scanner(new File(FILE_PERSON))) {

			int numRecords = Integer.parseInt(s.nextLine());
			for (int i = 0; i < numRecords; i++) {

				line = s.nextLine();
				if (!line.trim().isEmpty()) {
					FullName f = null;
					Location l = null;
					Person p = null;
					Email em = null;
					String tokens[] = line.split(",");
					String identification = tokens[0];
					String lastName = tokens[1];
					String firstName = tokens[2];
					String address = tokens[3];
					String city = tokens[4];
					String state = tokens[5];
					String zipcode = tokens[6];
					String country = tokens[7];
					String email = null;
					List<Email> emails = new ArrayList<Email>();
					int numberOfEmails = tokens.length - 8;
					for(int x = 6; i <= numberOfEmails; x++) {
						email = tokens[x];
						em = new Email(identification, email);
						emails.add(em);
						x += 1;
					l = new Location(address, city, state, zipcode, country);
					f = new FullName(lastName, firstName);
					p = new Person(identification, f, l, emails);
					result.add(p);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Encountered Error on line " + line, e);
		}

		return result;
	}

	public static List<InvoicePeople> loadInvoicePeopleData() {

		List<InvoicePeople> result = new ArrayList<InvoicePeople>();
		
		String line = null;

		try (Scanner s = new Scanner(new File(FILE_INVOICEPEOPLE))) {

			int numRecords = Integer.parseInt(s.nextLine());
			for (int i = 0; i < numRecords; i++) {

				line = s.nextLine();
				if (!line.trim().isEmpty()) {
					InvoicePeople ip = null;
					String tokens[] = line.split(",");
					String invoiceCode = tokens[0];
					String storeIdentification = tokens[1];
					String customerID = tokens[2];
					String salesPersonID = tokens[3];
					String invoiceDate = tokens[4];
			        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			        LocalDate date = LocalDate.parse(invoiceDate, formatter);
					ip = new InvoicePeople(invoiceCode, storeIdentification, customerID, salesPersonID, date);
				result.add(ip);					
			}
		}
	} catch (Exception e) {
		throw new RuntimeException("Encountered Error on line " + line, e);
	}
	return result;
}	
	
	public static List<InvoiceItems> loadInvoiceItemsData(List<Items> items) {
		
		List<InvoiceItems> result = new ArrayList<InvoiceItems>();
		
		String line = null;

		try (Scanner s = new Scanner(new File(FILE_INVOICEITEMS))) {

			int numRecords = Integer.parseInt(s.nextLine());
			for (int i = 0; i < numRecords; i++) {

				line = s.nextLine();
				if (!line.trim().isEmpty()) {
					InvoiceItems ii = null;
					String tokens[] = line.split(",");
					String invoiceCode = tokens[0];
					String itemsQRCode = tokens[1];
					Items hello = null;
					LocalDate startdate = null;
					LocalDate endDate = null;
					double  quantity = 0.00;
					double  cost = 0.00;
					String typeOfSale = null;
					for(Items hi : items) {
						if(hi.getItemsQRCode().equals(itemsQRCode)) {
							hello = hi;
						}
					}
					if(hello instanceof Equipment) {
						typeOfSale = tokens[2];
						if(typeOfSale.equals("P")) {
							cost = Double.parseDouble(tokens[3]);
						}else if(typeOfSale.equals("L")) {
							cost = Double.parseDouble(tokens[3]);
							String dayStarted = tokens[4];
							String dayEnd = tokens[5];
					        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					        startdate = LocalDate.parse(dayStarted, formatter);
					        endDate = LocalDate.parse(dayEnd, formatter);
						}
					}else if(hello instanceof Product) {
						quantity = Double.parseDouble(tokens[4]);
					}else if(hello instanceof Services) {
						quantity = Double.parseDouble(tokens[4]);
					}
					ii = new InvoiceItems(invoiceCode, itemsQRCode, typeOfSale, cost, quantity, startdate, endDate);
					result.add(ii);					
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Encountered Error on line " + line, e);
		}
		return result;
	}	
	
	public static void main(String[] args) {
		
//		List<Items> itemsResult = loadItemsData();
//		File itemsJson = new File("data/Items.json");
//		Gson gsoni = new GsonBuilder().setPrettyPrinting().create();
//		String Jsoni = gsoni.toJson(itemsResult);
//		try {
//			  FileWriter fw = new FileWriter(itemsJson);
//			  fw.write(Jsoni);
//			  fw.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		
//		List<Store> storeResult = loadStoreData();
//		File storeJson = new File("data/Stores.json");
//		Gson gsons = new GsonBuilder().setPrettyPrinting().create();
//		String Jsons = gsons.toJson(storeResult);
//		try {
//			  FileWriter fw = new FileWriter(storeJson);
//			  fw.write(Jsons);
//			  fw.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		
//		List<Person> personResult = loadPersonData();
//		File personJson = new File("data/Person.json");
//		Gson gsonp = new GsonBuilder().setPrettyPrinting().create();
//		String Jsonp = gsonp.toJson(personResult);
//		try {
//			  FileWriter fw = new FileWriter(personJson);
//			  fw.write(Jsonp);
//			  fw.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		
	}

}
