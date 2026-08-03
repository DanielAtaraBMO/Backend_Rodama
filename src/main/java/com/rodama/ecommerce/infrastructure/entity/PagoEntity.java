package com.rodama.ecommerce.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Pago")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPedido", nullable = false)
    private PedidoEntity pedido;

    private String metodo;

    private Double monto;

    private LocalDateTime fecha;
}
