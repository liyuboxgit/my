drop database security;
create database security;
use security;

create table security_user(
	id int(11) not null auto_increment,
	loginName varchar(255) unique,
	password varchar(255),
	userName varchar(255),
	departMentId int(11),
	positionId int(11),
	primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table security_department(
	id int(11) not null auto_increment,
	name varchar(255),
	pid int(11),
	primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table security_position(
	id int(11) not null auto_increment,
	name varchar(255),
	pid int(11),
	departMentId int(11),
	primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table security_module(
	id int(11) not null auto_increment,
	enable int(1),
	name varchar(255),
	pid int(11),
	url varchar(255) ,
	primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table security_button(
	id int(11) not null auto_increment,
	code varchar(255) unique,
	mark varchar(255),
	enable int(1),
	primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table security_permission(
	id int(11) not null auto_increment,
	positionId int(11),
	moduleId int(11),
	buttons varchar(255),
	primary key(id),
	constraint unique_positionId_moduleId unique(positionId,moduleId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;