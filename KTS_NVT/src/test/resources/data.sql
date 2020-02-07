 truncate table stand_sector;
 truncate table seats_sector;
 delete from location;
 delete from event;
 delete from event_day;
 delete from seats_ticket;
 delete from stand_ticket;
 delete from role;
 delete from sector_price;


 insert into location (id, title, address) values (1, 'Location 1', 'Address');
 insert into location (id, title, address) values (2, 'Location 2', 'Address 2');
 insert into location (id, title, address) values (3, 'Location 3', 'Address 3');

 insert into stand_sector (id, title, max_guests, location_id, top, pos_left, width, height, angle) values (101, 'Stand sector', 100, 1, 0, 0, 50, 50, 0);
 insert into stand_sector (id, title, max_guests, location_id, top, pos_left, width, height, angle) values (102, 'Stand sector 2', 150, 1, 100, 0, 50, 50, 0);

 insert into seats_sector (id, title, row_num, column_num, location_id, top, pos_left, width, height, angle) values (103, 'Seats sector', 10,10, 1, 0, 100, 50, 50, 0);
 insert into seats_sector (id, title, row_num, column_num, location_id, top, pos_left, width, height, angle) values (104, 'Seats sector 2', 20,10, 2, 100, 100, 50, 50, 0);

 delete from event;

 insert into event (id, title, description, max_tickets, location_id, event_category) values (1, 'Event 1', 'Event 1 Description', 60, 1, 0);
 insert into event (id, title, description, max_tickets, location_id, event_category) values (2, 'Event 2', 'Event 2 Description', 70, 2, 1);
 insert into event (id, title, description, max_tickets, location_id, event_category) values (3, 'Event 3', 'Event 3 Description', 80, 1, 2);
 insert into event (id, title, description, max_tickets,location_id, event_category) values (4, 'Event 4', 'Event 4 Description', 90, 2, 2);

 insert into event_day (id, eventdate, title, event_id) values (1, '2020-03-05', 'Event1 DAY 1', 1);
 insert into event_day (id, eventdate, title, event_id) values (2, '2020-03-06', 'Event4 DAY 1', 4);
 insert into event_day (id, eventdate, title, event_id) values (3, '2020-03-07', 'Event4 DAY 2', 4);
 
 insert into event_day (id, eventdate, title, event_id) values (4, '2019-03-07', 'Event3 DAY 1', 3);
 
 insert into sector_price (id, price, sector_id, event_id) values (110, 56, 101, 1);
 insert into sector_price (id, price, sector_id, event_id) values (111, 42, 102, 1);
 insert into sector_price (id, price, sector_id, event_id) values (112, 100, 104, 2);
 
 insert into seats_ticket (id, price, single_day, day_id, event_id, sector_id, column_num, row_num) values (1, 5000, 1, 1, 1, 103, 1, 1);
 insert into seats_ticket (id, price, single_day, day_id, event_id, sector_id, column_num, row_num) values (2, 5000, 1, 1, 1, 103, 1, 2);
 insert into seats_ticket (id, price, single_day, day_id, event_id, sector_id, column_num, row_num) values (3, 5000, 1, 1, 1, 103, 1, 3);
 insert into stand_ticket (id, price, single_day, day_id, event_id, sector_id) values(1, 2000, 0, 1, 1,101);
 insert into stand_ticket (id, price, single_day, day_id, event_id, sector_id) values(2, 2000, 1, 1, 1, 101);
 insert into stand_ticket (id, price, single_day, day_id, event_id, sector_id) values(3, 2000, 1, 1, 1, 101);
 insert into stand_ticket (id, price, single_day, day_id, event_id, sector_id) values(4, 2000, 1, 1, 1, 101);
 insert into stand_ticket (id, price, single_day, day_id, event_id, sector_id) values(5, 2000, 1, 1, 1, 101);
 
 insert into seats_ticket (id, price, single_day, day_id, event_id, sector_id, column_num, row_num) values (111, 5000, 1, 3, 4, 103, 2, 2);
 insert into seats_ticket (id, price, single_day, day_id, event_id, sector_id, column_num, row_num) values (222, 5000, 1, 3, 4, 103, 2, 3);
 insert into stand_ticket (id, price, single_day, day_id, event_id, sector_id) values(555, 2000, 1, 2, 1, 102);
 insert into stand_ticket (id, price, single_day, day_id, event_id, sector_id) values(666, 2000, 1, 2, 1, 102);

 insert into role (role_id, role) values(1, 'ADMIN');
 insert into role (role_id, role) values(2, 'USER');
 