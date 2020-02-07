/* insert into location (id, title, address) values (1, "Location 1", "Address");
 insert into location (id, title, address) values (2, "Location 2", "Address 2");

 insert into stand_sector (id, title, max_guests, location_id, top, pos_left, width, height, angle) values (100, "Stand sector", 100, 1, 50, 50, 100, 100, 0);
 insert into stand_sector (id, title, max_guests, location_id, top, pos_left, width, height, angle) values (101, "Stand sector 2", 150, 1, 125, 125, 75, 150, 315);

 insert into seats_sector (id, title, row_num, column_num, location_id, top, pos_left, width, height, angle) values (102, "Seats sector", 10,10, 1, 300, 20, 100, 100, 0);
 insert into seats_sector (id, title, row_num, column_num, location_id, top, pos_left, width, height, angle) values (103, "Seats sector 2", 7,15, 1, 20, 300, 150, 100, 0);

 insert into event (id, title, description, is_Active, event_Category, location_id) values (105, "Event1", "Descript Event1", true, 0, 1);
 insert into event (id, title, description, is_Active, event_Category, location_id) values (106, "Event2", "Descript Event2", true, 1, 2);

 insert into sector_price (id, price, sector_id, event_id) values (110, 56, 100, 105);
 insert into sector_price (id, price, sector_id, event_id) values (111, 42, 101, 106);

 insert into event_day (id, title, eventdate, event_id) values (201, "Day1", '2020-05-30', 105);
 insert into event_day (id, title, eventdate, event_id) values (202, "Day2", '2020-05-28', 106);
 insert into event_day (id, title, eventdate, event_id) values (203, "Day2", '2020-05-30', 105);
 
 insert into role (role_id, role) values(1, "ADMIN");
 insert into role (role_id, role) values(2, "USER");*/