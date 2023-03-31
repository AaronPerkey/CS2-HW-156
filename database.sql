# Computer Science II
# Assignment 4 -  Project Phase III
# 
#This creates tables and inserts data for FMT sales.
#
# Name(s): Aaron Perkey, Kyle Gann
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
('US');

insert into ZipCode (zipCode) values 
('68508'), ('68117'), ('61234'), ('59023'),('68901'), ('67230'), ('31405'), 
('44177'), ('47306'), ('36628'), ('55551'), ('24040'), ('93907'), ('75074'), 
('93786'), ('21405'), ('80525'),('43656');

insert into State (state) values 
('NE'), ('CA'), ('KS'), ('GA'),
('OH'), ('IN'), ('AL'), ('MN'),
('VA'), ('ND'), ('MD'), ('CO');

insert into City (city) values 
('Lincoln'), ('Omaha'), ('Mead'), ('Lead North Store'), ('Hastings'), ('Wichita'),
('Savannah'), ('Cleveland'), ('Muncie'), ('Mobile'), ('Young America'), ('Roanoke'),
('Salinas'), ('Gravity Falls'), ('Fresno'), ('Annapolis'), ('Fort Collins'), ('Toledo');

insert into Street (street) values 
('123 Cornhusker Way'), ('4242 A Street'), ('RR9901'), ('OR5911'), ('452 13th Street'), ('180 Magdeline Plaza'),
('601 Lotheville Road,Savannah'), ('0 Havey Avenue,Cleveland'), ('65337 Straubel Place'), ('81 Northport Avenue'), 
('7 Sunbrook Center'), ('33 Lake View Alley'),('6811 Pleasure Point'), ('37 Hoard Circle'), ('40 Aberg Avenue'), 
('5595 Saint Paul Place'), ('3 5th Junction'), ('427 Nevada Pass');

insert into Address (streetId, cityId, stateId, zipCodeId, countryId) values 
(1, 1, 1, 1, 1), (2, 2, 1, 2, 1), (3, 3, 1, 3, 1),(4, 4, 2, 4, 1),(5, 5, 1, 5, 1),(6, 6, 3, 6, 1),
(7, 7, 4, 7, 1),(8, 8, 5, 8, 1),(9, 9, 6, 9, 1),(10, 10, 7, 10, 1),(11, 11, 8, 11, 1),(12, 12, 9, 12, 1),
(13, 13, 2, 13, 1),(14, 14, 10, 14, 1),(15, 15, 2, 15, 1),(16, 16, 11, 16, 1),(17, 17, 12, 17, 1),(18, 18, 5, 18, 1);

insert into Person (personCode, lastName, firstName, addressId) values 
('fdc267', 'Gann', 'Kyle', 7), ('0375b6', 'Nonnah', 'Gerry', 8), ('aa887a', 'Arbuckle', 'John', 9), ('cefa4f', 'Hilbert', 'John', 10),
('48b5dc', 'Frants', 'Leaming', 11), ('529dbe', 'Bernadette', 'Stammers', 12), ('aef60b', 'Gianni', 'Uttley', 13), ('8197d3', 'Pines', 'Dipper', 14),
('f22d0b', 'Denny', 'Moonbeam', 15), ('d78da4', 'Keslie', 'Soloway', 16), ('99cdc9', 'Rice', 'Antoshin', 17), ('89a8a1', 'Portia', 'Pottie', 18);

insert into Email (personId, address) values 
(1, 'squarry0@reverbnation.com'), (1, 'squarry0@salon.com'),
(2, 'nmichelotti1@sbwire.com'), (2, 'nmichelotti1@vistaprint.com'), (2, 'nmichelotti1@gravatar.com'), (2, 'nmichelotti1@patch.com'),
(3, 'wleavold2@npr.org'), (3, 'wleavold2@craigslist.org'),
(4, 'zcullington3@engadget.com'), (4, 'zcullington3@wisc.edu'), (4, 'zcullington3@google.es'), (4, 'zcullington3@google.pl'),
(5, 'fleaming4@elpais.com'), (5, 'fleaming4@unblog.fr'), (5, 'fleaming4@hugedomains.com'),
(6, 'bstammers5@flickr.com'), (6, 'bstammers5@mapy.cz'), (6, 'bstammers5@cpanel.net'),
(7, 'guttley6@sogou.com'), (7, 'guttley6@linkedin.com'), (7, 'guttley6@latimes.com'),
(8, 'fhalse7@europa.eu'), (8, 'fhalse7@microsoft.com'), (8, 'fhalse7@fc2.com'), (8, 'fhalse7@moonfruit.com'),
(9, 'dwitherbed8@umich.edu'), (9, 'dwitherbed8@pbs.org'), (9, 'dwitherbed8@privacy.gov.au'),
(10, 'ksoloway9@amazon.co.uk'), (10, 'ksoloway9@sitemeter.com'), (10, 'ksoloway9@cmu.edu'), (10, 'ksoloway9@unicef.org'),
(11, 'rantoshina@cam.ac.uk'), (11, 'rantoshina@amazonaws.com'), (11, 'rantoshina@economist.com,'), (11, 'rantoshina@sfgate.com');

insert into Store (storeCode, manager, addressId) values 
('3c0234', 1, 1), 
('9f352c', 3, 2), 
('73668b', 1, 3), 
('13568h', 3, 4),
('96f2cf', 1, 5),
('25d901', 3, 6);

insert into Invoice (invoiceCode, storeId, customer, salesPerson, date) values 
('INV001', 1, 5, 1, '2666-01-33'), 
('INV002', 1, 9, 2, '4027-01-11'), 
('INV003', 2, 11, 3, '5572-05-01'), 
('INV004', 2, 12, 4, '6047-01-30'),
('INV005', 4, 1, 10, '2023-03-10');

insert into Items (itemsCode, type, itemName, unit, model, unitPrice, hourlyRate) values 
('1d4d89','E','Lawn Mower', null,'Ryobi er12', null, null),
('1d4d73','E','Dog', null,'SUPER MEGA 200x', null, null),
('0ec6e9','E','Harvester', null,'Looploop 120x', null, null),
('740515','E','Baler', null,'Haymakr 400', null, null),
('3506f6','E','Farmer', null,'Large Corp X25', null, null),
('649f88','E','Truck', null,'F105', null, null),
('342foa3','P','Haybale','bale', null, 500, null),
('3n3k4l2','P','Corn seed','bag', null, 50, null),
('n3453js','P','Fertilizer','liter', null, 10.25, null),
('t7450us','P','Planter','pot', null, 3, null),
('l4k32j4','P','Wire fencing','lft', null, 8, null),
('23n4kl2','P','Top soil','ton', null, 850, null),
('2334b23','S','Delivery', null, null, null, 100),
('n43k2l3','S','Plowing', null, null, null, 1000),
('432kn2l','S','Tractor Driving Lessons', null, null, null, 25),
('8432941','S','Cattle Vaccination', null, null, null, 225.50),
('im329op','S','Extermination', null, null, null, 2025.50);

insert into InvoiceItems (invoiceId, itemsId, typeOfBuy, price, quantity, hoursBilled, startDate, endDate) values 
(1, 2, 'P', 1117, NULL, NULL, NULL, NULL),
(4, 3, 'P', 2504, NULL, NULL, NULL, NULL),
(2, 4, 'P', 255, NULL, NULL, NULL, NULL),
(3, 5, 'P', 4501, NULL, NULL, NULL, NULL), 
(1, 6, 'L', 2485, NULL, NULL, '2666-01-30', '2666-02-25'),
(2, 1, 'L', 1034, NULL, NULL, '4027-01-11', '4027-03-09'),
(3, 2, 'L', 1101, NULL, NULL, '5572-05-01', '5590-12-03'),
(4, 3, 'L', 4362, NULL, NULL, '6047-01-30', '6047-06-15'),
(1, 7, null, null, 348, null, null, null),
(1, 12, null, null, 100, null, null, null),
(2, 8, null, null, 154, null, null, null),
(3, 9, null, null, 410, null, null, null),
(4, 10, null, null, 104, null, null, null),
(1, 14, null, null, null, 3.0, null, null),
(2, 15, null, null, null, 5.0, null, null),
(3, 16, null, null, null, 1.5, null, null),
(4, 17, null, null, null, 10.2, null, null);
