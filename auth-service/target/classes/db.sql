create table if not exists users (
	id serial primary key,
	username varchar(25) not null unique,
	"password" varchar(100) not null,
	email varchar(50) not null unique,
	"role" varchar(10) NOT NULL
)
