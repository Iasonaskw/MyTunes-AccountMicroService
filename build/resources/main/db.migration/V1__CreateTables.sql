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

CREATE TABLE user_role
(
    id        int         NOT NULL AUTO_INCREMENT,
    user_id   int         NOT NULL,
    role_name varchar(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (user_id, role_name),
    FOREIGN KEY (user_id) REFERENCES users (id)
);