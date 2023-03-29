# Computer Science II
# Assignment 4 -  Project Phase III
# 
#This creates the qrery's from the FMT sales tables
#
# Name(s): Aaron, Kyle
# Date: 2023-03-22

# This query to retrieves a persons code and name.
select Person.personCode, Person.lastName, Person.firstName from Person;

# A query to retrieve the major fields for every person including their address.
select Person.personCode, Person.lastName, Person.firstName, Street.street, 
City.city, State.state, ZipCode.zipCode, Country.country 
from Person
join Address on Address.addressId = Person.addressId
join Street on Street.streetId = Address.streetId
join City on City.cityId = Address.cityId
join State on State.stateId = Address.stateId
join ZipCode on ZipCode.zipCodeId = Address.zipCodeId
join Country on Country.countryId = Address.countryId;

# A query to get the email addresses of a specific person.
select Email.address from Email where personId = 1;

# A query to change the email address of a specific email record.
update Email set address = "yougotmail@lol.com" where emailId = 1;

# These query's to remove a specific person record.
delete from Email where personId = 5;
delete from Person where personId = 5;

# A query to get all the items on a specific invoice record.
select Invoice.invoiceCode, Items.itemsCode, Items.itemName from Items
left join InvoiceItems on Items.itemsId = InvoiceItems.itemsId
left join Invoice on Invoice.invoiceId = InvoiceItems.invoiceId
where Invoice.invoiceId = 1;

# A query to get all the items purchased by a specific person.
select Person.firstName, Person.lastName, Items.itemName from Items
left join InvoiceItems on Items.itemsId = InvoiceItems.itemsId
left join Invoice on InvoiceItems.invoiceId = Invoice.invoiceId
left join Person on Invoice.customer = Person.personId
where Person.personId = 1;

# A query to find the total number of sales made at each store.
select Store.storeCode, count(InvoiceItems.itemsId) as SalesMade from InvoiceItems
left join Invoice on InvoiceItems.invoiceId = Invoice.invoiceId
left join Store on Store.storeId = Invoice.storeId
group by storeCode;

# A query to find the total number of sales made by each employee.
select Person.firstName, Person.lastName, Count(InvoiceItems.itemsId) as SalesMade from InvoiceItems
left join Invoice on InvoiceItems.invoiceId = Invoice.invoiceId
left join Person on Person.personId = Invoice.salesPerson
group by salesPerson;

# A query to find the subtotal charge of all products in each invoice.
select i.invoiceCode, round(sum(itm.unitPrice * ii.quantity),2) from Invoice i
left join InvoiceItems ii on i.invoiceId = ii.invoiceId
left join Items itm on ii.itemsId = itm.itemsId
group by i.invoiceId;

# A query to detect invalid data.
select Invoice.invoiceCode, Items.itemName from Items
join InvoiceItems on Items.itemsId = InvoiceItems.itemsId
join Invoice on Invoice.invoiceId = InvoiceItems.invoiceId
group by InvoiceItems.itemsId, InvoiceItems.invoiceId
having count(InvoiceItems.itemsId) > 1;



# A query to detect a potential instances of fraud.
select Invoice.invoiceCode, Person.lastName, Person.firstName from Items
join InvoiceItems on Items.itemsId = InvoiceItems.itemsId
join Invoice on Invoice.invoiceId = InvoiceItems.invoiceId
join Person on Person.personId = Invoice.customer
where Invoice.customer = Invoice.salesPerson;