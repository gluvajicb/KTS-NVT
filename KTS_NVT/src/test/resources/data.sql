truncate table stand_sector;
truncate table seats_sector;
delete from location;

insert into location (id, title, address) values (1, 'Location 1', 'Address');
insert into location (id, title, address) values (2, 'Location 2', 'Address 2');

insert into stand_sector (id, title, max_guests, location_id) values (1, 'Stand sector', 100, 1);
insert into stand_sector (id, title, max_guests, location_id) values (2, 'Stand sector 2', 150, 1);

insert into seats_sector (id, title, row_num, column_num, location_id) values (3, 'Seats sector', 50,50, 1);
insert into seats_sector (id, title, row_num, column_num, location_id) values (4, 'Seats sector 2', 30,60, 2);
