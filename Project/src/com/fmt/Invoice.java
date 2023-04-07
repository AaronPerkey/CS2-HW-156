package com.fmt;





import java.util.List;

public class Invoice {

	private String invoiceCode;
	private List<Item> items;
	private String store;
	private Person customer;
	private Person salesperson;
	private String date;

	

	public Invoice(String invoiceCode, List<Item> items, String store, Person customer, Person salesperson,
			String date) {
		super();
		this.invoiceCode = invoiceCode;
		this.items = items;
		this.store = store;
		this.customer = customer;
		this.salesperson = salesperson;
		this.date = date;
	}

	public List<Item> getItem() {
		return items;
	}
	

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public Invoice(String store) {
		super();
		this.store = store;
	}

	public String getStore() {
		return store;
	}

	public Person getCustomer() {
		return customer;
	}

	public Person getSalesperson() {
		return salesperson;
	}

	public String getDate() {
		return date;
	}
	


public Double getInvoiceTotal() {
	if (this.getItem().size() > 0) {
		int numItems = this.getItem().size();
		Double total = 0.0;
		Double serviceCost = 0.0;
		Double productCost = 0.0;
		Double purchaseCost = 0.0;
		Double leaseCost = 0.0;
		Double sumTax = 0.0;
		for (int j = 0; j < numItems; j++) {
			// Service
			serviceCost += (this.getItem().get(j).getCost());
			sumTax += (serviceCost * this.getItem().get(j).getTaxRate());

			leaseCost += this.getItem().get(j).getFee();

			if (leaseCost > 10000 && leaseCost < 100000.0) {
				sumTax += 500.0;
			} else if (leaseCost > 100000) {
				sumTax += 1500.0;
			}
		}

		total += serviceCost + productCost + purchaseCost + leaseCost + sumTax;
		total = ((Math.round(total * 100.0)) / 100.0);
		return total;

	} else {
		// if they didn't buy anything the invoice is invalid
		return 0.0;
	}
}

public Double getCostWithNoTax() {
	if (this.getItem().size() > 0) {
		int numItems = this.getItem().size();
		Double total = 0.0;
		Double costs = 0.0;
		Double productCost = 0.0;
		Double purchaseCost = 0.0;
		Double leaseCost = 0.0;

		for (int j = 0; j < numItems; j++) {
			// Service
			costs += (this.getItem().get(j).getCost());

			leaseCost += (this.getItem().get(j).getFee());
		}

		total = costs + productCost + purchaseCost + leaseCost;
		total = ((Math.round(total * 100.0)) / 100.0);
		return total;

	} else {
		// if they didn't buy anything the invoice is invalid
		return 0.0;
	}
}

public Double getTax() {
	return this.getInvoiceTotal() - this.getCostWithNoTax();
}

public String toString () {
	StringBuilder string = new StringBuilder();
	//
	
	String invoiceCode = this.getInvoiceCode();
	string.append("\nInvoice  #" + invoiceCode);

		String storeCode = this.getStore();
		
		string.append("\nStore    #" + storeCode);
		

		String date = this.getDate();
		
		string.append("\nDate     " + date);

		// customer
		string.append("\nCustomer:\n" + this.getCustomer().toString());

		// salesperson
		string.append("\nSalesperson:\n" + this.getSalesperson().toString());
		
		string.append("\r\n" + "Item                                                               Total\r\n"
				+ "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-                          -=-=-=-=-=-\n");
		int numItems = this.getItem().size();
		for (int j = 0; j < numItems; j++) {
			string.append(this.getItem().get(j).toString());
		}
		
		string.append(String.format(
				"                                                             -=-=-=-=-=-\n"
						+ "                                                    Subtotal $ %.2f\r\n"
						+ "                                                         Tax $   %.2f\r\n"
						+ "                                                 Grand Total $ %.2f\n",
				this.getCostWithNoTax(), this.getTax(), this.getInvoiceTotal()));

	string.append("\n");

	return string.toString();
}


}
