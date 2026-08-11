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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Double precio;

    @Column(name = "categoria")
    private String categoriaRaw;

    private String descripcion;

    private Integer stock;

    private String talla;

    @Column(name = "image_url", columnDefinition = "LONGTEXT")
    private String imageUrl;

    public Categoria getCategoria() {
        if (categoriaRaw == null || categoriaRaw.trim().isEmpty()) return Categoria.ACCESORIOS;
        for (Categoria c : Categoria.values()) {
            if (c.name().equalsIgnoreCase(categoriaRaw.trim())) return c;
        }
        return Categoria.ACCESORIOS;
    }

    public void setCategoria(Categoria categoria) {
        this.categoriaRaw = categoria != null ? categoria.name() : Categoria.ACCESORIOS.name();
    }
}
