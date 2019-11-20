DROP TABLE IF EXISTS oauth_code;

CREATE TABLE oauth_code (
    code VARCHAR(255), authentication BYTEA
);
