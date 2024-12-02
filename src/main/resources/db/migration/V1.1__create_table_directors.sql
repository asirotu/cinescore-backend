CREATE TABLE IF NOT EXISTS directors (
    id SERIAL NOT NULL,
    name VARCHAR(255) NOT NULL,
    bio TEXT,
    CONSTRAINT pk_directors PRIMARY KEY (id)
);