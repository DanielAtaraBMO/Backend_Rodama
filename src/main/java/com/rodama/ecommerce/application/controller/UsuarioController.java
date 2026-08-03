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
}
