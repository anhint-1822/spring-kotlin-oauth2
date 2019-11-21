DROP TABLE IF EXISTS oauth_client_token;

CREATE TABLE oauth_client_token (
  token_id VARCHAR(255),
  token BLOB,
  authentication_id VARCHAR(255),
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  PRIMARY KEY (authentication_id)
);
