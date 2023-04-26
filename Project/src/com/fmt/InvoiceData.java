package com.fmt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class InvoiceData {

	/**
	 * Removes all records from all tables in the database.
	 */
	public static void clearDatabase() {
    //TODO: implement

	}

	/**
	 * Method to add a Address record to the database with the provided data.
	 * 
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @return
	 */
	public static Integer getAddress(String street, String city, String state, String zip, String country) {
		
		Integer countryId = -1;
		Integer stateId = -1;
		Integer addressId = -1;

		try {
			Connection conn = DatabaseInfo.openConnectSQL();
			String query0 = "select countryId from Country where country = ?;";
			PreparedStatement ps0 = null;
			ps0 = conn.prepareStatement(query0);
			ps0.setString(1, country);
			ResultSet rs0 = ps0.executeQuery();
			if(rs0.next()) {
				countryId = rs0.getInt("countryId");
			}
			DatabaseInfo.closeConnection(conn, ps0, rs0);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		if(countryId == -1) {

			try {
				Connection conn = DatabaseInfo.openConnectSQL();
				String query = "insert into Country (country) values (?);";
				PreparedStatement ps;
				ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, country);
				ps.executeUpdate();
				// get the generated keys:
				ResultSet keys = ps.getGeneratedKeys();
				keys.next();
				int key = keys.getInt(1);
				countryId = key;
				DatabaseInfo.closeConnection(conn, ps, keys);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		try {
			Connection conn = DatabaseInfo.openConnectSQL();
			String query0 = "select stateId from State where state = ?;";
			PreparedStatement ps = null;
			ps = conn.prepareStatement(query0);
			ps.setString(1, state);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				stateId = rs.getInt("stateId");
			}
			DatabaseInfo.closeConnection(conn, ps, rs);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		if(stateId == -1) {

			try {
				Connection conn = DatabaseInfo.openConnectSQL();
				String query = "insert into State (state) values (?);";
				PreparedStatement ps;
				ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, state);
				ps.executeUpdate();
				// get the generated keys:
				ResultSet keys = ps.getGeneratedKeys();
				keys.next();
				int key = keys.getInt(1);
				stateId = key;
				DatabaseInfo.closeConnection(conn, ps, keys);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		try {
		Connection conn = DatabaseInfo.openConnectSQL();
		PreparedStatement ps = null;
		String createPersonQuery = "insert into Address (street, city, zip, stateId, countryId) values (?,?,?,?,?);";
		ps = conn.prepareStatement(createPersonQuery, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, street);
		ps.setString(2, city);
		ps.setString(3, zip);
		ps.setInt(4, stateId);
		ps.setInt(5, countryId);
		ps.executeUpdate();
		// get the generated keys:
		ResultSet keys = ps.getGeneratedKeys();
		keys.next();
		int key = keys.getInt(1);
		addressId = key;
		DatabaseInfo.closeConnection(conn, ps, keys);
		}catch (SQLException e) {
		System.out.println("SQLException: ");
		e.printStackTrace();
		throw new RuntimeException(e);
		}
		return addressId;
	}

	
	/**
	 * Method to add a person record to the database with the provided data.
	 *
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	
	public static void addPerson(String personCode, String firstName, String lastName, String street,
			String city, String state, String zip, String country) {
		
		if(personCode == null || firstName == null || lastName == null || street == null || 
				city == null || state == null || zip == null || country == null) {
			throw new IllegalArgumentException("Invalid person data. (one of the veriables equal null)");
		}
		
		Integer personId = Person.getPerson(personCode);
		
		if(personId == -1) {
			
			Integer addressId = getAddress(street, city, state, zip, country);
			
  			try {
  			Connection conn = DatabaseInfo.openConnectSQL();
  			PreparedStatement ps = null;
  			String createPersonQuery = "insert into Person (personCode, lastName, firstName, addressId) values (?,?,?,?);";
			ps = conn.prepareStatement(createPersonQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, personCode);
			ps.setString(2, lastName);
			ps.setString(3, firstName);
			ps.setInt(4, addressId);
			ps.executeUpdate();
			
			// get the generated keys:
			ResultSet keys = ps.getGeneratedKeys();
			keys.next();
			int key = keys.getInt(1);
			addressId = key;
			DatabaseInfo.closeConnection(conn, ps, keys);
  			}catch (SQLException e) {
  			System.out.println("SQLException: ");
  			e.printStackTrace();
  			throw new RuntimeException(e);
  			}
  			DatabaseLoader.fullPerson = new ArrayList<>();
		} else {
			throw new IllegalArgumentException("Person already exsists.");
		}

	}
		
	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 *
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		
		Integer personId = Person.getPerson(personCode);
		Person person = Person.getPerson(personId);

		if(personId == -1) {
			throw new IllegalArgumentException("This person does not exist in the database.");
		}
		
			try {
			Connection conn = DatabaseInfo.openConnectSQL();
			PreparedStatement ps = null;
			ResultSet rs = null;
			String createPersonQuery = "insert into Email (personId, address) values (?,?);";
		ps = conn.prepareStatement(createPersonQuery, Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, personId);
		ps.setString(2, email);
		ps.executeUpdate();
		DatabaseInfo.closeConnection(conn, ps, rs);
			}catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
			}
			List<String> emails = person.getEmail();
			emails.add(email);
			DatabaseLoader.fullPerson = new ArrayList<>();
	}

	/**
	 * Adds a store record to the database managed by the person identified by the
	 * given code.
	 *
	 * @param storeCode
	 * @param managerCode
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addStore(String storeCode, String managerCode, String street, String city, String state,
			String zip, String country) {
		
		if(storeCode == null || managerCode == null || street == null || city == null || state == null 
				|| zip == null || country == null) {
			throw new IllegalArgumentException("Invalid store data. (one of the veriables equal null)");
		}
		
		Integer storeId = Store.getStore(storeCode);
		
		if(storeId == -1) {
			
			Integer managerId = Person.getPerson(managerCode);
			
			if(managerId == -1) {
				throw new IllegalArgumentException("Person does not exsist.");
			}
			
			Integer addressId = getAddress(street, city, state, zip, country);
			
  			try {
  			Connection conn = DatabaseInfo.openConnectSQL();
  			PreparedStatement ps = null;
  			ResultSet rs = null;
  			String createPersonQuery = "insert into Store (storeCode, managerId, addressId) values (?,?,?);";
			ps = conn.prepareStatement(createPersonQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, storeCode);
			ps.setInt(2, managerId);
			ps.setInt(3, addressId);
			ps.executeUpdate();
			DatabaseInfo.closeConnection(conn, ps, rs);
  			}catch (SQLException e) {
  			System.out.println("SQLException: ");
  			e.printStackTrace();
  			throw new RuntimeException(e);
  			}
		} else {
			throw new IllegalArgumentException("Store already exsists.");
		}
		
	}

	/**
	 * Adds a product record to the database with the given <code>code</code>, <code>name</code> and
	 * <code>unit</code> and <code>pricePerUnit</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param unit
	 * @param pricePerUnit
	 */
	public static void addProduct(String code, String name, String unit, double pricePerUnit) {
    //TODO: implement

	}

	/**
	 * Adds an equipment record to the database with the given <code>code</code>,
	 * <code>name</code> and <code>modelNumber</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param modelNumber
	 */
	public static void addEquipment(String code, String name, String modelNumber) {
    //TODO: implement

	}

	/**
	 * Adds a service record to the database with the given <code>code</code>,
	 * <code>name</code> and <code>costPerHour</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param modelNumber
	 */
	public static void addService(String code, String name, double costPerHour) {
    //TODO: implement

	}

	/**
	 * Adds an invoice record to the database with the given data.
	 *
	 * @param invoiceCode
	 * @param storeCode
	 * @param customerCode
	 * @param salesPersonCode
   * @param invoiceDate
	 */
	public static void addInvoice(String invoiceCode, String storeCode, String customerCode, String salesPersonCode, String invoiceDate) {
    

		
		if(invoiceCode == null || storeCode == null || customerCode == null || salesPersonCode == null || invoiceDate == null) {
			throw new IllegalArgumentException("Invalid invoice data. (one of the veriables equal null)");
		}
		
		Integer invoiceId = Invoice.getInvoice(invoiceCode);
		
		if(invoiceId == -1) {
			
			Integer storeId = Store.getStore(storeCode);
			Integer salesPersonId = Person.getPerson(salesPersonCode);
			Integer customerId = Person.getPerson(customerCode);
			
			if(storeId == -1) {
				throw new IllegalArgumentException("Store does not exsist.");
			}
			if(salesPersonId == -1 || customerId == -1) {
				throw new IllegalArgumentException("One of the people does not exsist.");
			}
			
  			try {
  			Connection conn = DatabaseInfo.openConnectSQL();
  			PreparedStatement ps = null;
  			ResultSet rs = null;
  			String createPersonQuery = "insert into Invoice (invoiceCode, storeId, customerId, salesPersonId, date) values (?,?,?,?,?);";
			ps = conn.prepareStatement(createPersonQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, invoiceCode);
			ps.setInt(2, storeId);
			ps.setInt(3, customerId);
			ps.setInt(4, salesPersonId);
			ps.setString(5, invoiceDate);
			ps.executeUpdate();
			DatabaseInfo.closeConnection(conn, ps, rs);
  			}catch (SQLException e) {
  			System.out.println("SQLException: ");
  			e.printStackTrace();
  			throw new RuntimeException(e);
  			}
		} else {
			throw new IllegalArgumentException("Store already exsists.");
		}
	}

	/**
	 * Adds a particular product (identified by <code>itemCode</code>)
	 * to a particular invoice (identified by <code>invoiceCode</code>) with the
	 * specified quantity.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param quantity
	 */
	public static void addProductToInvoice(String invoiceCode, String itemCode, int quantity) {
    //TODO: implement

	}

	/**
	 * Adds a particular equipment <i>purchase</i> (identified by <code>itemCode</code>) to a
	 * particular invoice (identified by <code>invoiceCode</code>) at the given <code>purchasePrice</code>.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param purchasePrice
	 */
	public static void addEquipmentToInvoice(String invoiceCode, String itemCode, double purchasePrice) {
    //TODO: implement

	}

	/**
	 * Adds a particular equipment <i>lease</i> (identified by <code>itemCode</code>) to a
	 * particular invoice (identified by <code>invoiceCode</code>) with the given 30-day
	 * <code>periodFee</code> and <code>beginDate/endDate</code>.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param amount
	 */
	public static void addEquipmentToInvoice(String invoiceCode, String itemCode, double periodFee, String beginDate, String endDate) {
    //TODO: implement

	}

	/**
	 * Adds a particular service (identified by <code>itemCode</code>) to a
	 * particular invoice (identified by <code>invoiceCode</code>) with the
	 * specified number of hours.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param billedHours
	 */
	public static void addServiceToInvoice(String invoiceCode, String itemCode, double billedHours) {
    //TODO: implement

	}

	public static void main(String[] args) {
		addPerson("aart0g", "Aaron", "Perkey", "Darth Vader Rd.", "coruscant", "NE", "CT7656", "US");
		DatabaseLoader.loadPersons();
		addEmail("aart0g", "captinrex@empiresucks.com");
		DatabaseLoader.loadPersons();
		addStore("3179aa", "aart0g", "Andermatt Rd.", "coruscant", "NE", "555555", "US");
		DatabaseLoader.loadStore();
		addInvoice("INV006", "3179aa", "aart0g", "89a8a1", "2030-04-04");
		DatabaseLoader.loadInvoice();
		System.out.println(DatabaseLoader.fullInvoices);

	}

}