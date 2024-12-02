CREATE TABLE IF NOT EXISTS reviews (
    id SERIAL NOT NULL,
    rating INT CHECK (rating BETWEEN 1 AND 5) NOT NULL,
    comment TEXT,
    user_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    CONSTRAINT pk_reviews PRIMARY KEY (id),
    CONSTRAINT fk_reviews_users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_reviews_movies FOREIGN KEY (movie_id) REFERENCES movies(id)
);
