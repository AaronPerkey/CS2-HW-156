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
	
	
//
//	public Double getCost() {
//		if (this.getInvoiceCode().size() > 0) {
//			int numItems = this.getInvoiceCode().size();
//			Double total = 0.0;
//			Double serviceCost = 0.0;
//			Double productCost = 0.0;
//			Double purchaseCost = 0.0;
//			Double leaseCost = 0.0;
//			Double sumTax = 0.0;
//			for (int j = 0; j < numItems; j++) {
//				// Service
//				serviceCost += (this.getInvoiceCode().get(j).getItemCode().getHourlyRate()
//						* this.getInvoiceCode().get(j).getHoursBilled());
//				sumTax += (serviceCost * 0.0345);
//				// Product
//				productCost += (this.getInvoiceCode().get(j).getItemCode().getPrice()
//						* this.getInvoiceCode().get(j).getQuantity());
//				sumTax += (productCost * 0.075);
//				// Purchase
//				purchaseCost += (this.getInvoiceCode().get(j).getPrice());
//				// Lease
//				int timeUsed = DataConverter.convertDates(this.getInvoiceCode().get(j).getStartDate(),
//						this.getInvoiceCode().get(j).getEndDate());
//				leaseCost += (this.getInvoiceCode().get(j).getFee() * (timeUsed / 30.0));
//
//				if (leaseCost > 10000 && leaseCost < 100000.0) {
//					sumTax += 500.0;
//				} else if (leaseCost > 100000) {
//					sumTax += 1500.0;
//				}
//			}
//
//			total += serviceCost + productCost + purchaseCost + leaseCost + sumTax;
//			total = ((Math.round(total * 100.0)) / 100.0);
//			return total;
//
//		} else {
//			// if they didn't buy anything the invoice is invalid
//			return 0.0;
//		}
//	}
//
//	public Double getCostWithNoTax() {
//		if (this.getInvoiceCode().size() > 0) {
//			int numItems = this.getInvoiceCode().size();
//			Double total = 0.0;
//			Double serviceCost = 0.0;
//			Double productCost = 0.0;
//			Double purchaseCost = 0.0;
//			Double leaseCost = 0.0;
//
//			for (int j = 0; j < numItems; j++) {
//				// Service
//				serviceCost += (this.getInvoiceCode().get(j).getItemCode().getHourlyRate()
//						* this.getInvoiceCode().get(j).getHoursBilled());
//
//				// Product
//				productCost += (this.getInvoiceCode().get(j).getItemCode().getPrice()
//						* this.getInvoiceCode().get(j).getQuantity());
//
//				// Purchase
//				purchaseCost += (this.getInvoiceCode().get(j).getPrice());
//				// Lease
//				int timeUsed = DataConverter.convertDates(this.getInvoiceCode().get(j).getStartDate(),
//						this.getInvoiceCode().get(j).getEndDate());
//				leaseCost += (this.getInvoiceCode().get(j).getFee() * (timeUsed / 30.0));
//
//				if (leaseCost > 10000 && leaseCost < 100000.0) {
//
//				} else if (leaseCost > 100000) {
//
//				}
//			}
//
//			total += serviceCost + productCost + purchaseCost + leaseCost;
//			total = ((Math.round(total * 100.0)) / 100.0);
//			return total;
//
//		} else {
//			// if they didn't buy anything the invoice is invalid
//			return 0.0;
//		}
//	}
//
//	public Double getTax() {
//		return this.getCost() - this.getCostWithNoTax();
//	}

}
