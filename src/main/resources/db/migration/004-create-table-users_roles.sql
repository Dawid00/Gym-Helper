CREATE TABLE users_roles(

	user_id bigint NOT NULL,
	role_id bigint NOT NULL,
	CONSTRAINT users_roles_pk PRIMARY KEY(user_id, role_id),
	CONSTRAINT fk_users_roles_users FOREIGN KEY(user_id) REFERENCES users(id),
	CONSTRAINT fk_users_roles_roles FOREIGN KEY(role_id) REFERENCES roles(id)
);
