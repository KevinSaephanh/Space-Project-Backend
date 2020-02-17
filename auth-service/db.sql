create table if not exists user_roles (
	role_id serial primary key,
	role_name varchar(10) not null
)

create table if not exists users (
	user_id serial primary key,
	username varchar(25) not null unique,
	"password" varchar(100) not null,
	email varchar(50) not null unique,
	role_id integer references user_roles(role_id) not null
)

-------------------- Predefined User Roles ------------------------
insert into user_roles(role_name) values('user');
insert into user_roles(role_name) values('admin');