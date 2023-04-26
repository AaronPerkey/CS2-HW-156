package com.fmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * Models a product
 * 
 * @author Kyle Gann, Aaron Perkey
 *
 */

public class Product extends Item{
	
	private String unit;
	private Double price;
	private int quantity;

	public Product(Integer itemId, String itemCode, String name, String unit, Double price) {
		super(itemId, itemCode, name);
		this.unit = unit;
		this.price = price;
	}

	public Product(Product p, int quanity, String invoiceCode) {
		super(p.getItemId(),p.getItemCode(), p.getName(), invoiceCode);
		this.unit = p.getUnit();
		this.price = p.getUnitPrice();
		this.quantity = quanity;
	}
	
	public Product(String code) {
		super(code);
	}

	@Override
	public String getUnit() {
		return unit;
	}
	@Override
	public Double getUnitPrice() {
		return price;
	}
	public Double getTax() {
		return price * 0.075;
	}
	
	@Override
	public int getQuantity() {
		return quantity;
	}
	
	@Override
	public Double getCost() {
		double cost = this.getUnitPrice() * this.getQuantity();
		return cost;
	}
	
	@Override
	public Double getTaxRate() {
		double taxRate =  0.0345;
		return taxRate;		
	}
	
	/**
	 * A method to get a product based on its code
	 * @param code
	 * @return Integer
	 */
	public static Integer getProduct(String code) {
		Integer itemId = -1;
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
		return itemId;
	}
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append(this.getItemCode() + "  ");
		
		String product = this.getName();
		
		string.append("   (Product)  " + product);
		Double productCost = this.getCost();
		
		string.append(String.format(
				"\n                       Quantity: %d                         $%10.2f \n",
				this.getQuantity(),productCost));
		
		return string.toString();
	}
	
}