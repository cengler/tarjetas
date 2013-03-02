# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  type                      varchar(31) not null,
  id                        bigint not null,
  name                      varchar(255),
  number                    bigint,
  since                     timestamp,
  through                   timestamp,
  close_week_number         integer,
  close_weekday             integer,
  pay_day                   integer,
  constraint ck_account_close_weekday check (close_weekday in (0,1,2,3,4,5,6)),
  constraint pk_account primary key (id))
;

create table category (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_category primary key (id))
;

create table closure (
  id                        bigint not null,
  account_id                bigint,
  date                      timestamp,
  amount                    double,
  constraint pk_closure primary key (id))
;

create table movement (
  id                        bigint not null,
  ptype                     integer,
  description               varchar(255),
  amount                    double,
  date                      timestamp,
  category_id               bigint,
  account_id                bigint,
  constraint ck_movement_ptype check (ptype in (0,1)),
  constraint pk_movement primary key (id))
;

create sequence account_seq;

create sequence category_seq;

create sequence closure_seq;

create sequence movement_seq;

alter table closure add constraint fk_closure_account_1 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_closure_account_1 on closure (account_id);
alter table movement add constraint fk_movement_category_2 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_movement_category_2 on movement (category_id);
alter table movement add constraint fk_movement_account_3 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_movement_account_3 on movement (account_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists account;

drop table if exists category;

drop table if exists closure;

drop table if exists movement;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists account_seq;

drop sequence if exists category_seq;

drop sequence if exists closure_seq;

drop sequence if exists movement_seq;

