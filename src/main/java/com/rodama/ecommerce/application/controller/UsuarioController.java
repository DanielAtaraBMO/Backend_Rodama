package com.rodama.ecommerce.application.controller;

import com.rodama.ecommerce.application.dto.UsuarioDTO;
import com.rodama.ecommerce.application.service.implementation.UsuarioServiceImpl;
import com.rodama.ecommerce.domain.model.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioServiceImpl service;

    public UsuarioController(UsuarioServiceImpl service) {
        this.service = service;
    }

    @Operation(summary = "Agregar un usuario", description = "Crear cliente nuevo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(
            @Valid @RequestBody UsuarioDTO dto) {

        Usuario usuario = service.crearUsuario(dto.toEntity());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UsuarioDTO.fromEntity(usuario));
    }

    @Operation(summary = "Obtener todos los usuarios", description = "Lista todos los clientes registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta exitosa")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerTodos() {

        List<UsuarioDTO> usuarios = service.obtenerTodos()
                .stream()
                .map(UsuarioDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Obtener usuario por id", description = "Busca un usuario por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> encontrarPorId(@PathVariable Long id) {

        Optional<Usuario> usuario = service.encontrarPorId(id);

        return usuario
                .map(u -> ResponseEntity.ok(UsuarioDTO.fromEntity(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener usuario por correo", description = "Busca un usuario por su correo electrónico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioDTO> encontrarPorCorreo(@PathVariable String correo) {

        Optional<Usuario> usuario = service.encontrarPorCorreo(correo);

        return usuario
                .map(u -> ResponseEntity.ok(UsuarioDTO.fromEntity(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO dto) {

        Optional<Usuario> actualizado = service.actualizarUsuario(id, dto.toEntity());

        return actualizado
                .map(u -> ResponseEntity.ok(UsuarioDTO.fromEntity(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {

        service.eliminarPorId(id);

        return ResponseEntity.noContent().build();
    }
}