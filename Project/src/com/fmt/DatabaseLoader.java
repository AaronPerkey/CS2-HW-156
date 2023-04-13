package com.fmt;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This the main data loader and connector to the SQL database
 * 
 * @author KyleG and AaronP
 *
 */

public class DatabaseLoader {

	/**
	 * Loads a person from the sql database
	 * 
	 * @return List of person
	 */
	public static List<Person> loadPersons() {
		List<Person> persons = new ArrayList<>();

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		String personQuery = "Select personId, addressId, personCode, lastName, firstName from Person;";
		try {
			ps = conn.prepareStatement(personQuery);
			rs = ps.executeQuery();
			while (rs.next()) {
				Person e = null;
				
				String personCode = rs.getString("personCode");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				int personId = rs.getInt("personId");
				int addressId = rs.getInt("addressId");

				Address address = getAddress(addressId, conn);
				List<String> email = new ArrayList<>();
				// getting the address
				PreparedStatement psj = null;
				ResultSet rsj = null;
				String emailQuery = "Select address from Email where personId = ?;";
				psj = conn.prepareStatement(emailQuery);
				psj.setInt(1, personId);
				rsj = psj.executeQuery();
				while (rsj.next()) {
					email.add(rsj.getString("address"));
				}
				rsj.close();
				psj.close();

				e = new Person(personId, personCode, firstName, lastName, address, email);
				persons.add(e);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: could not run person queries");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: Could not close connection, something is very wrong");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return persons;

	}

	public static List<Item> loadItems() {
		List<Item> items = new ArrayList<>();

		String url = DatabaseInfo.URL;
		String username = DatabaseInfo.USERNAME;
		String password = DatabaseInfo.PASSWORD;
//		Integer itemId = 0;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		Item i = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String itemQuery = " select itemsId, itemsCode, type, itemName, unit, model," 
				+ "unitPrice, hourlyRate from Items;";
		try {
			ps = conn.prepareStatement(itemQuery);

			rs = ps.executeQuery();
			while (rs.next()) {
//				itemId = rs.getInt("itemId");
				String itemCode = rs.getString("itemsCode");
				String typeOfSale = rs.getString("type");
				String name = rs.getString("itemName");
				String unit = rs.getString("unit");
				String model = rs.getString("model");
				Double price = rs.getDouble("unitPrice");
				Double hourlyRate = rs.getDouble("hourlyRate");
				if (typeOfSale.equals("E")) {
					i = new Equipment(itemCode, name, model);
				}
				if (typeOfSale.equals("P")) {
					i = new Product(itemCode, name, unit, price);
				}
				if (typeOfSale.equals("S")) {
					i = new Service(itemCode, name, hourlyRate);
				}
				items.add(i);
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return items;
	}

	public static Address getAddress(int addressId, Connection conn) {

		String street = null;
		String city = null;
		String state = null;
		String zip = null;
		String country = null;
		
		try {
			// getting the address
			PreparedStatement psj = null;
			ResultSet rsj = null;
			String addressQuery = """
						select Address.addressId, Street.street, City.city, State.state, ZipCode.zipCode, Country.country from Address
						right join Street on Street.streetId = Address.streetId
                        right join City on City.cityId = Address.cityId
						right join State on State.stateId = Address.stateId
                        right join ZipCode on ZipCode.zipCodeId = Address.zipCodeId
						right join Country on Country.countryId = Address.countryId
						where Address.addressId = ?;
						""";
			psj = conn.prepareStatement(addressQuery);
			psj.setInt(1, addressId);
			rsj = psj.executeQuery();
			if (rsj.next()) {
				street = rsj.getString("street");
				zip = rsj.getString("zipCode");
				state = rsj.getString("state");
				zip = rsj.getString("zipCode");
				country = rsj.getString("country");
			}
			rsj.close();
			psj.close();
			Address address = new Address(addressId, street, city, state, zip, country);
			return address;
		} catch (SQLException e) {
			System.out.println("SQLException: Cannot find address with addressid" + addressId);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static Person getPerson(int personId) {
		Person person = null;
		for (Person dude : loadPersons()) {
			if (dude.getPersonId().equals(personId)) {
				person = dude;
			}
		}
		return person;
	}

	public static Store getStore(int storeId) {
		Store store = null;
		for (Store market : loadStore()) {
			if (market.getStoreId().equals(storeId)) {
				store = market;
			}
		}
		return store;
	}

	public static List<Store> loadStore() {
		List<Store> stores = new ArrayList<>();

		String url = DatabaseInfo.URL;
		String username = DatabaseInfo.USERNAME;
		String password = DatabaseInfo.PASSWORD;

		Integer storeId = 0;
		String storeCode;
		int managerId = 0;
		int addressId = 0;
		Address addressConnection;
		Person perosnConnection;

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		Store s = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String storeQuery = "select storeId, storeCode, manager, addressId from Store;";
		try {
			ps = conn.prepareStatement(storeQuery);

			rs = ps.executeQuery();
			while (rs.next()) {
				storeId = rs.getInt("storeId");
				storeCode = rs.getString("storeCode");
				managerId = rs.getInt("manager");
				addressId = rs.getInt("addressId");
				addressConnection = getAddress(addressId, conn);
				perosnConnection = getPerson(managerId);

				List<Invoice> invoices = new ArrayList<>();

				Invoice found = null;
				for (Invoice storesInvoice : loadInvoice()) {
					if (storesInvoice.getStore().equals(storeCode)) {
						found = storesInvoice;
					}
				}
				invoices.add(found);
				// getting the address

				s = new Store(storeId, storeCode, perosnConnection, addressConnection, invoices);
				stores.add(s);
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return stores;
	}

	public static List<Invoice> loadInvoice() {
		List<Invoice> invoices = new ArrayList<>();

		String url = DatabaseInfo.URL;
		String username = DatabaseInfo.USERNAME;
		String password = DatabaseInfo.PASSWORD;

		Integer invoiceId = 0;
		Person perosnConnection1;
		Person perosnConnection2;

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		Invoice i = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String storeQuery = "select invoiceId, invoiceCode, storeId, customer, salesPerson, date from Invoice;";
		try {
			ps = conn.prepareStatement(storeQuery);

			rs = ps.executeQuery();
			while (rs.next()) {
				invoiceId = rs.getInt("invoiceId");
				String invoiceCode = rs.getString("invoiceCode");
				int storeId = rs.getInt("storeId");
				
				int customerId = rs.getInt("customer");
				int salespersonId = rs.getInt("salesperson");
				String datePurchased = rs.getString("date");
				
				String storeCode = getStore(storeId).getStoreCode();
				perosnConnection1 = getPerson(customerId);
				perosnConnection2 = getPerson(salespersonId);

				List<Item> item = new ArrayList<>();

				Item found = null;
				for (Item itemInvoice : loadInvoiceItems()) {
					if (itemInvoice.getInvoiceCode().equals(invoiceCode)) {
						found = itemInvoice;
					}
				}
				item.add(found);

				i = new Invoice(invoiceId, invoiceCode, item, storeCode, perosnConnection1, perosnConnection2, datePurchased);
				invoices.add(i);
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return invoices;
	}

	public static List<Item> loadInvoiceItems() {
		List<Item> items = new ArrayList<>();

//		Integer invoiceItemsId = 0;
//		Integer invoiceId = 0;
		Integer itemId = 0;
		String typeOfBuy = null;
		Double price = 0.0;
		Integer quantity = 0;
		Double hoursBilled = 0.0;
		LocalDate startDate = null;
		LocalDate endDate = null;

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		// now get all the songs
		String invoiceItemQuery = "Select invoiceItemsId, invoiceId, itemId, typeOfBuy, price, quantity, hoursBilled, startDate, endDate from InvoiceItems;";
		try {
			ps = conn.prepareStatement(invoiceItemQuery);
			rs = ps.executeQuery();

			Item i = null;
			while (rs.next()) {
//				invoiceItemsId = rs.getInt("invoiceItemsId");
//				invoiceId = rs.getInt("invoiceId");
				itemId = rs.getInt("itemId");
				typeOfBuy = rs.getString("typeOfBuy");
				price = rs.getDouble("price");
				quantity = rs.getInt("quanity");
				hoursBilled = rs.getDouble("hoursBilled");
				String startDateS = rs.getString("startDate");
				String endDateS = rs.getString("endDate");
				startDate = LocalDate.parse(startDateS);
				endDate = LocalDate.parse(endDateS);
				String itemCode = null;
				String invoiceCode = null;
				String name = null;
				String model = null;
				String unit = null;
				Double hourlyRate = 0.0;
				for (Item x : loadItems()) {
					if (x.getItemId().equals(itemId)) {
						itemCode = x.getItemCode();
						invoiceCode = x.getInvoiceCode();
						name = x.getName();
						model = x.getModel();
						unit = x.getUnit();
						hourlyRate = x.getHourlyRate();
					}
				}
				if (typeOfBuy != null) {
					if (typeOfBuy.equals("P")) {

						i = new Purchase(itemCode, name, model, price, invoiceCode);
					} else if (typeOfBuy.equals("L")) {
						i = new Lease(itemCode, name, model, price, startDate, endDate, invoiceCode);
					}
				}
				
					if (hoursBilled.equals(null)) {
						Product i1 = new Product(itemCode, name, unit, price);
						i = new Product(i1, quantity, invoiceCode);
					} else {
						Service i1 = new Service(itemCode, name, hourlyRate);
						i = new Service(i1, hoursBilled, invoiceCode);
					}
				items.add(i);
			}
		} catch (SQLException e) {
			System.out.println("SQLException: could not run person queries");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return items;
	}

	public static void main(String[] args) {
//		List<Person> persons = loadPersons();
//
//		for (int i = 0; i < persons.size(); i++) {
//			System.out.println(persons.get(i).toString());
//		}
		
//		List<Item> items = loadItems();
//
//		for (int i = 0; i < items.size(); i++) {
//			System.out.println(items.get(i).toString());
//		}
		List<Store> stores = loadStore();

		for (int i = 0; i < stores.size(); i++) {
			System.out.println(stores.get(i).toString());
		}
		
		

	}
}
