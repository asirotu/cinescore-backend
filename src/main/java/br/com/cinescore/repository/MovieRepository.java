package br.com.cinescore.repository;

import br.com.cinescore.model.MovieModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<MovieModel, Long> {
    Page<MovieModel> findByTitleStartingWithIgnoreCaseOrderByTitleAsc(String title, Pageable pageable);

    Optional<MovieModel> findById(Long id);

    Page<MovieModel> findByDirectorId(Long id, Pageable pageable);
}