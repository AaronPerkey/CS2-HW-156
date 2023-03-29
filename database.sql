# Computer Science II
# Assignment 4 -  Project Phase III
# 
#This creates tables and inserts data for FMT sales.
#
# Name(s): Aaron, Kyle
# Date: 2023-03-22

drop table if exists InvoiceItems;
drop table if exists Invoice;
drop table if exists Items;
drop table if exists Store;
drop table if exists Email;
drop table if exists Person;
drop table if exists Address;
drop table if exists Street;
drop table if exists ZipCode;
drop table if exists City;
drop table if exists State;
drop table if exists Country;

create table Country (
	countryId int not null primary key auto_increment,
    country varchar(100) unique
);

create table ZipCode (
	zipCodeId int not null primary key auto_increment,
    zipCode varchar(100) unique
);

create table State (
	stateId int not null primary key auto_increment,
	state varchar(100) unique
);

create table City (
	cityId int not null primary key auto_increment,
    city varchar(100) unique
);

create table Street (
	streetId int not null primary key auto_increment,
    street varchar(100) unique
);

create table Address (
	addressId int not null primary key auto_increment,
    streetId int not null,
    cityId int not null,
    stateId int not null,
    zipCodeId int not null,
    countryId int not null,
    foreign key (streetId) references Street(streetId),
    foreign key (cityId) references City(cityId),
    foreign key (stateId) references State(stateId),
    foreign key (zipCodeId) references ZipCode(zipCodeId),
    foreign key (countryId) references Country(countryId)
);

create table Person (
	personId int not null primary key auto_increment,
    personCode varchar(100) not null unique,
	lastName varchar(100) not null,
    firstName varchar(100) not null,
    addressId int not null,
    foreign key (addressId) references Address(addressId)
);

create table Email (
	emailId int not null primary key auto_increment,
    personId int not null,
    address varchar(100) unique,
    foreign key (personId) references Person(personId)
);

create table Store (
	storeId int not null primary key auto_increment,
	storeCode varchar(100) not null unique,
    manager int not null,
    addressId int not null not null,
    foreign key (manager) references Person(personId),
    foreign key (addressId) references Address(addressId)
);

    create table Invoice (
    invoiceId int not null primary key auto_increment,
	invoiceCode varchar(100) not null unique,
    storeId int not null,
    customer int not null,
    salesPerson int not null,
    date varchar(100),
    foreign key (storeId) references Store(storeId),
    foreign key (customer) references Person(personId),
    foreign key (salesPerson) references Person(personId)
    );

create table Items (
	itemsId int not null primary key auto_increment,
    itemsCode varchar(100) not null unique,
    type varchar(1) not null,
    itemName varchar(200) not null,
    unit varchar(100),
    model varchar(100),
    unitPrice float,
    hourlyRate float
);

create table InvoiceItems(
	invoiceItemsId int not null primary key auto_increment,
	invoiceId int not null,
    itemsId int not null,
    typeOfBuy varchar(100),
	price float,
    quantity varchar(100),
    hoursBilled varchar(100),
    startDate varchar(100),
    endDate varchar(100),
    foreign key (invoiceId) references Invoice(invoiceId),
    foreign key (itemsId) references Items(itemsId)
    );

insert into Country (country) values 
('United States'), 
('Canada'), 
('Mexico'), 
('United Kingdom');

insert into ZipCode (zipCode) values 
('10001'), 
('12345'), 
('45050'), 
('67892');

insert into State (state) values 
('Texas'), 
('Nebraska'), 
('Arkansas'), 
('Georga');

insert into City (city) values 
('Dallas'), 
('Lincoln'), 
('Jonesboro'), 
('Atlanta');

insert into Street (street) values 
('Broadway'), 
('Yonge Street'), 
('Chapultepec'), 
('Oxford Street');

insert into Address (streetId, cityId, stateId, zipCodeId, countryId) values 
(1, 1, 1, 1, 1), 
(2, 2, 2, 2, 2), 
(3, 3, 3, 3, 3), 
(4, 4, 4, 4, 4);

insert into Person (personCode, lastName, firstName, addressId) values 
('P001', 'Smith', 'John', 1), 
('P002', 'Garcia', 'Maria', 2), 
('P003', 'Lee', 'David', 3), 
('P004', 'Patel', 'Priya', 4);

insert into Email (personId, address) values 
(1, 'john.smith@example.com'), 
(1, 'bigfoot@hotmail.com'),
(2, 'maria.garcia@example.com'), 
(3, 'david.lee@example.com'), 
(4, 'priya.patel@example.com');

insert into Store (storeCode, manager, addressId) values 
('S001', 1, 1), 
('S002', 2, 2), 
('S003', 3, 3), 
('S004', 4, 4);

insert into Invoice (invoiceCode, storeId, customer, salesPerson, date) values 
('INV001', 1, 2, 3, '2022-01-01'), 
('INV002', 2, 3, 4, '2022-02-01'), 
('INV003', 3, 4, 1, '2022-03-01'), 
('INV004', 4, 1, 2, '2022-04-01'),
('INV005', 3, 1, 1, '2022-05-01');

insert into Items (itemsCode, type, itemName, unit, model, unitPrice, hourlyRate) values 
('ITM001', 'P', 'Haybale', 'bale', NULL, 10.99, NULL), 
('ITM002', 'P', 'Fertilizer', 'liter', NULL, 25.99, NULL), 
('ITM003', 'S', 'Plowing snow', NULL, NULL, NULL, 75.00), 
('ITM004', 'S', 'Mowing lawn', NULL, NULL, NULL, 100.00),
('ITM005', 'E', 'F150', NULL, 'Lightning', 	NULL, NULL),
('ITM006', 'E', 'Jeep Wrangler', NULL, 'Sahara', NULL, NULL);

insert into InvoiceItems (invoiceId, itemsId, typeOfBuy, price, quantity, hoursBilled, startDate, endDate) values 
(1, 1, NULL, NULL, 3, NULL, NULL, NULL),
(2, 2, NULL, NULL, 2, NULL, NULL, NULL),
(2, 5, 'P', 40000.00, NULL, NULL, NULL, NULL),
(2, 6, 'P', 50000.00, NULL, NULL, NULL, NULL), 
(4, 3, NULL, NULL, NULL, 24, NULL, NULL),
(4, 4, NULL, NULL, NULL, 36, NULL, NULL),
(5, 3, NULL, NULL, NULL, 24, NULL, NULL);
