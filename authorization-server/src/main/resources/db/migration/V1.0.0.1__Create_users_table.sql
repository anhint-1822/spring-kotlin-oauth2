DROP TABLE IF EXISTS users;

CREATE TABLE users(
    id CHAR(36) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    reset_token VARCHAR(255),
    PRIMARY KEY (id)
);
