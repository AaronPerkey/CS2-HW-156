package com.fmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * Models a service
 *
 * @author Kyle Gann, Aaron Perkey
 *
 */

public class Service extends Item{

	private Double hourlyRate;
	private Double hoursBilled;

	public Service(Integer itemId, String itemCode, String name, Double hourlyRate) {
		super(itemId, itemCode, name);
		this.hourlyRate = hourlyRate;
	}
	
	public Service(Service s, Double hoursBilled, String invoiceCode) {
		super(s.getItemId(), s.getItemCode(), s.getName(), invoiceCode);
		this.hourlyRate = s.getHourlyRate();
		this.hoursBilled = hoursBilled;
		
	}
	
	public Service(String code) {
		super(code);
	}
	
	@Override
	public Double getHourlyRate() {
		return hourlyRate;
	}
	
	@Override
	public Double getCost() {
		double subtotal = this.hourlyRate * this.hoursBilled;
		return subtotal;
	}
	
	@Override
	public Double getTaxRate() {
		double tax = 0.0345;
		return tax;
	}
	
	@Override
	public Double getHoursBilled() {
		return hoursBilled;
	}
	/**
	 * A method to get a service based on its code
	 * @param code
	 * @return Integer
	 */
	public static Integer getService(String code) {
		Connection conn = DatabaseInfo.openConnectSQL();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer itemId = -1;
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
		return itemId;
	}
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append(this.getItemCode() + "  ");

		Double hours = 0.0;
		if (this.getHoursBilled() != null) {
			hours = this.getHoursBilled();
		}
		Double serviceCost = (this.getHourlyRate()
				* hours);
		
		String product = this.getName();
		
		string.append("   (Service)  " + product);

		string.append(String.format("\n       %.2f Hours @ $%.1f / Hour"
				+ "\n                                                             $%10.2f \n",
				hours,
				this.getHourlyRate(),
				serviceCost));
		return string.toString();
	}
	
}