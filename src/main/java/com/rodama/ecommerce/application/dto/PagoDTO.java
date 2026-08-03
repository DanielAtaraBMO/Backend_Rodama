package com.rodama.ecommerce.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PagoDTO {

    private Long id;

    @NotNull(message = "El pedido es obligatorio")
    @Schema(description = "ID del pedido asociado al pago", example = "1")
    private Long pedidoId;

    @NotBlank(message = "El método de pago no puede estar vacío")
    @Size(min = 3, max = 30, message = "El método de pago debe contener entre 3 y 30 caracteres")
    @Schema(description = "Método de pago", example = "Tarjeta de crédito")
    private String metodo;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a 0")
    @Schema(description = "Monto del pago", example = "150000")
    private Double monto;

    @NotNull(message = "La fecha es obligatoria")
    @Schema(description = "Fecha del pago", example = "2026-08-02T21:30:00")
    private LocalDateTime fecha;
}
