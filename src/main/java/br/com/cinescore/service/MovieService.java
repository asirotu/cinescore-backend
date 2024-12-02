package br.com.cinescore.service;

import br.com.cinescore.dto.MovieDto;
import br.com.cinescore.exception.ResourceNotFoundException;
import br.com.cinescore.mapper.CustomModelMapper;
import br.com.cinescore.model.MovieModel;
import br.com.cinescore.repository.MovieRepository;
import br.com.cinescore.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private ReviewRepository reviewRepository;

    public MovieDto create(MovieDto movieDto) {
        MovieModel movieModel = CustomModelMapper.parseObject(movieDto, MovieModel.class);
        return CustomModelMapper.parseObject(repository.save(movieModel), MovieDto.class);
    }

    public MovieDto findById(Long id) {
        MovieModel found = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Filme não encontrado com o ID: " + id));

        MovieDto movieDto = CustomModelMapper.parseObject(found, MovieDto.class);

        Double averageRating = reviewRepository.calculateAverageRating(found.getId());
        int roundedRating = averageRating != null ? (int) Math.round(averageRating) : 0;
        movieDto.setRating(roundedRating);

        return movieDto;
    }

    public MovieDto update(MovieDto movieDto) {
        MovieModel found = repository.findById(movieDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Filme não encontrado com o ID: " + movieDto.getId()));

        found.setTitle(movieDto.getTitle());
        found.setReleaseYear(movieDto.getReleaseYear());
        found.setDirector(movieDto.getDirector());
        found.setGenre(movieDto.getGenre());
        found.setDurationMinutes(movieDto.getDurationMinutes());
        found.setImage(movieDto.getImage());
        return CustomModelMapper.parseObject(repository.save(found), MovieDto.class);
    }

    public void delete(Long id) {
        MovieModel found = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Filme não encontrado com o ID: " + id));
        repository.delete(found);
    }

    public Page<MovieDto> findAll(Pageable pageable) {
        Page<MovieModel> movies = repository.findAll(pageable);

        return movies.map(movie -> {
            MovieDto movieDto = CustomModelMapper.parseObject(movie, MovieDto.class);
            Double averageRating = reviewRepository.calculateAverageRating(movie.getId());
            int roundedRating = averageRating != null ? (int) Math.round(averageRating) : 0;
            movieDto.setRating(roundedRating);

            return movieDto;
        });
    }

    public Page<MovieDto> findByTitle(String title, Pageable pageable) {
        return repository.findByTitleStartingWithIgnoreCaseOrderByTitleAsc(title, pageable)
                .map(movie -> CustomModelMapper.parseObject(movie, MovieDto.class));
    }

    public Page<MovieDto> findByDirector(long id, Pageable pageable) {
        Page<MovieModel> movies = repository.findByDirectorId(id, pageable);

        return movies.map(movie -> {
            MovieDto movieDto = CustomModelMapper.parseObject(movie, MovieDto.class);
            Double averageRating = reviewRepository.calculateAverageRating(movie.getId());
            int roundedRating = averageRating != null ? (int) Math.round(averageRating) : 0;
            movieDto.setRating(roundedRating);

            return movieDto;
        });
    }
}
