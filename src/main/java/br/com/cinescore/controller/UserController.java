package br.com.cinescore.controller;

import br.com.cinescore.dto.UserDto;
import br.com.cinescore.exception.CustomExceptionResponse;
import br.com.cinescore.service.UserService;
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

@Tag(name = "User", description = "Endpoint para operações relacionadas a usuários")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Criar um novo usuário", responses = {
            @ApiResponse(responseCode = "201", description = "Usuário criado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        UserDto user = userService.create(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um usuário pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    public ResponseEntity<UserDto> findById(@PathVariable long id) {
        UserDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    @Operation(summary = "Atualizar um usuário existente", responses = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto) {
        UserDto updatedUser = userService.update(userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um usuário pelo ID", responses = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários com paginação", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários", content = @Content(
                    mediaType = "application/json"))
    })
    public ResponseEntity<Page<UserDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String direction,
            PagedResourcesAssembler<UserDto> assembler
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), "username"));
        Page<UserDto> users = userService.findAll(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/find/name/{username}")
    @Operation(summary = "Buscar usuários por nome", responses = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados", content = @Content(
                    mediaType = "application/json"))
    })
    public ResponseEntity<Page<UserDto>> findByName(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String direction,
            PagedResourcesAssembler<UserDto> assembler
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), "username"));
        Page<UserDto> users = userService.findByUsername(username, pageable);
        return ResponseEntity.ok(users);
    }
}
