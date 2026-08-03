package com.rodama.ecommerce.application.dto;

import com.rodama.ecommerce.domain.model.Pedido;
import com.rodama.ecommerce.domain.model.Usuario;
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
public class PedidoDTO {

    private Long id;

    @NotNull(message = "El usuario es obligatorio")
    @Schema(description = "Usuario asociado al pedido")
    private Usuario usuario;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(min = 5, max = 100, message = "La dirección debe contener entre 5 y 100 caracteres")
    @Schema(description = "Dirección de entrega", example = "Calle 123 #45-67")
    private String direccion;

    @NotNull(message = "La fecha es obligatoria")
    @Schema(description = "Fecha del pedido", example = "2026-08-02T21:30:00")
    private LocalDateTime fecha;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    @Schema(description = "Precio total del pedido", example = "150000")
    private Double precio;

    public Pedido toEntity() {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(this.id);
        pedido.setUsuario(this.usuario);
        pedido.setDireccion(this.direccion);
        pedido.setFecha(this.fecha);
        pedido.setPrecio(this.precio);
        return pedido;
    }

    public static PedidoDTO fromEntity(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getIdPedido());
        dto.setUsuario(pedido.getUsuario());
        dto.setDireccion(pedido.getDireccion());
        dto.setFecha(pedido.getFecha());
        dto.setPrecio(pedido.getPrecio());
        return dto;
    }
}
