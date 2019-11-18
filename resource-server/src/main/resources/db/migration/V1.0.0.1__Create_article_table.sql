DROP TABLE IF EXISTS article;

CREATE TABLE article(
    id CHAR(36) NOT NULL,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);