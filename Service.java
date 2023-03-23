package com.fmt;

public class Service extends Item{

	
	private Double hourlyRate;
	private Double hoursBilled;


	@Override
	public Double getHoursBilled() {
		return hoursBilled;
	}
	
	public Service(String code, String itemCode, Double hourlyRate) {
		super(code, itemCode);
		this.hourlyRate = hourlyRate;
	}
	public Service(Service s, Double hoursBilled) {
		super(s.getCode(), s.getItemCode());
		this.hourlyRate = s.hourlyRate;
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
		double subtotal = hourlyRate * hoursBilled;
		return subtotal;
	}
	
	@Override
	public Double getTaxRate() {
		double tax = 0.0345;
		return tax;
	}
	
	
}
