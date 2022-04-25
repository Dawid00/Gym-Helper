CREATE TABLE users(
	id bigint NOT NULL,
	username character varying(50) NOT NULL,
	password character varying(255) NOT NULL,
	email character varying(50) NOT NULL ,
	height real,
	weight real,
	CONSTRAINT users_pk PRIMARY KEY(id)
);