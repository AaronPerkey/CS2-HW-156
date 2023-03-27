# TODO Delete before turnin
use aperkey;

# Computer Science II
# Assignment 4 -  Project Phase III
# 
#This stores FMT Invoices data
#
# Name(s): Aaron, Kyle
#
#
#
# Part 3.2

-- May need to delete before turn in this is to reduce duplacate tables
-- --------------------------------------------------------------------------------------------------------------------------------------------------------------
drop table if exists InvoiceItems;
drop table if exists Invoice;
drop table if exists Items;
drop table if exists Store;
drop table if exists Person;
drop table if exists Email;
drop table if exists Address;
drop table if exists Street;
drop table if exists ZipCode;
drop table if exists City;
drop table if exists State;
drop table if exists Country;
-- --------------------------------------------------------------------------------------------------------------------------------------------------------------

#tables
-- we may need to change some variable names to match yours 
-- (only one I think we sould keep is "invoiceCode" to make it less confusing in our program)

create table Country (
	countryId int not null primary key auto_increment,
    country varchar(100)
);

create table State (
	stateId int not null primary key auto_increment,
	state varchar(100),
    countryId int not null,
    foreign key (countryId) references Country(countryId)
);

create table City (
	cityId int not null primary key auto_increment,
    city varchar(100),
    stateId int not null,
    foreign key (stateId) references State(stateId)
);

create table ZipCode (
	zipCodeId int not null primary key auto_increment,
    zipCode varchar(100),
    cityId int not null,
    foreign key (cityId) references City(cityId)
);

create table Street (
	streetId int not null primary key auto_increment,
    street varchar(100),
    zipCodeId int not null,
    foreign key (zipCodeId) references ZipCode(zipCodeId)
);

create table Address (
	addressId int not null primary key auto_increment,
    streetId int not null,
    foreign key (streetId) references Street(streetId)
);

create table Email (
	emailId int not null primary key auto_increment,
    email varchar(100)
);

create table Person (
	personId int not null primary key auto_increment,
    personCode varchar(100) not null,
    firstName varchar(100) not null,
    lastName varchar(100) not null,
    addressId int not null,
    emailId int not null,
    foreign key (addressId) references Address(addressId),
    foreign key (emailId) references Email(emailId)
);

create table Store (
	storeId int not null primary key auto_increment,
	storeCode varchar(100) not null,
    addressId int not null not null,
    foreign key (addressId) references Address(addressId)
);

    create table Invoice (
	invoiceId int not null primary key auto_increment,
    invoiceCode varchar(100),
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
    itemsCode varchar(100) not null,
    type varchar(1) not null,
    itemName varchar(200) not null,
    unit varchar(100) not null,
    price int not null,
    hourlyRate int not null,
    model varchar(100) not null,
    unitPrice varchar(100) -- TODO may need to remove
);


create table InvoiceItems(
	invoiceItemsId int not null primary key auto_increment,
    quantity varchar(100) not null,
    hoursBilled varchar(100) not null,
    typeOfBuy varchar(100) not null,
    fee int not null,
    price int not null,
    startDate varchar(100) not null,
    endDate varchar(100) not null,
    invoiceId int not null,
    itemsId int not null,
    foreign key (invoiceId) references Invoice(invoiceId),
    foreign key (itemsId) references Items(itemsId)
    );
