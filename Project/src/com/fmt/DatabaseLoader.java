package com.fmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This the main data loader and connector to the SQL database
 * 
 * @author KyleG and AaronP
 *
 */

public class DatabaseLoader {

	public static List<Person> fullPerson = new ArrayList<>();
	public static List<Store> fullStore = new ArrayList<>();
	public static List<Invoice> fullInvoices = new ArrayList<>();
	public static List<Item> fullItems = new ArrayList<>();
	public static List<Item> fullInvoiceItems = new ArrayList<>();

	private static final Logger LOGGER = LogManager.getLogger(DatabaseInfo.class);

	/**
	 * Loads a person from the sql database
	 * 
	 * @return List of people
	 */
	public static List<Person> loadPersons() {

		if (fullPerson.size() != 0) {
			return fullPerson;
		}

		Connection conn = DatabaseInfo.openConnectSQL();

		PreparedStatement ps = null;
		ResultSet rs = null;
		String personQuery = """
				Select Person.personId, Person.personCode, Person.lastName, Person.firstName,
					Address.addressId, Address.street, Address.city, Address.zip, State.state,
					Country.country from Person
					join Address on Address.addressId = Person.addressId
					join State on State.stateId = Address.stateId
					join Country on Country.countryId = Address.countryId
				    group by Person.personId;
				""";
		try {
			ps = conn.prepareStatement(personQuery);
			rs = ps.executeQuery();
			while (rs.next()) {
				Person e = null;
				String personCode = rs.getString("personCode");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				Integer personId = rs.getInt("personId");
				Integer addressId = rs.getInt("addressId");
				String street = rs.getString("Address.street");
				String city = rs.getString("Address.city");
				String zipCode = rs.getString("Address.zip");
				String state = rs.getString("State.state");
				String country = rs.getString("Country.country");

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
				Address a = new Address(addressId, street, city, zipCode, state, country);
				e = new Person(personId, personCode, firstName, lastName, a, email);
				fullPerson.add(e);
			}
		} catch (SQLException e) {
			System.out.println("SQLException: could not run person queries");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		DatabaseInfo.closeConnection(conn, ps, rs);
		LOGGER.info("Done loading persons");
		return fullPerson;

	}

	/**
	 * Loads a Item from the sql database
	 * 
	 * @return List of items
	 */
	public static List<Item> loadItem() {

		if (fullItems.size() != 0) {
			return fullItems;
		}

		Connection conn = DatabaseInfo.openConnectSQL();

		Item i = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String itemQuery = """
				select itemId, itemCode, typeOfSale, name, unit, model, price,
					hourlyRate from Item;
				""";
		try {
			ps = conn.prepareStatement(itemQuery);
			rs = ps.executeQuery();
			while (rs.next()) {
				Integer itemId = rs.getInt("itemId");
				String itemCode = rs.getString("itemCode");
				String typeOfSale = rs.getString("typeOfSale");
				String name = rs.getString("name");
				String unit = rs.getString("unit");
				String model = rs.getString("model");
				Double price = rs.getDouble("price");
				Double hourlyRate = rs.getDouble("hourlyRate");
				if (typeOfSale.equals("E")) {
					i = new Equipment(itemId, itemCode, name, model);
				}
				if (typeOfSale.equals("P")) {
					i = new Product(itemId, itemCode, name, unit, price);
				}
				if (typeOfSale.equals("S")) {
					i = new Service(itemId, itemCode, name, hourlyRate);
				}
				fullItems.add(i);
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		DatabaseInfo.closeConnection(conn, ps, rs);

		LOGGER.info("Done loading items");
		return fullItems;
	}

	public static Person getPerson(int personId) {

		List<Person> persons = loadPersons();
		Person person = null;
		for (Person dude : persons) {
			if (dude.getPersonId().equals(personId)) {
				person = dude;
			}
		}
		return person;
	}

	/**
	 * Loads a Store from the sql database
	 * 
	 * @return List of stores
	 */
	public static List<Store> loadStore() {

		if (fullStore.size() != 0) {
			return fullStore;
		}

		Person perosnConnection = null;
		Connection conn = DatabaseInfo.openConnectSQL();

		PreparedStatement ps = null;
		ResultSet rs = null;
		String storeQuery = """
				select Store.storeId, Store.storeCode, Store.managerId, Address.addressId,
					Address.street, Address.city, Address.zip, State.state,
					Country.country from Store
					join Address on Address.addressId = Store.addressId
					join State on State.stateId = Address.stateId
					join Country on Country.countryId = Address.countryId
					group by Store.storeId;
				""";
		try {
			ps = conn.prepareStatement(storeQuery);

			rs = ps.executeQuery();
			while (rs.next()) {
				Integer storeId = rs.getInt("Store.storeId");
				String storeCode = rs.getString("Store.storeCode");
				Integer managerId = rs.getInt("Store.managerId");
				Integer addressId = rs.getInt("Address.addressId");
				String street = rs.getString("Address.street");
				String city = rs.getString("Address.city");
				String zipCode = rs.getString("Address.zip");
				String state = rs.getString("State.state");
				String country = rs.getString("Country.country");
				perosnConnection = getPerson(managerId);
				Address a = new Address(addressId, street, city, zipCode, state, country);
				Store s = new Store(storeId, storeCode, perosnConnection, a);
				Store completeStore = getStoreInvoices(s);
				fullStore.add(completeStore);
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		DatabaseInfo.closeConnection(conn, ps, rs);

		LOGGER.info("Done loading stores");
		return fullStore;
	}

	/**
	 * Loads a Invoice from the sql database
	 * 
	 * @return List of invoices
	 */
	public static List<Invoice> loadInvoice() {

		if (fullInvoices.size() != 0) {
			return fullInvoices;
		}

		Integer invoiceId = 0;
		Person customerConnection;
		Person salesPerosnConnection;
		Connection conn = DatabaseInfo.openConnectSQL();

		PreparedStatement ps = null;
		ResultSet rs = null;
		String storeQuery = """
				select Invoice.invoiceId, Invoice.invoiceCode, Invoice.customerId,
					Invoice.salesPersonId, Store.storeCode,date from Store
					join Invoice on Invoice.storeId = Store.storeId;
				""";
		try {
			ps = conn.prepareStatement(storeQuery);

			rs = ps.executeQuery();
			while (rs.next()) {
				invoiceId = rs.getInt("invoiceId");
				String invoiceCode = rs.getString("invoiceCode");
				int customerId = rs.getInt("customerId");
				String storeCode = rs.getString("storeCode");
				int salespersonId = rs.getInt("salesPersonId");
				String datePurchased = rs.getString("date");
				customerConnection = getPerson(customerId);
				salesPerosnConnection = getPerson(salespersonId);

				Invoice i = new Invoice(invoiceId, invoiceCode, storeCode, customerConnection, salesPerosnConnection,
						datePurchased);
				Invoice completeInvoice = getListInvoiceItems(i);
				fullInvoices.add(completeInvoice);
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		DatabaseInfo.closeConnection(conn, ps, rs);

		LOGGER.info("done loading invoices");
		return fullInvoices;
	}

	/**
	 * Loads a InvoiceItems from the sql database
	 * 
	 * @return List of invoice items
	 */
	public static List<Item> loadInvoiceItems() {
		if (fullInvoiceItems.size() != 0) {
			return fullInvoiceItems;
		}

		Integer itemId = 0;
		String typeOfBuy = null;
		Double price = 0.0;
		Integer quantity = 0;
		Double hoursBilled = 0.0;
		LocalDate startDate = null;
		LocalDate endDate = null;
		String itemCode = null;
		String name = null;
		String model = null;
		String unit = null;
		Double hourlyRate = 0.0;
		Double unitPrice = 0.0;
		String invoiceCode = null;
		List<Item> item = loadItem();
		Connection conn = DatabaseInfo.openConnectSQL();

		PreparedStatement ps = null;
		ResultSet rs = null;
		String invoiceItemsQuery = """
				select InvoiceItems.itemId, Invoice.invoiceCode, InvoiceItems.typeOfBuy, InvoiceItems.price, Item.price as unitPrice,
				             InvoiceItems.quantity, InvoiceItems.hoursBilled, InvoiceItems.startDate, endDate from Invoice
				             join InvoiceItems on InvoiceItems.invoiceId = Invoice.invoiceId
				             join Item on Item.itemId = InvoiceItems.itemId;
					""";
		try {
			ps = conn.prepareStatement(invoiceItemsQuery);
			rs = ps.executeQuery();

			Item i = null;
			while (rs.next()) {
				itemId = rs.getInt("itemId");
				invoiceCode = rs.getString("invoiceCode");
				typeOfBuy = rs.getString("typeOfBuy");
				price = rs.getDouble("price");
				unitPrice = rs.getDouble("unitPrice");
				quantity = rs.getInt("quantity");
				hoursBilled = rs.getDouble("hoursBilled");
				String startDateS = rs.getString("startDate");
				String endDateS = rs.getString("endDate");

				for (Item x : item) {
					if (x.getItemId().equals(itemId)) {
						itemCode = x.getItemCode();
						name = x.getName();
						model = x.getModel();
						unit = x.getUnit();
						hourlyRate = x.getHourlyRate();
					}
				}

				if (typeOfBuy != null) {
					if (typeOfBuy.equals("P")) {

						i = new Purchase(itemId, itemCode, name, model, price, invoiceCode);
					} else if (typeOfBuy.equals("L")) {
						startDate = LocalDate.parse(startDateS);
						endDate = LocalDate.parse(endDateS);
						i = new Lease(itemId, itemCode, name, model, price, startDate, endDate, invoiceCode);
					}
				}

				if (hoursBilled.equals(0.0) && typeOfBuy == null) {
					Product i1 = new Product(itemId, itemCode, name, unit, unitPrice);
					i = new Product(i1, quantity, invoiceCode);
				} else if (hoursBilled != 0.0 && typeOfBuy == null) {
					Service i1 = new Service(itemId, itemCode, name, hourlyRate);
					i = new Service(i1, hoursBilled, invoiceCode);
				}
				fullInvoiceItems.add(i);
			}

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		DatabaseInfo.closeConnection(conn, ps, rs);

		LOGGER.info("Done loading invoiceItems");
		return fullInvoiceItems;
	}

	/**
	 * takes in a storeId, without its invoices, returns the store with the invoices
	 * 
	 * @return List of invoices of a certain store
	 */
	public static Store getStoreInvoices(Store store) {

		List<Invoice> invoiceList = new ArrayList<>();
		Invoice invoice = null;
		List<Invoice> invoices = loadInvoice();
		Store s = null;
		Connection conn = DatabaseInfo.openConnectSQL();

		PreparedStatement ps = null;
		ResultSet rs = null;
		String storeInvoicesQuery = """
				select Invoice.invoiceId from Store
					join Invoice on Invoice.storeId = Store.storeId
					where Store.storeId = ?;
				""";
		try {
			ps = conn.prepareStatement(storeInvoicesQuery);
			ps.setInt(1, store.getStoreId());
			rs = ps.executeQuery();
			while (rs.next()) {
				Integer invoiceId = rs.getInt("Invoice.invoiceId");
				for (Invoice recept : invoices) {
					if (recept.getInvoiceId().equals(invoiceId)) {
						invoice = recept;
					}	
				}
				
				invoiceList.add(invoice);

			}
			s = new Store(store, invoiceList);
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: could not run person queries");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		DatabaseInfo.closeConnection(conn, ps, rs);

		return s;
	}

	/**
	 * Connects items with there corresponding invoice
	 * 
	 * @return List of items of a certain invoice
	 */
	public static Invoice getListInvoiceItems(Invoice invoice) {

		Invoice s = null;
		List<Item> itemList = new ArrayList<>();
		Item item = null;
		List<Item> items = loadInvoiceItems();
		Connection conn = DatabaseInfo.openConnectSQL();

		PreparedStatement ps = null;
		ResultSet rs = null;
		String itemInvoicesQuery = """
							select Item.itemId, InvoiceItems.endDate from Invoice
					join InvoiceItems on InvoiceItems.invoiceId = Invoice.invoiceId
					join Item on Item.itemId = InvoiceItems.itemId
				    where Invoice.invoiceId = ?;
				""";
		try {
			ps = conn.prepareStatement(itemInvoicesQuery);
			ps.setInt(1, invoice.getInvoiceId());
			rs = ps.executeQuery();
			while (rs.next()) {
				Integer itemId = rs.getInt("Item.itemId");
				String endDate = rs.getString("endDate");
				for (Item thing : items) {
					Integer itemIdf = thing.getItemId();
					String itemT = null;
					
					// cannot do .equals to a null value so skip it if they are null
					// then check separately with a == operator
					if (thing.getEndDate() != null) {
						itemT = thing.getEndDate().toString();
					}
					if ((itemT == null || endDate == null)) {
						if (itemIdf.equals(itemId) && itemT == endDate) {
							item = thing;
						}
					} else {
						if (itemT.equals(endDate)) {
							if (itemIdf.equals(itemId)) {
								item = thing;
							}
						}
					}
				}

				itemList.add(item);

			}
			s = new Invoice(invoice, itemList);

		}catch(

	SQLException e)
	{
		System.out.println("SQLException: could not run person queries");
		e.printStackTrace();
		throw new RuntimeException(e);
	}

	DatabaseInfo.closeConnection(conn,ps,rs);return s;
}

}