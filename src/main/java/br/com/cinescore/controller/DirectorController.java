package br.com.cinescore.controller;

import br.com.cinescore.dto.DirectorDto;
import br.com.cinescore.exception.CustomExceptionResponse;
import br.com.cinescore.service.DirectorService;
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

@Tag(name = "Director", description = "Endpoint para operações relacionadas a diretores")
@RestController
@RequestMapping("/directors")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Criar um novo diretor", responses = {
            @ApiResponse(responseCode = "201", description = "Diretor criado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = DirectorDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public ResponseEntity<DirectorDto> create(@RequestBody DirectorDto directorDto) {
        DirectorDto director = directorService.create(directorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(director);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um diretor pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "Diretor encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = DirectorDto.class))),
            @ApiResponse(responseCode = "404", description = "Diretor não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    public ResponseEntity<DirectorDto> findById(@PathVariable long id) {
        DirectorDto director = directorService.findById(id);
        return ResponseEntity.ok(director);
    }

    @PutMapping
    @Operation(summary = "Atualizar um diretor existente", responses = {
            @ApiResponse(responseCode = "200", description = "Diretor atualizado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = DirectorDto.class))),
            @ApiResponse(responseCode = "404", description = "Diretor não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    public ResponseEntity<DirectorDto> update(@RequestBody DirectorDto directorDto) {
        DirectorDto updatedDirector = directorService.update(directorDto);
        return ResponseEntity.ok(updatedDirector);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um diretor pelo ID", responses = {
            @ApiResponse(responseCode = "204", description = "Diretor deletado"),
            @ApiResponse(responseCode = "404", description = "Diretor não encontrado", content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable long id) {
        directorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Listar todos os diretores com paginação", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de diretores", content = @Content(
                    mediaType = "application/json"))
    })
    public ResponseEntity<DirectorDto> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String direction,
            PagedResourcesAssembler<DirectorDto> assembler
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), "name"));
        Page<DirectorDto> directors = directorService.findAll(pageable);
        return new ResponseEntity(assembler.toModel(directors), HttpStatus.OK);
    }
}
