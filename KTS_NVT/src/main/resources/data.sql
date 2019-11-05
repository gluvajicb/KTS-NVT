insert into location (id, title, address) values (1, "Location 1", "Address");
insert into location (id, title, address) values (2, "Location 2", "Address 2");

insert into stand_sector (id, title, max_guests, location_id) values (100, "Stand sector", 100, 1);
insert into stand_sector (id, title, max_guests, location_id) values (101, "Stand sector 2", 150, 1);

insert into seats_sector (id, title, row_num, column_num, location_id) values (102, "Seats sector", 50,50, 1);
insert into seats_sector (id, title, row_num, column_num, location_id) values (103, "Seats sector 2", 30,60, 1);