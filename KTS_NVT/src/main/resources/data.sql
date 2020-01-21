insert into location (id, title, address) values (1, "Location 1", "Address");
insert into location (id, title, address) values (2, "Location 2", "Address 2");

insert into stand_sector (id, title, max_guests, location_id) values (100, "Stand sector", 100, 1);
insert into stand_sector (id, title, max_guests, location_id) values (101, "Stand sector 2", 150, 1);

insert into seats_sector (id, title, row_num, column_num, location_id) values (102, "Seats sector", 50,50, 1);
insert into seats_sector (id, title, row_num, column_num, location_id) values (103, "Seats sector 2", 30,60, 1);

insert into event (id, title, description, is_Active, event_Category, location_id) values (105, "Event1", "Descript Event1", true, 0, 1);
insert into event (id, title, description, is_Active, event_Category, location_id) values (106, "Event2", "Descript Event2", true, 1, 2);

insert into sector_price (id, price, sector_id, event_id) values (110, 56, 100, 105);
insert into sector_price (id, price, sector_id, event_id) values (111, 42, 101, 106);

insert into event_day (id, title, eventdate, event_id) values (201, "Day1", '2019-05-30', 105);
insert into event_day (id, title, eventdate, event_id) values (202, "Day2", '2019-05-28', 106);

