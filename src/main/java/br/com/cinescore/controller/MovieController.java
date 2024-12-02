package br.com.cinescore.controller;

import br.com.cinescore.dto.MovieDto;
import br.com.cinescore.exception.CustomExceptionResponse;
import br.com.cinescore.service.MovieService;
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
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Movie", description = "Endpoint para operações relacionadas a filmes")
@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Criar um novo filme com imagem", responses = {
            @ApiResponse(responseCode = "201", description = "Filme criado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = MovieDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public ResponseEntity<MovieDto> create(@RequestBody MovieDto movieDto) {
        MovieDto movie = movieService.create(movieDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(movie);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Atualizar um filme com imagem", responses = {
            @ApiResponse(responseCode = "200", description = "Filme atualizado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = MovieDto.class))),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado", content = @Content)
    })
    public ResponseEntity<MovieDto> update(@RequestBody MovieDto movieDto) {
        MovieDto updatedMovie = movieService.update(movieDto);
        return ResponseEntity.ok(updatedMovie);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um filme pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "Filme encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = MovieDto.class))),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    public ResponseEntity<MovieDto> findById(@PathVariable long id) {
        MovieDto movie = movieService.findById(id);
        return ResponseEntity.ok(movie);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um filme pelo ID", responses = {
            @ApiResponse(responseCode = "204", description = "Filme deletado"),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado", content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable long id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Listar todos os filmes com paginação", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de filmes", content = @Content(
                    mediaType = "application/json"))
    })
    public ResponseEntity<PagedModel<EntityModel<MovieDto>>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String direction,
            PagedResourcesAssembler<MovieDto> assembler
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), "title"));
        Page<MovieDto> movies = movieService.findAll(pageable);
        PagedModel<EntityModel<MovieDto>> resources = assembler.toModel(movies);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/find/directors/{id}")
    @Operation(summary = "Buscar filmes de um diretor pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "Filmes encontrados", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = MovieDto.class))),
            @ApiResponse(responseCode = "404", description = "Diretor não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    public ResponseEntity<PagedModel<EntityModel<MovieDto>>> findByDirectorId(
            @PathVariable long id, Pageable pageable, PagedResourcesAssembler<MovieDto> assembler) {
        Page<MovieDto> movies = movieService.findByDirector(id, pageable);
        PagedModel<EntityModel<MovieDto>> resources = assembler.toModel(movies);
        return ResponseEntity.ok(resources);
    }
}
