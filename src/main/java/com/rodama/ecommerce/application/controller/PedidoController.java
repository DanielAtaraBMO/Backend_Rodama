package com.rodama.ecommerce.application.controller;

import com.rodama.ecommerce.application.dto.PedidoDTO;
import com.rodama.ecommerce.application.service.implementation.PedidoServiceImpl;
import com.rodama.ecommerce.domain.model.Pedido;
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
@RequestMapping("/api/pedido")
@Tag(name = "Pedidos", description = "Gestión de pedidos de compra")
@CrossOrigin(origins = "http://127.0.0.1:5173", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS})
@SecurityRequirement(name = "bearerAuth")
public class PedidoController {

    private final PedidoServiceImpl service;

    public PedidoController(PedidoServiceImpl service) {
        this.service = service;
    }

    @Operation(summary = "Crear pedido", description = "Registra un nuevo pedido de compra asociado a un usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @PostMapping
    public ResponseEntity<PedidoDTO> crearPedido(@Valid @RequestBody PedidoDTO dto) {
        Pedido pedido = service.crearPedido(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(PedidoDTO.fromEntity(pedido));
    }

    @Operation(summary = "Listar todos los pedidos", description = "Retorna la lista completa de pedidos registrados en la plataforma.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @GetMapping
    public ResponseEntity<List<PedidoDTO>> obtenerTodos() {
        List<PedidoDTO> pedidos = service.obtenerTodos()
                .stream()
                .map(PedidoDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Obtener pedido por ID", description = "Busca y retorna un pedido específico por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> encontrarPorId(
            @Parameter(description = "ID del pedido", example = "1") @PathVariable Long id) {
        Optional<Pedido> pedido = service.encontrarPorId(id);
        return pedido
                .map(p -> ResponseEntity.ok(PedidoDTO.fromEntity(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar pedido", description = "Elimina un pedido por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido eliminado exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(
            @Parameter(description = "ID del pedido", example = "1") @PathVariable Long id) {
        service.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}