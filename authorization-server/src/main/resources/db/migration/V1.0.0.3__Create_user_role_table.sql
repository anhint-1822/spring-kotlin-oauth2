DROP TABLE IF EXISTS user_role;

CREATE TABLE user_role(
    user_id CHAR(36) NOT NULL,
    role_id CHAR(36) NOT NULL,
    PRIMARY KEY (user_id, role_id)
);