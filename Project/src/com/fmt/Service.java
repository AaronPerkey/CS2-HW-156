package com.fmt;

/**
 * Models a service
 * @author kyleg
 *
 */
public class Service extends Item{

	
	private Double hourlyRate;
	private Double hoursBilled;


	@Override
	public Double getHoursBilled() {
		return hoursBilled;
	}
	
	public Service(String itemCode, String name, Double hourlyRate) {
		super(itemCode, name);
		this.hourlyRate = hourlyRate;
	}
	public Service(Service s, Double hoursBilled, String invoiceCode) {
		super(s.getItemCode(), s.getName(), invoiceCode);
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
