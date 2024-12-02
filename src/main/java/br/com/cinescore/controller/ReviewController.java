package br.com.cinescore.controller;

import br.com.cinescore.dto.ReviewDto;
import br.com.cinescore.exception.CustomExceptionResponse;
import br.com.cinescore.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Review", description = "Endpoint para operações relacionadas a avaliações")
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Criar uma nova avaliação", responses = {
            @ApiResponse(responseCode = "201", description = "Avaliação criada", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ReviewDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public ResponseEntity<ReviewDto> create(@RequestBody ReviewDto reviewDto) {
        ReviewDto review = reviewService.create(reviewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma avaliação pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "Avaliação encontrada", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ReviewDto.class))),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    public ResponseEntity<ReviewDto> findById(@PathVariable long id) {
        ReviewDto review = reviewService.findById(id);
        return ResponseEntity.ok(review);
    }

    @PutMapping
    @Operation(summary = "Atualizar uma avaliação existente", responses = {
            @ApiResponse(responseCode = "200", description = "Avaliação atualizada", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ReviewDto.class))),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    public ResponseEntity<ReviewDto> update(@RequestBody ReviewDto reviewDto) {
        ReviewDto updatedReview = reviewService.update(reviewDto);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma avaliação pelo ID", responses = {
            @ApiResponse(responseCode = "204", description = "Avaliação deletada"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada", content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Listar todas as avaliações com paginação", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de avaliações", content = @Content(
                    mediaType = "application/json"))
    })
    public ResponseEntity<Page<ReviewDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String direction,
            PagedResourcesAssembler<ReviewDto> assembler
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), "id"));
        Page<ReviewDto> reviews = reviewService.findAll(pageable);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/find/movie/{Id}")
    @Operation(summary = "Buscar avaliações de um filme pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "Avaliações encontradas", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ReviewDto.class))),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    public ResponseEntity<Page<ReviewDto>> findByMovieId(
            @PathVariable long Id, Pageable pageable) {
        Page<ReviewDto> reviews = reviewService.findByMovieId(Id, pageable);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/find/user/{id}")
    @Operation(summary = "Buscar avaliações de um usuário pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "Avaliações encontradas", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ReviewDto.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    public ResponseEntity<Page<ReviewDto>> findByUserId(
            @PathVariable long Id, Pageable pageable) {
        Page<ReviewDto> reviews = reviewService.findByUserId(Id, pageable);
        return ResponseEntity.ok(reviews);
    }
}
