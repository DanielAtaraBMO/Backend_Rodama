package com.rodama.ecommerce.application.controller;

import com.rodama.ecommerce.application.dto.UsuarioDTO;
import com.rodama.ecommerce.application.service.implementation.UsuarioServiceImpl;
import com.rodama.ecommerce.domain.model.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
@Tag(name = "Usuarios", description = "Gestión de usuarios y clientes de la plataforma")
@CrossOrigin(origins = "http://127.0.0.1:5173", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS})
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioServiceImpl service;

    public UsuarioController(UsuarioServiceImpl service) {
        this.service = service;
    }

    @Operation(summary = "Crear usuario", description = "Registra un nuevo usuario en la plataforma.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody UsuarioDTO dto) {
        Usuario usuario = service.crearUsuario(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioDTO.fromEntity(usuario));
    }

    @Operation(summary = "Listar todos los usuarios", description = "Retorna la lista de todos los usuarios registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerTodos() {
        List<UsuarioDTO> usuarios = service.obtenerTodos()
                .stream()
                .map(UsuarioDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Obtener usuario por ID", description = "Busca y retorna un usuario específico por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> encontrarPorId(
            @Parameter(description = "ID del usuario", example = "1") @PathVariable Long id) {
        Optional<Usuario> usuario = service.encontrarPorId(id);
        return usuario
                .map(u -> ResponseEntity.ok(UsuarioDTO.fromEntity(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar usuario por correo", description = "Retorna el usuario que corresponde al correo electrónico indicado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioDTO> encontrarPorCorreo(
            @Parameter(description = "Correo electrónico del usuario", example = "juan@email.com") @PathVariable String correo) {
        Optional<Usuario> usuario = service.encontrarPorCorreo(correo);
        return usuario
                .map(u -> ResponseEntity.ok(UsuarioDTO.fromEntity(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @Parameter(description = "ID del usuario", example = "1") @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO dto) {
        Optional<Usuario> actualizado = service.actualizarUsuario(id, dto.toEntity());
        return actualizado
                .map(u -> ResponseEntity.ok(UsuarioDTO.fromEntity(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario de la plataforma por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(
            @Parameter(description = "ID del usuario", example = "1") @PathVariable Long id) {
        service.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}