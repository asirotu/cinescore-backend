CREATE TABLE IF NOT EXISTS movies (
    id SERIAL NOT NULL,
    director_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    release_year INT,
    genre VARCHAR(50),
    duration_minutes INT,
    image TEXT,
    CONSTRAINT pk_movies PRIMARY KEY (id),
    CONSTRAINT fk_movies_directors FOREIGN KEY (director_id) REFERENCES directors(id)
);
