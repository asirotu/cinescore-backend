package br.com.cinescore.repository;

import br.com.cinescore.model.ReviewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewModel, Long> {
    Page<ReviewModel> findByMovieId(Long movieId, Pageable pageable);
    Page<ReviewModel> findByUserId(Long userId, Pageable pageable);
    @Query("SELECT AVG(r.rating) FROM ReviewModel r WHERE r.movie.id = :movieId")
    Double calculateAverageRating(Long movieId);
}
