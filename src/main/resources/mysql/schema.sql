

create table USER (id int NOT NULL AUTO_INCREMENT, user_id int NOT NULL, phone_number VARCHAR(10), PRIMARY KEY (id), UNIQUE (user_id));

create table USER_CREDENTIALS (id int NOT NULL AUTO_INCREMENT, client_key VARCHAR(32) NOT NULL, secret VARCHAR(32) NOT NULL, user_id int NOT NULL, PRIMARY KEY (id), FOREIGN KEY (user_id) REFERENCES USER(user_id));