package com.rodama.ecommerce.application.controller;

import com.rodama.ecommerce.application.dto.PedidoDTO;
import com.rodama.ecommerce.application.service.implementation.PedidoServiceImpl;
import com.rodama.ecommerce.domain.model.Pedido;
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
@RequestMapping("/api/pedido")
public class PedidoController {

    private final PedidoServiceImpl service;

    public PedidoController(PedidoServiceImpl service) {
        this.service = service;
    }

    @Operation(summary = "Agregar un pedido", description = "Crear un pedido nuevo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<PedidoDTO> crearPedido(
            @Valid @RequestBody PedidoDTO dto) {

        Pedido pedido = service.crearPedido(dto.toEntity());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PedidoDTO.fromEntity(pedido));
    }

    @Operation(summary = "Obtener todos los pedidos", description = "Lista todos los pedidos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta exitosa")
    })
    @GetMapping
    public ResponseEntity<List<PedidoDTO>> obtenerTodos() {

        List<PedidoDTO> pedidos = service.obtenerTodos()
                .stream()
                .map(PedidoDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Obtener pedido por id", description = "Busca un pedido por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> encontrarPorId(@PathVariable Long id) {

        Optional<Pedido> pedido = service.encontrarPorId(id);

        return pedido
                .map(p -> ResponseEntity.ok(PedidoDTO.fromEntity(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar pedido", description = "Elimina un pedido por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido eliminado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {

        service.eliminarPorId(id);

        return ResponseEntity.noContent().build();
    }
}