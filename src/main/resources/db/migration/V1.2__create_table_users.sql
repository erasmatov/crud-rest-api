CREATE TABLE IF NOT EXISTS users
(
    id       INT AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    status VARCHAR(7)   NOT NULL,

    CONSTRAINT
    PRIMARY KEY (id)
);