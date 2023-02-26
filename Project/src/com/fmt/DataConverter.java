package com.fmt;

import com.cinco.payroll.Employee;
import com.google.gson.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
	public static final String FILE_PERSON = "data/persons.csv";


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
						e = new Equipment(itemQRCode, typeOfSale, name, model);
					} else if (tokens[1].equals("P")) {
						unit = tokens[3];
						unitPrice = Double.parseDouble(tokens[4]);
						// 
						e = new Product(itemQRCode, typeOfSale, name, unit, unitPrice);
					} else if (tokens[1].equals("S")) {
						hourlyRate =  Double.parseDouble(tokens[3]);
						// 
						e = new Services(itemQRCode, typeOfSale, name, hourlyRate);
					}

					result.add(e);
					
//					System.out.println(Json);
					
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
					String tokens[] = line.split(",");
					String identification = tokens[0];
					String lastName = tokens[1];
					String firstName = tokens[2];
					String address = tokens[3];
					String city = tokens[4];
					String state = tokens[5];
					String zipcode = tokens[6];
					String country = tokens[7];
					String email1 = null;
					String email2 = null;
					String email3 = null;
					String email4 = null;
					System.out.println(tokens.length);
//					int numberOfEmails = tokens.length - 8;
//					if(numberOfEmails == 1) {
//						email1 = tokens[8];
//				} else if(numberOfEmails == 2) {
//					email1 = tokens[8];
//					email2 = tokens[9];
//				} else if(numberOfEmails == 3) {
//					email1 = tokens[8];
//					email2 = tokens[9];
//					email3 = tokens[10];
//				} else if(numberOfEmails == 4) {
//					email1 = tokens[8];
//					email2 = tokens[9];
//					email3 = tokens[10];
//					email4 = tokens[11];
//				}
					l = new Location(address, city, state, zipcode, country);
					f = new FullName(lastName, firstName);
					p = new Person(identification, f, l, email1, email2, email3, email4);
					result.add(p);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Encountered Error on line " + line, e);
		}

		return result;
	}

	public static void main(String[] args) {
		
		List<Items> itemsResult = loadItemsData();
		File itemsJson = new File("data/Items.json");
		Gson gsoni = new GsonBuilder().setPrettyPrinting().create();
		String Jsoni = gsoni.toJson(itemsResult);
		try {
			  FileWriter fw = new FileWriter(itemsJson);
			  fw.write(Jsoni);
			  fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		List<Store> storeResult = loadStoreData();
		File storeJson = new File("data/Stores.json");
		Gson gsons = new GsonBuilder().setPrettyPrinting().create();
		String Jsons = gsons.toJson(storeResult);
		try {
			  FileWriter fw = new FileWriter(storeJson);
			  fw.write(Jsons);
			  fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		List<Person> personResult = loadPersonData();
		File personJson = new File("data/Person.json");
		Gson gsonp = new GsonBuilder().setPrettyPrinting().create();
		String Jsonp = gsonp.toJson(personResult);
		try {
			  FileWriter fw = new FileWriter(personJson);
			  fw.write(Jsonp);
			  fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
