package com.rodama.ecommerce.application.dto;

import com.rodama.ecommerce.domain.model.Producto;
import com.rodama.ecommerce.domain.model.enums.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Datos del producto")
public class ProductoDTO {

    @Schema(description = "ID del producto (generado automáticamente)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre debe contener entre 3 y 100 caracteres")
    @Schema(description = "Nombre del producto", example = "Camisa Sport")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    @Schema(description = "Precio del producto en pesos", example = "49990.0")
    private Double precio;

    @NotNull(message = "La categoría es obligatoria")
    @Schema(description = "Categoría del producto", example = "CASUAL")
    private Categoria categoria;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 5, max = 255, message = "La descripción debe contener entre 5 y 255 caracteres")
    @Schema(description = "Descripción del producto", example = "Camisa de algodón 100% para uso casual")
    private String descripcion;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Schema(description = "Cantidad disponible en inventario", example = "50")
    private Integer stock;

    @Schema(description = "Talla del producto (ej: S, M, L, XL, 38, 42)", example = "M")
    private String talla;

    @Schema(description = "URL de la imagen del producto", example = "https://ejemplo.com/imagenes/camisa-sport.jpg")
    private String imageUrl;

    public Producto toEntity() {
        Producto producto = new Producto();
        producto.setId(this.id);
        producto.setNombre(this.nombre);
        producto.setPrecio(this.precio);
        producto.setCategoria(this.categoria);
        producto.setDescripcion(this.descripcion);
        producto.setStock(this.stock);
        producto.setTalla(this.talla);
        producto.setImageUrl(this.imageUrl);
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
        dto.setTalla(producto.getTalla());
        dto.setImageUrl(producto.getImageUrl());
        return dto;
    }
}
