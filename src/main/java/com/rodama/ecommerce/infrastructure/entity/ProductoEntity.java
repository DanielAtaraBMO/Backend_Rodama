package com.rodama.ecommerce.infrastructure.entity;

import com.rodama.ecommerce.domain.model.enums.Categoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Producto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String nombre;
    private Double precio;
    private Categoria categoria;
    private String descripcion;
    private Integer stock;
}
