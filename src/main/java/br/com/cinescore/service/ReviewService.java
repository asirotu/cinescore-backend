package br.com.cinescore.service;

import br.com.cinescore.dto.ReviewDto;
import br.com.cinescore.exception.ResourceNotFoundException;
import br.com.cinescore.mapper.CustomModelMapper;
import br.com.cinescore.model.ReviewModel;
import br.com.cinescore.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repository;

    public ReviewDto create(ReviewDto reviewDto) {
        ReviewModel reviewModel = CustomModelMapper.parseObject(reviewDto, ReviewModel.class);
        return CustomModelMapper.parseObject(repository.save(reviewModel), ReviewDto.class);
    }

    public ReviewDto findById(Long id) {
        ReviewModel found = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Review não encontrada com o ID: " + id));
        return CustomModelMapper.parseObject(found, ReviewDto.class);
    }

    public ReviewDto update(ReviewDto reviewDto) {
        ReviewModel found = repository.findById(reviewDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Review não encontrada com o ID: " + reviewDto.getId()));

        found.setComment(reviewDto.getComment());
        found.setRating(reviewDto.getRating());

        return CustomModelMapper.parseObject(repository.save(found), ReviewDto.class);
    }

    public void delete(Long id) {
        ReviewModel found = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Review não encontrada com o ID: " + id));
        repository.delete(found);
    }

    public Page<ReviewDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(review -> CustomModelMapper.parseObject(review, ReviewDto.class));
    }

    public Page<ReviewDto> findByMovieId(Long movieId, Pageable pageable) {
        return repository.findByMovieId(movieId, pageable)
                .map(review -> CustomModelMapper.parseObject(review, ReviewDto.class));
    }

    public Page<ReviewDto> findByUserId(Long userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable)
                .map(review -> CustomModelMapper.parseObject(review, ReviewDto.class));
    }
}
