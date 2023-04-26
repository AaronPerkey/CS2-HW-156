package com.fmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * 
 * Models equipment
 * 
 * @author Kyle Gann, Aaron Perkey
 *
 */

public class Equipment extends Item{

	private String model;
	public Equipment(Integer itemId, String itemCode, String name, String model) {
		super(itemId, itemCode, name);
		this.model = model;
	}
	
	public Equipment(Integer itemId, String itemCode, String name, String invoiceCode, String model) {
		super(itemId, itemCode, name, invoiceCode);
		this.model = model;
	}
	
	public Equipment(String code) {
		super(code);
	}

	@Override
	public String getModel() {
		return model;
	}
	@Override
	public Double getFee() {
		return 0.0;
	}
	
	public Double getTaxRate() {
		return 0.0;
	}
	
	public Double getCost() {
		return 0.0;
	}
	
	public Double getUnitPrice() {
		return 0.0;
	}
	
	public LocalDate getStartDate() {
		return null;
	}
	
	public LocalDate getEndDate() {
		return null;
	}
	
	/**
	 * A method to get equippment based on its code
	 * @param code
	 * @return Integer
	 */
	public static Integer getEquippment(String code) {
		Integer itemId = -1;
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
		return itemId;
	}

}