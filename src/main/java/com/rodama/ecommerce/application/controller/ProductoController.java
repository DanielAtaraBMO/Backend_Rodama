package com.rodama.ecommerce.application.controller;

import com.rodama.ecommerce.application.dto.ProductoDTO;
import com.rodama.ecommerce.application.service.implementation.ProductoServiceImpl;
import com.rodama.ecommerce.domain.model.Producto;
import com.rodama.ecommerce.domain.model.enums.Categoria;
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
@RequestMapping("/api/producto")
public class ProductoController {

    private final ProductoServiceImpl service;

    public ProductoController(ProductoServiceImpl service) {
        this.service = service;
    }

    @Operation(summary = "Agregar un producto", description = "Crear un producto nuevo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(
            @Valid @RequestBody ProductoDTO dto) {

        Producto producto = service.crearProducto(dto.toEntity());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProductoDTO.fromEntity(producto));
    }

    @Operation(summary = "Obtener todos los productos", description = "Lista todos los productos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta exitosa")
    })
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> obtenerTodos() {

        List<ProductoDTO> productos = service.obtenerTodos()
                .stream()
                .map(ProductoDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Obtener producto por id", description = "Busca un producto por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> encontrarPorId(@PathVariable Long id) {

        Optional<Producto> producto = service.encontrarPorId(id);

        return producto
                .map(p -> ResponseEntity.ok(ProductoDTO.fromEntity(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener productos por categoría", description = "Lista los productos que pertenecen a una categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta exitosa")
    })
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoDTO>> encontrarPorCategoria(@PathVariable Categoria categoria) {

        List<ProductoDTO> productos = service.encontrarPorCategoria(categoria)
                .stream()
                .map(ProductoDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza los datos de un producto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody ProductoDTO dto) {

        Optional<Producto> actualizado = service.actualizarProducto(id, dto.toEntity());

        return actualizado
                .map(p -> ResponseEntity.ok(ProductoDTO.fromEntity(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {

        service.eliminarPorId(id);

        return ResponseEntity.noContent().build();
    }
}