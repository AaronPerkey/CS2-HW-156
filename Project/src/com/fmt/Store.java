package com.fmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 
 * Models a store
 * 
 * @author Kyle Gann, Aaron Perkey
 * 
 */

public class Store {

	private Integer storeId;
	private String storeCode;
	private Person managerCode;
	private Address address;
	private List<Invoice> invoiceList;

	public Store(Integer storeId, String storeCode, Person managerCode, Address address) {
		super();
		this.storeId = storeId;
		this.storeCode = storeCode;
		this.managerCode = managerCode;
		this.address = address;
	}
	
	public Store(Store store, List<Invoice> invoiceList) {
		this.storeId = store.getStoreId();
		this.storeCode = store.getStoreCode();
		this.managerCode = store.getManagerCode();
		this.address = store.getAddress();
		this.invoiceList = invoiceList;
	}

	//For binary search
	public Store(String storeCode) {
		super();
		this.storeCode = storeCode;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public Person getManagerCode() {
		return managerCode;
	}

	public Address getAddress() {
		return address;
	}
	
	public List<Invoice> getInvoiceList() {
		return invoiceList;
	}
	
	public static Integer getStore(String storeCode) {
		Integer storeId = -1;
		try {
			Connection conn = DatabaseInfo.openConnectSQL();
			String query0 = "select storeId from Store where storeCode = ?;";
			PreparedStatement ps0 = null;
			ps0 = conn.prepareStatement(query0);
			ps0.setString(1, storeCode);
			ResultSet rs0 = ps0.executeQuery();
			if(rs0.next()) {
				storeId = rs0.getInt("storeId");
			}
			DatabaseInfo.closeConnection(conn, ps0, rs0);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return storeId;
	}
	
	public String toString () {
		// Writing the store summary report
		StringBuilder string = new StringBuilder();

		Double totalGrandTotal = 0.0;


			String store = this.getStoreCode();
			String manager = this.getManagerCode().getFullName();
			Double grandTotal = this.getGrandTotal();
			int sales = 0;
			List<Invoice> invoices = this.getInvoiceList();
			if (invoices != null ) {
				sales = invoices.size();
			}

			totalGrandTotal += grandTotal;
			
			string.append(String.format("%s %20s %15d         $%11.2f \n", store, manager, sales, grandTotal));

			return string.toString();
	}
	
	public Double getGrandTotal() {
		Double grandTotal = 0.0;
		if (this.getInvoiceList() != null) {
			for (int j = 0; j < this.getInvoiceList().size(); j++) {
				Invoice invoice = this.getInvoiceList().get(j);
				if (invoice != null) {
					grandTotal += invoice.getInvoiceTotal();
				}
			}
		}
		return grandTotal;
	}
	
//	
}