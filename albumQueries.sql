# Computer Science II
# Lab 8.0 - Structured Query Language II
# Queries
#
# Name(s): Aaron, Kyle
#
#
#
# Part 3.2

# 1. Choose your favorite album and insert it into the database by doing the
#   following.
#   1.  Write a query to insert the band of the album 
insert into Band (bandId,name) values (9000, "American Authors");
#   2.  Write a query to insert the album 
insert into Album (albumId,title,year,number,bandId) values (5000, "Oh, What A Life", 2014, 1, 9000);
#   3.  Write two queries to insert the first two songs of the album
insert into Song (songId,title) values (89221,"Believer");
insert into Song (songId,title) values (89222,"Think About It");
#   4.  Write two queries to associate the two songs with the inserted album
insert into AlbumSong (trackNumber,trackLength,albumId,songId) values (1, 183, 5000, 89221);
insert into AlbumSong (trackNumber,trackLength,albumId,songId) values (2, 183, 5000, 89222);
# 2. Update the musician record for "P. Best", his first name should be "Pete".
update Musician set firstName = "Pete" where musicianId = 1005;
# 3. Pete Best was the Beatles original drummer, but was fired in 1962. 
#    Write a query that removes Pete Best from the Beatles.
delete from BandMember where musicianId = 1005;
# 4. Attempt to delete the song "Big in Japan" (by Tom Waits on the album
#    *Mule Variations*).  Observe that the query will fail due to referencing
#    records. Write a series of queries that will allow you to delete the 
#    album *Mule Variations*.
delete from AlbumSong where songId = 71;
# Part 3.2.2
# Write quries to create your new tables for concerts, venues, etc. here:

create table Venue (
	venueId int not null primary key auto_increment,
	name varchar(100) not null,
    capacity int not null,
    ticketsSold int not null
);

create table Concert (
	concertId int not null primary key auto_increment,
    date varchar(100) not null,
    venueId int not null,
    foreign key (venueId) references Venue(venueId)
);

create table ConcertSong (
	concertSongId int not null primary key auto_increment,
    setListNumber int not null,
	songId int not null,
	concertId int not null,
	foreign key (songId) references Song(songId),
	foreign key (concertId) references Concert(concertId)
);





# Part 3.3.3
# 
# Insert some test data to your new tables
#
# 1.  Write queries to insert at least two `Concert` records.
insert into Venue (venueId, name, capacity, ticketsSold) values (1,"metlife stadium", 85000, 100);
insert into Concert (concertId, date, venueId) values (1, "2023-04-28", 1);
insert into Concert (concertId, date, venueId) values (2, "2024-03-04", 1);
# 2.  Write queries to associate at least 2 songs with each of the two concerts
insert into ConcertSong (concertSongId, setListNumber, songId, concertId) values (1, 1, 87, 1);
insert into ConcertSong (concertSongId, setListNumber, songId, concertId) values (2, 2, 88, 1);
insert into ConcertSong (concertSongId, setListNumber, songId, concertId) values (3, 1, 89221, 2);
insert into ConcertSong (concertSongId, setListNumber, songId, concertId) values (4, 2, 89222, 2);
# 3.  Write a select-join query to retrieve these new results and produce
#     a playlist for each concert
select * from Song s
join ConcertSong cS on s.songId = cS.songId
join Concert c on cS.concertId = c.concertId;