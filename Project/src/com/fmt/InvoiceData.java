package com.fmt;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
		Connection conn = DatabaseInfo.openConnectSQL();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = """
				SET FOREIGN_KEY_CHECKS=0;
				Delete From Country;
				Delete From State;
				Delete From Address;
				Delete From Person;
				Delete From Email;
				Delete From Store;
				Delete From Invoice;
				Delete From Item;
				Delete From InvoiceItems;
				SET FOREIGN_KEY_CHECKS=1;
				""";
		try {
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		DatabaseLoader.fullItems = new ArrayList<>();
		DatabaseLoader.fullPerson = new ArrayList<>();
		DatabaseLoader.fullInvoices = new ArrayList<>();
		DatabaseLoader.fullStore = new ArrayList<>();
		DatabaseLoader.fullInvoiceItems = new ArrayList<>();
		DatabaseInfo.closeConnection(conn, ps, rs);

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

		Connection conn = DatabaseInfo.openConnectSQL();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String query0 = "select countryId from Country where country = ?;";
			ps = conn.prepareStatement(query0);
			ps.setString(1, country);
			rs = ps.executeQuery();
			if (rs.next()) {
				countryId = rs.getInt("countryId");
			}

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		if (countryId == -1) {

			try {
				String query = "insert into Country (country) values (?);";
				ps.close();
				rs = null;
				ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, country);
				ps.executeUpdate();
				// get the generated keys:
				rs = ps.getGeneratedKeys();
				rs.next();
				int key = rs.getInt(1);
				countryId = key;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		try {
			String query0 = "select stateId from State where state = ?;";
			ps.close();
			rs = null;
			ps = conn.prepareStatement(query0);
			ps.setString(1, state);
			rs = ps.executeQuery();
			if (rs.next()) {
				stateId = rs.getInt("stateId");
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		if (stateId == -1) {

			try {
				String query = "insert into State (state) values (?);";
				ps.close();
				rs = null;
				ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, state);
				ps.executeUpdate();
				// get the generated keys:
				ResultSet keys = ps.getGeneratedKeys();
				keys.next();
				int key = keys.getInt(1);
				stateId = key;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		try {
			String createPersonQuery = "insert into Address (street, city, zip, stateId, countryId) values (?,?,?,?,?);";
			ps.close();
			rs = null;
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
		} catch (SQLException e) {
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

	public static void addPerson(String personCode, String firstName, String lastName, String street, String city,
			String state, String zip, String country) {

		if (personCode == null || firstName == null || lastName == null || street == null || city == null
				|| state == null || zip == null || country == null) {
			throw new IllegalArgumentException("Invalid person data. (one of the veriables equal null)");
		}

		Integer personId = Person.getPersonId(personCode);

		if (personId == -1) {

			Integer addressId = getAddress(street, city, state, zip, country);
			Connection conn = DatabaseInfo.openConnectSQL();
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {

				String createPersonQuery = "insert into Person (personCode, lastName, firstName, addressId) values (?,?,?,?);";
				ps = conn.prepareStatement(createPersonQuery, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, personCode);
				ps.setString(2, lastName);
				ps.setString(3, firstName);
				ps.setInt(4, addressId);
				ps.executeUpdate();

				// get the generated keys:
				rs = ps.getGeneratedKeys();
				rs.next();
				int key = rs.getInt(1);
				addressId = key;
				DatabaseInfo.closeConnection(conn, ps, rs);
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			DatabaseLoader.fullPerson = new ArrayList<>();
			DatabaseInfo.closeConnection(conn, ps, rs);
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

		Integer personId = Person.getPersonId(personCode);

		if (personId == -1) {
			throw new IllegalArgumentException("This person does not exist in the database.");
		}

		Integer emailId = -1;
		Connection conn = DatabaseInfo.openConnectSQL();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String query0 = "Select emailId from Email WHERE address = ?;";
			ps = conn.prepareStatement(query0);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				emailId = rs.getInt("emailId");
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		if (emailId == -1) {
			try {
				ps.close();
				rs = null;
				String createPersonQuery = "insert into Email (personId, address) values (?,?);";
				ps = conn.prepareStatement(createPersonQuery, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, personId);
				ps.setString(2, email);
				ps.executeUpdate();

			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			DatabaseInfo.closeConnection(conn, ps, rs);
			DatabaseLoader.fullPerson = new ArrayList<>();
		}
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

		if (storeCode == null || managerCode == null || street == null || city == null || state == null || zip == null
				|| country == null) {
			throw new IllegalArgumentException("Invalid store data. (one of the veriables equal null)");
		}

		Integer storeId = Store.getStore(storeCode);

		if (storeId == -1) {

			Integer managerId = Person.getPersonId(managerCode);

			if (managerId == -1) {
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
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			DatabaseLoader.fullStore = new ArrayList<>();
		}
	}

	/**
	 * Adds a product record to the database with the given <code>code</code>,
	 * <code>name</code> and <code>unit</code> and <code>pricePerUnit</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param unit
	 * @param pricePerUnit
	 */
	public static void addProduct(String code, String name, String unit, double pricePerUnit) {
		if (code == null || name == null || unit == null) {
			throw new IllegalArgumentException("Invalid arugment");
		}
		Connection conn = DatabaseInfo.openConnectSQL();
		PreparedStatement ps = null;
		ResultSet rs = null;

		Integer itemId = Product.getProduct(code);
		if (itemId == -1) {
			String query = "insert into Item (itemCode, typeOfSale, name, unit, model, price, hourlyRate) values (?,?,?,?,?,?,?);";

			try {
				ps = conn.prepareStatement(query);

				ps.setString(1, code);
				ps.setString(2, "P");
				ps.setString(3, name);
				ps.setString(4, unit);
				ps.setString(5, null);
				ps.setDouble(6, pricePerUnit);
				ps.setString(7, null);

				ps.executeUpdate();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		DatabaseLoader.fullItems = new ArrayList<>();
		DatabaseInfo.closeConnection(conn, ps, rs);
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
		
		if (code == null || name == null || modelNumber == null) {
			throw new IllegalArgumentException("Invalid arugment");
		}
		Connection conn = DatabaseInfo.openConnectSQL();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer itemId = Equipment.getEquippment(code);
		if (itemId == -1) {
			String query = "insert into Item (itemCode, typeOfSale, name, unit, model, price, hourlyRate) values (?,?,?,?,?,?,?);";

			try {
				ps = conn.prepareStatement(query);

				ps.setString(1, code);
				ps.setString(2, "E");
				ps.setString(3, name);
				ps.setString(4, null);
				ps.setString(5, modelNumber);
				ps.setString(6, null);
				ps.setString(7, null);

				ps.executeUpdate();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		DatabaseLoader.fullItems = new ArrayList<>();
		DatabaseInfo.closeConnection(conn, ps, rs);
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
		if (code == null || name == null) {
			throw new IllegalArgumentException("Invalid arugment");
		}
		Connection conn = DatabaseInfo.openConnectSQL();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer itemId = Service.getService(code);
		try {

			String query0 = "SELECT itemId, itemCode, name, unit, price FROM Item WHERE typeOfSale = ? and itemCode = ?;";
			ps = conn.prepareStatement(query0);
			ps.setString(1, "S");
			ps.setString(2, code);
			rs = ps.executeQuery();
			if (rs.next()) {
				itemId = rs.getInt("itemId");
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		if (itemId == -1) {
			String query = "insert into Item (itemCode, typeOfSale, name, unit, model, price, hourlyRate) values (?,?,?,?,?,?,?);";

			try {
				ps.close();
				ps = conn.prepareStatement(query);

				ps.setString(1, code);
				ps.setString(2, "S");
				ps.setString(3, name);
				ps.setString(4, null);
				ps.setString(5, null);
				ps.setString(6, null);
				ps.setDouble(7, costPerHour);

				ps.executeUpdate();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		DatabaseLoader.fullItems = new ArrayList<>();
		DatabaseInfo.closeConnection(conn, ps, rs);
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
	public static void addInvoice(String invoiceCode, String storeCode, String customerCode, String salesPersonCode,
			String invoiceDate) {

		if (invoiceCode == null || storeCode == null || customerCode == null || salesPersonCode == null
				|| invoiceDate == null) {
			throw new IllegalArgumentException("Invalid invoice data. (one of the veriables equal null)");
		}

		Integer invoiceId = Invoice.getInvoice(invoiceCode);

		if (invoiceId == -1) {

			Integer storeId = Store.getStore(storeCode);
			Integer salesPersonId = Person.getPersonId(salesPersonCode);
			Integer customerId = Person.getPersonId(customerCode);

			if (storeId == -1) {
				throw new IllegalArgumentException("Store does not exsist.");
			}
			if (salesPersonId == -1 || customerId == -1) {
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
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			DatabaseLoader.fullInvoices = new ArrayList<>();
		}
	}

	/**
	 * Adds a particular product (identified by <code>itemCode</code>) to a
	 * particular invoice (identified by <code>invoiceCode</code>) with the
	 * specified quantity.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param quantity
	 */
	public static void addProductToInvoice(String invoiceCode, String itemCode, int quantity) {
		if (invoiceCode == null || itemCode == null) {
			throw new IllegalArgumentException("Invalid arugment");
		}
		Integer invoiceItemId = -1;
		Connection conn = DatabaseInfo.openConnectSQL();
		PreparedStatement ps = null;
		ResultSet rs = null;

		Integer invoiceId = Invoice.getInvoice(invoiceCode);
		if (invoiceId == -1) {
			throw new IllegalArgumentException("Invoice does not exist.");
		}

		Integer itemId = Product.getProduct(itemCode);
		if (itemId == -1) {
			throw new IllegalArgumentException("Item does not exist.");
		}

		try {
			String query0 = "Select invoiceItemsId from InvoiceItems \r\n"
					+ "join Item on Item.itemId = InvoiceItems.itemId\r\n"
					+ "join Invoice on Invoice.invoiceId = InvoiceItems.InvoiceId\r\n"
					+ "WHERE Item.itemCode = ? and Invoice.invoiceCode = ?;";
			ps = conn.prepareStatement(query0);
			ps.setString(1, itemCode);
			ps.setString(2, invoiceCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				invoiceItemId = rs.getInt("invoiceItemsId");
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		if (invoiceItemId == -1) {
			String query = "insert into InvoiceItems (invoiceId, itemId, typeOfBuy, price, quantity, hoursBilled, startDate, endDate) values (?,?,?,?,?,?,?,?);";

			try {
				ps.close();
				ps = conn.prepareStatement(query);

				ps.setInt(1, invoiceId);
				ps.setInt(2, itemId);
				ps.setString(3, null);
				ps.setString(4, null);
				ps.setDouble(5, quantity);
				ps.setString(6, null);
				ps.setString(7, null);
				ps.setString(8, null);

				ps.executeUpdate();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else {
			// if product is already there, add more quantity
			double currentQuantity = 0.0;
			String query0 = "Select quantity from InvoiceItems WHERE invoiceId = ? and itemId = ?;";
			try {
				
				ps.close();
				ps = conn.prepareStatement(query0);

				ps.setInt(1, invoiceId);
				ps.setInt(2, itemId);

				rs = ps.executeQuery();
				if (rs.next()) {
					currentQuantity = rs.getInt("quantity");
				}
			} catch (SQLException e1) {

				e1.printStackTrace();
			}

			String query = "update InvoiceItems Set quantity = ? Where invoiceId = ? and itemId = ?;";
			try {
				ps.close();
				ps = conn.prepareStatement(query);

				ps.setDouble(1, currentQuantity + quantity);
				ps.setInt(2, invoiceId);
				ps.setInt(3, itemId);

				ps.executeUpdate();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
		DatabaseLoader.fullInvoiceItems = new ArrayList<>();
		DatabaseInfo.closeConnection(conn, ps, rs);
	}

	/**
	 * Adds a particular equipment <i>purchase</i> (identified by
	 * <code>itemCode</code>) to a particular invoice (identified by
	 * <code>invoiceCode</code>) at the given <code>purchasePrice</code>.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param purchasePrice
	 */
	public static void addEquipmentToInvoice(String invoiceCode, String itemCode, double purchasePrice) {
		if (invoiceCode == null || itemCode == null) {
			throw new IllegalArgumentException("Invalid arugment");
		}
		Integer invoiceItemId = -1;
		Connection conn = DatabaseInfo.openConnectSQL();
		PreparedStatement ps = null;
		ResultSet rs = null;

		Integer invoiceId = Invoice.getInvoice(invoiceCode);
		if (invoiceId == -1) {
			throw new IllegalArgumentException("Invoice does not exist.");
		}

		Integer itemId = Equipment.getEquippment(itemCode);
		if (itemId == -1) {
			throw new IllegalArgumentException("Item does not exist.");
		}

		try {
			String query0 = "Select invoiceItemsId from InvoiceItems \r\n"
					+ "join Item on Item.itemId = InvoiceItems.itemId\r\n"
					+ "join Invoice on Invoice.invoiceId = InvoiceItems.InvoiceId\r\n"
					+ "WHERE Item.itemCode = ? and Invoice.invoiceCode = ?;";
			ps = conn.prepareStatement(query0);
			ps.setString(1, itemCode);
			ps.setString(2, invoiceCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				invoiceItemId = rs.getInt("invoiceItemsId");
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		if (invoiceItemId == -1) {
			String query = "insert into InvoiceItems (invoiceId, itemId, typeOfBuy, price, quantity, hoursBilled, startDate, endDate) values (?,?,?,?,?,?,?,?);";

			try {
				ps.close();
				ps = conn.prepareStatement(query);

				ps.setInt(1, invoiceId);
				ps.setInt(2, itemId);
				ps.setString(3, "P");
				ps.setDouble(4, purchasePrice);
				ps.setString(5, null);
				ps.setString(6, null);
				ps.setString(7, null);
				ps.setString(8, null);

				ps.executeUpdate();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		DatabaseLoader.fullInvoiceItems = new ArrayList<>();
		DatabaseInfo.closeConnection(conn, ps, rs);
	}

	/**
	 * Adds a particular equipment <i>lease</i> (identified by
	 * <code>itemCode</code>) to a particular invoice (identified by
	 * <code>invoiceCode</code>) with the given 30-day <code>periodFee</code> and
	 * <code>beginDate/endDate</code>.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param amount
	 */
	public static void addEquipmentToInvoice(String invoiceCode, String itemCode, double periodFee, String beginDate,
			String endDate) {
		if (invoiceCode == null || itemCode == null) {
			throw new IllegalArgumentException("Invalid arugment");
		}
		Integer invoiceItemId = -1;
		Connection conn = DatabaseInfo.openConnectSQL();
		PreparedStatement ps = null;
		ResultSet rs = null;

		Integer invoiceId = Invoice.getInvoice(invoiceCode);
		if (invoiceId == -1) {
			throw new IllegalArgumentException("Invoice does not exist.");
		}

		Integer itemId = Equipment.getEquippment(itemCode);
		if (itemId == -1) {
			throw new IllegalArgumentException("Item does not exist.");
		}

		try {
			String query0 = "Select invoiceItemsId from InvoiceItems \r\n"
					+ "join Item on Item.itemId = InvoiceItems.itemId\r\n"
					+ "join Invoice on Invoice.invoiceId = InvoiceItems.InvoiceId\r\n"
					+ "WHERE Item.itemCode = ? and Invoice.invoiceCode = ?;";
			ps = conn.prepareStatement(query0);
			ps.setString(1, itemCode);
			ps.setString(2, invoiceCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				invoiceItemId = rs.getInt("invoiceItemsId");
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		if (invoiceItemId == -1) {
			String query = "insert into InvoiceItems (invoiceId, itemId, typeOfBuy, price, quantity, hoursBilled, startDate, endDate) values (?,?,?,?,?,?,?,?);";

			try {
				ps.close();
				ps = conn.prepareStatement(query);

				ps.setInt(1, invoiceId);
				ps.setInt(2, itemId);
				ps.setString(3, "L");
				ps.setDouble(4, periodFee);
				ps.setString(5, null);
				ps.setString(6, null);
				ps.setString(7, beginDate);
				ps.setString(8, endDate);

				ps.executeUpdate();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		DatabaseLoader.fullInvoiceItems = new ArrayList<>();
		DatabaseInfo.closeConnection(conn, ps, rs);
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
		if (invoiceCode == null || itemCode == null) {
			throw new IllegalArgumentException("Invalid arugment");
		}
		Integer invoiceItemId = -1;
		Connection conn = DatabaseInfo.openConnectSQL();
		PreparedStatement ps = null;
		ResultSet rs = null;

		Integer invoiceId = Invoice.getInvoice(invoiceCode);
		if (invoiceId == -1) {
			throw new IllegalArgumentException("Invoice does not exist.");
		}

		Integer itemId = Service.getService(itemCode);
		if (itemId == -1) {
			throw new IllegalArgumentException("Item does not exist.");
		}

		try {
			String query0 = "Select invoiceItemsId from InvoiceItems \r\n"
					+ "join Item on Item.itemId = InvoiceItems.itemId\r\n"
					+ "join Invoice on Invoice.invoiceId = InvoiceItems.InvoiceId\r\n"
					+ "WHERE Item.itemCode = ? and Invoice.invoiceCode = ?;";
			ps = conn.prepareStatement(query0);
			ps.setString(1, itemCode);
			ps.setString(2, invoiceCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				invoiceItemId = rs.getInt("invoiceItemsId");
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		if (invoiceItemId == -1) {
			String query = "insert into InvoiceItems (invoiceId, itemId, typeOfBuy, price, quantity, hoursBilled, startDate, endDate) values (?,?,?,?,?,?,?,?);";

			try {
				ps.close();
				ps = conn.prepareStatement(query);

				ps.setInt(1, invoiceId);
				ps.setInt(2, itemId);
				ps.setString(3, null);
				ps.setString(4, null);
				ps.setString(5, null);
				ps.setDouble(6, billedHours);
				ps.setString(7, null);
				ps.setString(8, null);

				ps.executeUpdate();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		DatabaseLoader.fullInvoiceItems = new ArrayList<>();
		DatabaseInfo.closeConnection(conn, ps, rs);

	}
}