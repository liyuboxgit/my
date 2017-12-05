drop table SECURITY_BUTTON;
drop table SECURITY_DEPARTMENT;
drop table SECURITY_MODULE;
drop table SECURITY_PERMISSION;
drop table SECURITY_POSITION;
drop table SECURITY_USER;

drop sequence SQE_SECURITY_BUTTON;
drop sequence SQE_SECURITY_DEPARTMENT;
drop sequence SQE_SECURITY_MODULE;
drop sequence SQE_SECURITY_PERMISSION;
drop sequence SQE_SECURITY_POSITION;
drop sequence SQE_SECURITY_USER;

create sequence SQE_SECURITY_BUTTON start with 1 increment by 1;
create sequence SQE_SECURITY_DEPARTMENT start with 1 increment by 1;
create sequence SQE_SECURITY_MODULE start with 1 increment by 1;
create sequence SQE_SECURITY_PERMISSION start with 1 increment by 1;
create sequence SQE_SECURITY_POSITION start with 1 increment by 1;
create sequence SQE_SECURITY_USER start with 1 increment by 1;

create table security_user(
	id number(11),
	loginName varchar2(255),
	password varchar2(255),
	userName varchar2(255),
	departMentId number(11),
	positionId number(11),
	primary key(id)
);

create table security_department(
	id number(11),
	name varchar2(255),
	pid number(11),
	primary key(id)
);

create table security_position(
	id number(11),
	name varchar2(255),
	pid number(11),
	departMentId number(11),
	primary key(id)
);

create table security_module(
	id number(11),
	code varchar2(255) unique,
	name varchar2(255),
	pid number(11),
	url varchar2(255),
	primary key(id)
);

create table security_button(
	id number(11),
	code varchar2(255) unique,
	mark varchar2(255),
	enable number(1),
	primary key(id)
);

create table security_permission(
	id number(11),
	positionId number(11),
	moduleId number(11),
	buttons varchar2(255),
	primary key(id),
	constraint unique_positionId_moduleId unique(positionId,moduleId)
);