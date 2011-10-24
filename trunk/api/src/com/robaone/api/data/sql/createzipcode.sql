drop table zipcodes;
drop table county;
drop table city;

create table zipcodes (
  zip numeric(5,0) not null primary key,
  county numeric(18,0) not null,
  state varchar(2) not null,
  city numeric(18,0) not null
);

create table county (
  id numeric(18,0) primary key,
  name varchar(64) not null
);

create table city (
 id numeric(18,0) primary key,
 name varchar(64) not null
 );