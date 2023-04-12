package com.fmt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseLoader {
	
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
		//now get all the songs
		String personQuery = "Select personId, addressId, personCode, lastName, firstName from Person;";
		try {
			ps = conn.prepareStatement(personQuery);
			rs = ps.executeQuery();
			while(rs.next()) {
				Person e = null;
				String personCode = rs.getString("personCode");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				int personId = rs.getInt("personId");
				int addressId = rs.getInt("addressId");
				
				String street = null;
				String city = null;
				String state = null;
				String zipCode = null;
				String country = null;
				
				Address address = getAddress(addressId, conn);
				List<String> email = new ArrayList<>();

				email.add("Bob@gmail.com");


				e = new Person(personCode, firstName, lastName, address, email);
				persons.add(e);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return persons;
		
	}
	
		public static List<Item> loadItems() {
		List<Item> items = new ArrayList<>();
		
		String url = DatabaseInfo.URL;
		String username = DatabaseInfo.USERNAME;
		String password = DatabaseInfo.PASSWORD;
		
		Integer itemId = 0;
		
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
		String itemQuery = """
				select Item.itemId, Item.itemCode, Item.typeOfSale, Item.name, Item.unit, Item.model, 
				Item.price, Item.hourlyRate from Item;
				""";
		try {
			ps = conn.prepareStatement(itemQuery);
			ps.setInt(1, itemId);
			rs = ps.executeQuery();
			while(rs.next()) {
				String itemCode = rs.getString("Item.itemCode");
				String typeOfSale = rs.getString("Item.typeOfSale");
				String name = rs.getString("Item.name");
				String unit = rs.getString("Item.unit");
				String model = rs.getString("Item.model");
				double price = rs.getDouble("Item.pirce");
				double hourlyRate = rs.getDouble("Item.hourlyRate");
				if(typeOfSale.equals("E")) {
					i = new Equipment(itemCode, name, model);
				}
				if(typeOfSale.equals("P")) {
					i = new Product(itemCode, name, unit, price);
				}
				if(typeOfSale.equals("S")) {
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
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
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
		String country = null;
		String state = null;
		
		try {
			//getting the address
			PreparedStatement psj = null;
			ResultSet rsj = null;
			String addressQuery = """
				select Adress.adressId, Address.address, State.state, Country.country from Address
					right join State on State.stateId = Address.stateId
					right join Country on Country.countryId = Address.countryId
					where Address.addressId = ?;
					""";
			psj = conn.prepareStatement(addressQuery);
			psj.setInt(1, addressId);
			rsj = psj.executeQuery();
			if (rsj.next()) {
				street = rsj.getString("Address.address");
				state = rsj.getString("State.state");
				country = rsj.getString("Country.country");
			}
			rsj.close();
			psj.close();
			Address address = new Address(addressId, street, state, country);
			return address;
			} catch (SQLException e) {
				System.out.println("SQLException: Cannot find address with addressid" + addressId);
				e.printStackTrace();
				throw new RuntimeException(e);
			}
	}

	public static Person getPerson(int personId) {
		Person person = null;
		for(Person dude: loadPersons()) {
			if(dude.getPersonId().equals(personId)) { 
				person = dude;
			}
		}
		return person;
	}	
	
	public static Store getStore(int storeId) {
		Store store = null;
		for(Store market: loadStore()) {
			if(market.getStoreId().equals(storeId)) { 
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
		String storeQuery = "select storeId, storeCode, managerId, addressId from Store;";
		try {
			ps = conn.prepareStatement(storeQuery);
			ps.setInt(1, storeId);
			rs = ps.executeQuery();
			while(rs.next()) {
				storeCode = rs.getString("StoreCode");
				managerId = rs.getInt("managerId");
				addressId = rs.getInt("addressId");
				addressConnection = getAddress(addressId, conn);
				perosnConnection = getPerson(managerId);
				s = new Store(storeId, storeCode, perosnConnection, addressConnection);
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
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
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
		Person storeConnection;
		
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
		String storeQuery = "select invoiceId, invoiceCode, storeId, customerId, salespersonId, date from Invoice;";
		try {
			ps = conn.prepareStatement(storeQuery);
			ps.setInt(1, invoiceId);
			rs = ps.executeQuery();
			while(rs.next()) {
				String invoiceCode = rs.getString("invoiceCode");
				int storeId = rs.getInt("storeId");
				int customerId = rs.getInt("customerId");
				int salespersonId = rs.getInt("salespersonId");
				String d = rs.getString("date");
				LocalDate date = LocalDate.parse(d);
				storeConnection = getPerson(storeId);
				perosnConnection1 = getPerson(customerId);
				perosnConnection2 = getPerson(salespersonId);
				i = new Invoice(invoiceId, invoiceCode, storeConnection, perosnConnection1, perosnConnection2, d);
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
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return invoices;
	}
	
	public static void main(String[] args) {
		List<Person> persons = loadPersons();
		
		for (int i = 0; i < persons.size(); i++) {
			System.out.println(persons.get(i).toString());
		}
		
	}

	
}

	
}
