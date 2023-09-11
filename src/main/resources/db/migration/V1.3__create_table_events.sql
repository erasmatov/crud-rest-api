CREATE TABLE IF NOT EXISTS events
(
    id        INT AUTO_INCREMENT,
    timestamp TIMESTAMP NOT NULL,
    user_id INT NOT NULL,
    file_id   INT NOT NULL,

    CONSTRAINT
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (file_id) REFERENCES files(id)
);