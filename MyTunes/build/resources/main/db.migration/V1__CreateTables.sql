CREATE TABLE users
(
    id   int     NOT NULL AUTO_INCREMENT,
    username varchar(20) NOT NULL,
    password varchar(200) NOT NULL,
    email varchar(60) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (username),
    UNIQUE (email)
);