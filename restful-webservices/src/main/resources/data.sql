insert into user_details (id, dob, name, password) values (1000, current_date(), 'Manjiri', 'abc');
insert into user_details (id, dob, name, password) values (1001, current_date(), 'Shashank', 'abcd');
insert into user_details (id, dob, name, password) values (1002, current_date(), 'Vedant', 'abcdf');

insert into post (id, description, user_id) values (2000, 'Learning Aws', 1000);
insert into post (id, description, user_id) values (2001, 'Learning Java', 1000);
insert into post (id, description, user_id) values (2002, 'Learning JPA', 1001);
insert into post (id, description, user_id) values (2003, 'Learning Spring', 1001);
insert into post (id, description, user_id) values (2004, 'Learning SpringBoot', 1002);

