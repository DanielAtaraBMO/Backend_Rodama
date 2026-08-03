package com.rodama.ecommerce.application.controller;

import com.rodama.ecommerce.application.dto.PagoDTO;
import com.rodama.ecommerce.application.service.implementation.PagoServiceImpl;
import com.rodama.ecommerce.domain.model.Pago;
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
@RequestMapping("/api/pago")
public class PagoController {

    private final PagoServiceImpl service;

    public PagoController(PagoServiceImpl service) {
        this.service = service;
    }

    @Operation(summary = "Registrar un pago", description = "Crear un pago nuevo asociado a un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<PagoDTO> crearPago(
            @Valid @RequestBody PagoDTO dto) {

        Pago pago = service.crearPago(dto.toEntity());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PagoDTO.fromEntity(pago));
    }

    @Operation(summary = "Obtener todos los pagos", description = "Lista todos los pagos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta exitosa")
    })
    @GetMapping
    public ResponseEntity<List<PagoDTO>> obtenerTodos() {

        List<PagoDTO> pagos = service.obtenerTodos()
                .stream()
                .map(PagoDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(pagos);
    }

    @Operation(summary = "Obtener pago por id", description = "Busca un pago por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> encontrarPorId(@PathVariable Long id) {

        Optional<Pago> pago = service.encontrarPorId(id);

        return pago
                .map(p -> ResponseEntity.ok(PagoDTO.fromEntity(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}