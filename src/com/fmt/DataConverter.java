package com.fmt;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * This is the main driver program that parses the data file and
 *
 */
public class DataConverter {

	public static List<Item> parseDataFileItem(String file) {
		List<Item> result = new ArrayList<Item>();
		File f = new File(file);
		try (Scanner s = new Scanner(f)) {
			int size = 0;
			while (s.hasNext()) {
				String line = s.nextLine();
				if (!line.trim().isEmpty()) {
					if (size == 0) {
						size = Integer.parseInt(line);
						line = s.nextLine();
					}
					Item e = null;
					String tokens[] = line.split(",");
					String code = tokens[0];
					String name = tokens[2];

					if (tokens[1].equals("E")) {
						String model = tokens[3];
						e = new Equipment(code, name, model);
					} else if (tokens[1].equals("S")) {
						double hourlyRate = Double.parseDouble(tokens[3]);
						e = new Service(code, name, hourlyRate);
					} else if (tokens[1].equals("P")) {
						String unit = tokens[3];
						double price = Double.parseDouble(tokens[4]);
						e = new Product(code, name, unit, price);
					}

					result.add(e);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	public static List<Person> parseDataFilePerson(String file) {
		List<Person> result = new ArrayList<Person>();
		File f = new File(file);
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
					Address address = new Address(tokens[3], tokens[4], tokens[5], tokens[6], tokens[7]);
					List<String> email = new ArrayList<>();
					for (int j = 0; j < tokens.length - 8; j++) {
						email.add(tokens[j + 8]);
					}

					e = new Person(personCode, firstName, lastName, address, email);
					result.add(e);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	public static List<Store> parseDataFileStore(String file) {
		List<Store> result = new ArrayList<Store>();
		File f = new File(file);
		try (Scanner s = new Scanner(f)) {
			int size = 0;
			while (s.hasNext()) {
				String line = s.nextLine();
				if (!line.trim().isEmpty()) {
					if (size == 0) {
						size = Integer.parseInt(line);
						line = s.nextLine();
					}
					Store e = null;
					String tokens[] = line.split(",");
					String storeCode = tokens[0];

					Address address = new Address(tokens[2], tokens[3], tokens[4], tokens[5], tokens[6]);

					List<Invoice> invoices = parseDataFileInvoice("data/Invoices.csv");
					Comparator<Invoice> byStore = Comparator.comparing(Invoice::getStore);
					Collections.sort(invoices, byStore);

					int index = 0;
					List<Invoice> invoicesList = new ArrayList<Invoice>();
					while (index > -1) {
						Invoice key = new Invoice(tokens[0]);
						index = Collections.binarySearch(invoices, key, byStore);
						// find an invoice, get its class
						// add it to the list, remove it from invoice items, search again
						if (index > -1) {
							invoicesList.add(invoices.get(index));
							invoices.remove(index);
						}

					}

					List<Person> persons = parseDataFilePerson("data/Persons.csv");
					Comparator<Person> byId = Comparator.comparing(Person::getPersonCode);
					Collections.sort(persons, byId);
					Person key = new Person(tokens[1]);

					index = Collections.binarySearch(persons, key, byId);
					Person managerCode = new Person(tokens[1], persons.get(index).getFirstName(),
							persons.get(index).getLastName(), persons.get(index).getAddress(),
							persons.get(index).getEmail());

					e = new Store(storeCode, managerCode, address, invoicesList);

					result.add(e);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	public static List<Invoice> parseDataFileInvoice(String file) {
		List<Invoice> result = new ArrayList<Invoice>();
		File f = new File(file);
		try (Scanner s = new Scanner(f)) {
			int size = 0;

			while (s.hasNext()) {

				String line = s.nextLine();
				if (!line.trim().isEmpty()) {
					if (size == 0) {
						size = Integer.parseInt(line);
						line = s.nextLine();
					}
					Invoice e = null;
					String tokens[] = line.split(",");
					List<Item> invoiceItems = parseDataFileInvoiceItems("data/InvoiceItems.csv");

					String storeCode = tokens[1];
					// uses the id given by token 2 to binary search in persons database
					// for all other information about the person
					List<Person> persons = parseDataFilePerson("data/Persons.csv");
					Comparator<Person> byId = Comparator.comparing(Person::getPersonCode);
					Collections.sort(persons, byId);
					Person key = new Person(tokens[2]);
					int index = Collections.binarySearch(persons, key, byId);
					Person customer = new Person(tokens[2], persons.get(index).getFirstName(),
							persons.get(index).getLastName(), persons.get(index).getAddress(),
							persons.get(index).getEmail());

					key = new Person(tokens[3]);
					index = Collections.binarySearch(persons, key, byId);
					Person salesperson = new Person(tokens[3], persons.get(index).getFirstName(),
							persons.get(index).getLastName(), persons.get(index).getAddress(),
							persons.get(index).getEmail());
					String date = (tokens[4]);

					index = 0;
					List<Item> invoiceCodes = new ArrayList<Item>();

					for (int i = 0; i < invoiceItems.size(); i++) {
						String code = invoiceItems.get(i).getInvoiceCode();
						if (tokens[0].equals(code)) {
							index = i;
						} else {
							index = -1;
						}
						//TODO USE INSTANCE OF HERE INSTEAD OF GETCLASS NAME
						if (index > -1) {
							// invoice now has a list as its first element, find an invoice, get its class
							// add it to the list, remove it from invoice items, search again
							if (invoiceItems.get(index).getClass().getSimpleName().equals("Purchase")) {

								Purchase invoiceCode = new Purchase(invoiceItems.get(index).getItemCode(),
										invoiceItems.get(index).getName(), invoiceItems.get(index).getModel(),
										invoiceItems.get(index).getUnitPrice(),
										invoiceItems.get(index).getInvoiceCode());

								invoiceCodes.add(invoiceCode);
								invoiceItems.remove(index);
							} else if (invoiceItems.get(index).getClass().getSimpleName().equals("Lease")) {

								LocalDate startDate = invoiceItems.get(index).getStartDate();
								LocalDate endDate = invoiceItems.get(index).getEndDate();

								Lease invoiceCode = new Lease(invoiceItems.get(index).getItemCode(),
										invoiceItems.get(index).getName(), invoiceItems.get(index).getModel(),
										invoiceItems.get(index).getFee(), startDate, endDate,
										invoiceItems.get(index).getInvoiceCode());
								invoiceCodes.add(invoiceCode);
								invoiceItems.remove(index);

							} else if (invoiceItems.get(index).getClass().getSimpleName().equals("Service")) {

								Service invoiceCode1 = new Service(invoiceItems.get(index).getItemCode(),
										invoiceItems.get(index).getName(), invoiceItems.get(index).getHourlyRate());
								Service invoiceCode = new Service(invoiceCode1,
										invoiceItems.get(index).getHoursBilled(),
										invoiceItems.get(index).getInvoiceCode());

								invoiceCodes.add(invoiceCode);
								invoiceItems.remove(index);
							} else if (invoiceItems.get(index).getClass().getSimpleName().equals("Product")) {

								Product invoiceCode1 = new Product(invoiceItems.get(index).getItemCode(),
										invoiceItems.get(index).getName(), invoiceItems.get(index).getUnit(),
										invoiceItems.get(index).getUnitPrice());
								Product invoiceCode = new Product(invoiceCode1, invoiceItems.get(index).getQuantity(),
										invoiceItems.get(index).getInvoiceCode());
								invoiceCodes.add(invoiceCode);

								invoiceItems.remove(index);
							} else {
								System.out.println(invoiceItems.get(index).getClass().getSimpleName());
							}
						}

					}
					e = new Invoice(tokens[0], invoiceCodes, storeCode, customer, salesperson, date);

					result.add(e);
				}

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	public static List<Item> parseDataFileInvoiceItems(String file) {
		List<Item> result = new ArrayList<Item>();
		File f = new File(file);
		try (Scanner s = new Scanner(f)) {
			int size = 0;
			while (s.hasNext()) {
				String line = s.nextLine();
				if (!line.trim().isEmpty()) {
					if (size == 0) {
						size = Integer.parseInt(line);
						line = s.nextLine();
					}
					Item e = null;
					String tokens[] = line.split(",");
					String invoiceCode = tokens[0];
					String itemCode = tokens[1];

					List<Item> items = DataConverter.parseDataFileItem("data/Items.csv");
					Comparator<Item> byId = Comparator.comparing(Item::getItemCode);
					Collections.sort(items, byId);

					if (tokens[2].equals("P")) {
						Equipment key = new Equipment(tokens[1]);
						int index = Collections.binarySearch(items, key, byId);
						Equipment item = new Equipment(tokens[1], items.get(index).getName(),
								items.get(index).getModel());
						Double price = Double.parseDouble(tokens[3]);

						e = new Purchase(itemCode, item.getName(), item.getModel(), price, tokens[0]);
					} else if (tokens[2].equals("L")) {
						Equipment key = new Equipment(tokens[1]);
						int index = Collections.binarySearch(items, key, byId);
						Equipment item = new Equipment(tokens[1], items.get(index).getName(),
								items.get(index).getModel());
						Double fee = Double.parseDouble(tokens[3]);
						String startDate = tokens[4];
						String endDate = tokens[5];
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						LocalDate startDateLocal = LocalDate.parse(startDate, formatter);
						LocalDate endDateLocal = LocalDate.parse(endDate, formatter);
						e = new Lease(itemCode, item.getName(), item.getModel(), fee, startDateLocal, endDateLocal,
								tokens[0]);
					} else {
						if (tokens[2].contains(".")) {
							Service key = new Service(tokens[1]);
							int index = Collections.binarySearch(items, key, byId);
							Service item = new Service(tokens[1], items.get(index).getName(),
									items.get(index).getHourlyRate());
							Double hoursBilled = Double.parseDouble(tokens[2]);
							Service service = new Service(itemCode, item.getName(), item.getHourlyRate());
							e = new Service(service, hoursBilled, invoiceCode);
						} else {
							Product key = new Product(tokens[1]);
							int index = Collections.binarySearch(items, key, byId);
							Product item = new Product(tokens[1], items.get(index).getName(),
									items.get(index).getUnit(), items.get(index).getUnitPrice());
							int amount = Integer.parseInt(tokens[2]);
							String name = item.getName();
							Product product = new Product(itemCode, name, item.getUnit(), item.getUnitPrice());

							e = new Product(product, amount, invoiceCode);
						}
					}

					result.add(e);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	public static void main(String args[]) {

		List<Item> items = parseDataFileItem("data/Items.csv");
		List<Person> persons = parseDataFilePerson("data/Persons.csv");
		List<Store> stores = parseDataFileStore("data/Stores.csv");
		try {
			Item.writeJSONItems(items, "data/Items.json");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		try {
			Person.writeJSONPerson(persons, "data/Persons.json");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		try {
			Store.writeJSONStore(stores, "data/Stores.json");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		List<Person> Persons2 = parseDataFilePerson("data/personTestCase.csv");
		try {
			Person.writeJSONPerson(Persons2, "src/com/fmt/PersonsTest.json");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
