package com.rodama.ecommerce.application.dto;

import com.rodama.ecommerce.domain.model.Producto;
import com.rodama.ecommerce.domain.model.enums.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDTO {

    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 20, message = "El nombre debe contener mínimo 3 a 20 caracteres")
    @Schema(description = "Nombre del producto", example = "Camisa")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    @Schema(description = "Precio del producto", example = "49.99")
    private Double precio;

    @NotNull(message = "La categoría es obligatoria")
    @Schema(description = "Categoría del producto", example = "ROPA")
    private Categoria categoria;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 5, max = 100, message = "La descripción debe contener entre 5 y 100 caracteres")
    @Schema(description = "Descripción del producto", example = "Camisa de algodón")
    private String descripcion;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Schema(description = "Cantidad disponible en inventario", example = "10")
    private Integer stock;

    public Producto toEntity() {
        Producto producto = new Producto();
        producto.setId(this.id);
        producto.setNombre(this.nombre);
        producto.setPrecio(this.precio);
        producto.setCategoria(this.categoria);
        producto.setDescripcion(this.descripcion);
        producto.setStock(this.stock);
        return producto;
    }

    public static ProductoDTO fromEntity(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setCategoria(producto.getCategoria());
        dto.setDescripcion(producto.getDescripcion());
        dto.setStock(producto.getStock());
        return dto;
    }

}


