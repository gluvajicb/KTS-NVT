truncate table stand_sector;
truncate table seats_sector;
delete from location;

insert into location (id, title, address) values (1, 'Location 1', 'Address');
insert into location (id, title, address) values (2, 'Location 2', 'Address 2');
insert into location (id, title, address) values (3, 'Location 3', 'Address 3');

insert into stand_sector (id, title, max_guests, location_id) values (101, 'Stand sector', 100, 1);
insert into stand_sector (id, title, max_guests, location_id) values (102, 'Stand sector 2', 150, 1);

insert into seats_sector (id, title, row_num, column_num, location_id) values (103, 'Seats sector', 50,50, 1);
insert into seats_sector (id, title, row_num, column_num, location_id) values (104, 'Seats sector 2', 30,60, 2);

delete from event;

insert into event (id, title, description, max_tickets) values (1, 'Event 1', 'Event 1 Description', 60);
insert into event (id, title, description, max_tickets) values (2, 'Event 2', 'Event 2 Description', 70);
insert into event (id, title, description, max_tickets) values (3, 'Event 3', 'Event 3 Description', 80);
insert into event (id, title, description, max_tickets) values (4, 'Event 4', 'Event 4 Description', 90);