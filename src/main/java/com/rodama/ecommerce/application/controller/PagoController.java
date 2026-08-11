package com.rodama.ecommerce.application.controller;

import com.rodama.ecommerce.application.dto.PagoDTO;
import com.rodama.ecommerce.application.service.implementation.PagoServiceImpl;
import com.rodama.ecommerce.domain.model.Pago;
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
@RequestMapping("/api/pago")
@Tag(name = "Pagos", description = "Registro y consulta de pagos asociados a pedidos")
@CrossOrigin(origins = "http://127.0.0.1:5173", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS})
@SecurityRequirement(name = "bearerAuth")
public class PagoController {

    private final PagoServiceImpl service;

    public PagoController(PagoServiceImpl service) {
        this.service = service;
    }

    @Operation(summary = "Registrar pago", description = "Registra un nuevo pago asociado a un pedido existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @PostMapping
    public ResponseEntity<PagoDTO> crearPago(@Valid @RequestBody PagoDTO dto) {
        Pago pago = service.crearPago(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(PagoDTO.fromEntity(pago));
    }

    @Operation(summary = "Listar todos los pagos", description = "Retorna la lista completa de pagos registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @GetMapping
    public ResponseEntity<List<PagoDTO>> obtenerTodos() {
        List<PagoDTO> pagos = service.obtenerTodos()
                .stream()
                .map(PagoDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(pagos);
    }

    @Operation(summary = "Obtener pago por ID", description = "Busca y retorna un pago específico por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> encontrarPorId(
            @Parameter(description = "ID del pago", example = "1") @PathVariable Long id) {
        Optional<Pago> pago = service.encontrarPorId(id);
        return pago
                .map(p -> ResponseEntity.ok(PagoDTO.fromEntity(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}