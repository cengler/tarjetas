# --- Sample dataset

# --- !Ups
insert into account (type, id, name, number, since , through, close_week_number, close_weekday, pay_day) values ('bank',1001,'Caja de Ahorro',123456789123,null,null,null,null,null);
insert into account (type, id, name, number, since , through, close_week_number, close_weekday, pay_day) values ('card',1002,'Visa',123456789111,'1987-01-01','2030-01-01',1,0,10);
 
insert into category (id, name) values (1001, 'Electro');
insert into category (id, name) values (1002, 'Sueldo');
insert into category (id, name) values (1003, 'Comida');
insert into category (id, name) values (1004, 'Indumentaria');

insert into movement(id, ptype, description, amount, date, category_id, account_id) values (1001, 0, 'Sueldo', 9000, '2013-11-11', 1002, 1001);
insert into movement(id, ptype, description, amount, date, category_id, account_id) values (1002, 1, 'Tele', 190, '2013-11-11', 1001, 1001);
insert into movement(id, ptype, description, amount, date, category_id, account_id) values (1003, 1, 'Pan', 10, '2013-11-11', 1003, 1001);
insert into movement(id, ptype, description, amount, date, category_id, account_id) values (1004, 1, 'Pan', 10, '2013-11-11', 1003, 1002);
insert into movement(id, ptype, description, amount, date, category_id, account_id) values (1005, 1, 'Pan', 40, '2013-11-11', 1003, 1002);

# --- !Downs
