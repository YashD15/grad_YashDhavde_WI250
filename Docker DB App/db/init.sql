CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(50)
);

INSERT INTO users (name, email) VALUES ('Ramesh', 'ramesh@gmail.com');
INSERT INTO users (name, email) VALUES ('Rakesh', 'rakesh@gmail.com');