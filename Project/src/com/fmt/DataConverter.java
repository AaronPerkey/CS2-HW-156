package com.fmt;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is the main driver program that parses the data file and
 * 
 * @author Kyle Gann, Aaron Perkey
 *
 */

public class DataConverter {
	
	public static String FILE_ITEMS = "data/Items.csv";
	public static String FILE_STORE = "data/Stores.csv";
	public static String FILE_PERSON = "data/Persons.csv";
	public static String FILE_INVOICE = "data/Invoices.csv";
	public static String FILE_INVOICEITEMS = "data/InvoiceItems.csv";

	/**
	 * Parses items from the CSV data file.
	 * 
	 * @return List of Items.
	 */
	public static List<Item> parseDataFileItem() {
		
		List<Item> fullItemsList = new ArrayList<>();
		
		File f = new File(FILE_ITEMS);
		try (Scanner s = new Scanner(f)) {
			int size = 0;
			while (s.hasNext()) {
				String line = s.nextLine();
				if (!line.trim().isEmpty()) {
					if (size == 0) {
						size = Integer.parseInt(line);
						line = s.nextLine();
					}
					Item it = null;
					String tokens[] = line.split(",");
					String itemCode = tokens[0];
					String name = tokens[2];

					if (tokens[1].equals("E")) {
						String model = tokens[3];
						it = new Equipment(0, itemCode, name, model);
					} else if (tokens[1].equals("S")) {
						double hourlyRate = Double.parseDouble(tokens[3]);
						it = new Service(0, itemCode, name, hourlyRate);
					} else if (tokens[1].equals("P")) {
						String unit = tokens[3];
						double price = Double.parseDouble(tokens[4]);
						it = new Product(0, itemCode, name, unit, price);
					}

					fullItemsList.add(it);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return fullItemsList;
	}

	/**
	 * Parses people from the CSV data file.
	 * 
	 * @return List of people.
	 */
	public static List<Person> parseDataFilePerson() {
		
		List<Person> fullPersonList = new ArrayList<>();
		
		File f = new File(FILE_PERSON);
		try (Scanner s = new Scanner(f)) {
			int size = 0;
			while (s.hasNext()) {
				String line = s.nextLine();
				if (!line.trim().isEmpty()) {
					if (size == 0) {
						size = Integer.parseInt(line);
						line = s.nextLine();
					}
					Person e = null;
					String tokens[] = line.split(",");
					String personCode = tokens[0];
					String firstName = tokens[1];
					String lastName = tokens[2];
					String street = tokens[3];
					String city = tokens[4];
					String state = tokens[5];
					String zipCode = tokens[6];
					String country = tokens[7];
					Address address = new Address(street, city, state, zipCode, country);
					List<String> email = new ArrayList<>();
					for (int j = 0; j < tokens.length - 8; j++) {
						email.add(tokens[j + 8]);
					}

					e = new Person(personCode, firstName, lastName, address, email);
					fullPersonList.add(e);
					
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return fullPersonList;
	}

	/**
	 * Parses stores from the CSV data file.
	 * 
	 * @return List of stores.
	 */
	public static List<Store> parseDataFileStore() {
		
		List<Store> fullStoreList = new ArrayList<>();
		
		File f = new File(FILE_STORE);
		try (Scanner s = new Scanner(f)) {
			int size = 0;
			while (s.hasNext()) {
				String line = s.nextLine();
				if (!line.trim().isEmpty()) {
					if (size == 0) {
						size = Integer.parseInt(line);
						line = s.nextLine();
					}
					Store st = null;
					String tokens[] = line.split(",");
					String storeCode = tokens[0];
					String personCode = tokens[1];
					String street = tokens[2];
					String city = tokens[3];
					String state = tokens[4];
					String zipCode = tokens[5];
					String country = tokens[6];
					Address address = new Address(street, city, state, zipCode, country);
					Person managerCode = Person.getPerson(personCode);
					st = new Store(0, storeCode, managerCode, address);
					Store completeStore = getStoreInvoices(st);
					fullStoreList.add(completeStore);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return fullStoreList;
	}

	/**
	 * Parses invoices from the CSV data file.
	 * 
	 * @return List of invoices.
	 */
	public static List<Invoice> parseDataFileInvoice() {
		
		List<Invoice> fullInvoiceList = new ArrayList<>();
		
		File f = new File(FILE_INVOICE);
		try (Scanner s = new Scanner(f)) {
			int size = 0;
			while (s.hasNext()) {
				String line = s.nextLine();
				if (!line.trim().isEmpty()) {
					if (size == 0) {
						size = Integer.parseInt(line);
						line = s.nextLine();
					}
					Invoice in = null;
					String tokens[] = line.split(",");
					String invoiceCode = tokens[0]; 
					String storeCode = tokens[1];   
					Person customer = Person.getPerson(tokens[2]);  
					Person salesPerson = Person.getPerson(tokens[3]);
					LocalDate date = getDate(tokens[4]);
					in = new Invoice(0, invoiceCode, storeCode, customer, salesPerson, date.toString());
					Invoice completeInvoice = getListInvoiceItems(in);
					fullInvoiceList.add(completeInvoice);
				}

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return fullInvoiceList;
	}

	/**
	 * Parses invoice items from the CSV data file.
	 * 
	 * @return List of invoice items.
	 */
	public static List<Item> parseDataFileInvoiceItems() {
		
		List<Item> fullInvoiceItemsList = new ArrayList<>();
		Item it = null;
		Item matchingItem = null;
		LocalDate startDate = null;
		LocalDate endDate = null;
		String typeOfBuy = null;
		String name = null;
		String model = null;
		String unit = null;
		int quantity = 0;
		Double cost = 0.00;
		Double unitPrice = 0.00;
		Double hourlyRate = 0.00;
		Double hoursBilled = 0.00;
		
		File f = new File(FILE_INVOICEITEMS);
		try (Scanner s = new Scanner(f)) {
			int size = 0;
			while (s.hasNext()) {
				String line = s.nextLine();
				if (!line.trim().isEmpty()) {
					if (size == 0) {
						size = Integer.parseInt(line);
						line = s.nextLine();
					}
					String tokens[] = line.split(",");
					String invoiceCode = tokens[0];
					String itemCode = tokens[1];
					for(Item item : parseDataFileItem()) {
						if(item.getItemCode().equals(itemCode)) {
							matchingItem = item;
						}
					}
					name = matchingItem.getName();
					model = matchingItem.getModel();
					unit = matchingItem.getUnit();
					unitPrice = matchingItem.getUnitPrice();
					hourlyRate = matchingItem.getHourlyRate();
					if(matchingItem instanceof Equipment) {
						typeOfBuy = tokens[2];
						cost = Double.parseDouble(tokens[3]);
						if(typeOfBuy.equals("P")) {
							it = new Purchase(0, itemCode, name, model, cost, invoiceCode);
						} else if(typeOfBuy.equals("L")) {
							startDate = getDate(tokens[4]);
							endDate = getDate(tokens[5]);
							it = new Lease(0, itemCode, name, model, cost, startDate, endDate, invoiceCode);
						}
					} else if(matchingItem instanceof Product) {
						quantity = Integer.parseInt(tokens[2]);
						Product p = new Product(0, itemCode, name, unit, unitPrice);
						it = new Product(p, quantity, invoiceCode);
					} else if(matchingItem instanceof Service) {
						hoursBilled = Double.parseDouble(tokens[2]);
						Service sv = new Service(0, itemCode, name, hourlyRate);
						it = new Service(sv, hoursBilled, invoiceCode);
					}

					fullInvoiceItemsList.add(it);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return fullInvoiceItemsList;
	}

	public static LocalDate getDate(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(dateString, formatter);
		return date;
	}
	
	/**
	 * Connects invoices with there corresponding store.
	 * 
	 * @return List of invoices of a certain store.
	 */
	public static Store getStoreInvoices(Store store) {
		
		List<Invoice> invoiceList = new ArrayList<>();

		for (Invoice invoice : parseDataFileInvoice()) {
			if (invoice.getStoreCode().equals(store.getStoreCode())) {
				invoiceList.add(invoice);
			}
		}
		Store s = new Store(store, invoiceList);
		return s;
	}
	
	/**
	 * Connects items with there corresponding invoice
	 * 
	 * @return List of items of a certain invoice
	 */
	public static Invoice getListInvoiceItems(Invoice invoice) {
		
		List<Item> itemList = new ArrayList<>();

		for(Item thing : parseDataFileInvoiceItems()) {
			if(thing.getInvoiceCode().equals(invoice.getInvoiceCode())) {
				itemList.add(thing);
			}
		}
		Invoice in = new Invoice(invoice, itemList);
		return in;
	}
	
	public static void main(String args[]) {
		
		parseDataFileItem();
		parseDataFilePerson();
		parseDataFileStore();
		parseDataFileInvoice();
		parseDataFileInvoiceItems();
		
		try {
			JsonLoader.writeJSONItems(parseDataFileItem(), "data/Items.json");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		try {
			JsonLoader.writeJSONPerson(parseDataFilePerson(), "data/Persons.json");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		try {
			JsonLoader.writeJSONStore(parseDataFileStore(), "data/Stores.json");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}