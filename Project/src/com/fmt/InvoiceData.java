package com.fmt;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		// TODO: implement kyle do this

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
		// TODO: implement

	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 *
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		// TODO: implement

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
		// TODO: implement

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
		Integer itemId = -1;
		if (code == null || name == null || unit == null) {
			throw new IllegalArgumentException("Invalid arugment");
		}
		Connection conn = DatabaseInfo.openConnectSQL();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String query0 = "SELECT itemId, itemCode, name, unit, price FROM Item WHERE typeOfSale = ? and itemCode = ?;";
			ps = conn.prepareStatement(query0);
			ps.setString(1, "P");
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
				ps = null;
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
				// TODO Auto-generated catch block
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
		Integer itemId = -1;
		if (code == null || name == null || modelNumber == null) {
			throw new IllegalArgumentException("Invalid arugment");
		}
		Connection conn = DatabaseInfo.openConnectSQL();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String query0 = "SELECT itemId, itemCode, name, unit, price FROM Item WHERE typeOfSale = ? and itemCode = ?;";
			ps = conn.prepareStatement(query0);
			ps.setString(1, "E");
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
				ps = null;
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
				// TODO Auto-generated catch block
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
		Integer itemId = -1;
		if (code == null || name == null) {
			throw new IllegalArgumentException("Invalid arugment");
		}
		Connection conn = DatabaseInfo.openConnectSQL();
		PreparedStatement ps = null;
		ResultSet rs = null;
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
				ps = null;
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
				// TODO Auto-generated catch block
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
		// TODO: implement

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
		// TODO: implement

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
		// TODO: implement

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
		// TODO: implement

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
		// TODO: implement

	}
	
	public static void main(String[] args) {
	addProduct("thormp0", "Thomper", "Tool", 1000.0);
	addEquipment("equil0", "Gargoyle statue,", "Super scary XL");
	addService("lo0p43", "Shave you bald", 0.5);
	}

}