DROP TABLE IF EXISTS oauth_access_token;

CREATE TABLE oauth_access_token (
  token_id VARCHAR(255),
  token BYTEA,
  authentication_id VARCHAR(255),
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication BYTEA,
  refresh_token VARCHAR(255),
  PRIMARY KEY (authentication_id)
);
