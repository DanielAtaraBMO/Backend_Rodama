package com.rodama.ecommerce.application.controller;

import com.rodama.ecommerce.application.dto.ProductoDTO;
import com.rodama.ecommerce.application.service.implementation.ProductoServiceImpl;
import com.rodama.ecommerce.domain.model.Producto;
import com.rodama.ecommerce.domain.model.enums.Categoria;
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
@RequestMapping("/api/producto")
@CrossOrigin(origins = "http://127.0.0.1:5173", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS})
@Tag(name = "Productos", description = "Gestión del catálogo de productos")
public class ProductoController {

    private final ProductoServiceImpl service;

    public ProductoController(ProductoServiceImpl service) {
        this.service = service;
    }

    @Operation(
        summary = "Crear producto",
        description = "Registra un nuevo producto en el catálogo. Requiere autenticación.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@Valid @RequestBody ProductoDTO dto) {
        Producto producto = service.crearProducto(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductoDTO.fromEntity(producto));
    }

    @Operation(
        summary = "Listar todos los productos",
        description = "Retorna la lista completa del catálogo de productos. Acceso público."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> obtenerTodos() {
        List<ProductoDTO> productos = service.obtenerTodos()
                .stream()
                .map(ProductoDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(productos);
    }

    @Operation(
        summary = "Obtener producto por ID",
        description = "Busca y retorna un producto específico por su ID. Acceso público."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> encontrarPorId(
            @Parameter(description = "ID del producto", example = "1") @PathVariable Long id) {
        Optional<Producto> producto = service.encontrarPorId(id);
        return producto
                .map(p -> ResponseEntity.ok(ProductoDTO.fromEntity(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Listar productos por categoría",
        description = "Filtra y retorna los productos que pertenecen a una categoría específica. Acceso público."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoDTO>> encontrarPorCategoria(
            @Parameter(description = "Categoría: DEPORTIVO, CASUAL, NOCTURNO, ACCESORIOS, COMBOS", example = "CASUAL")
            @PathVariable Categoria categoria) {
        List<ProductoDTO> productos = service.encontrarPorCategoria(categoria)
                .stream()
                .map(ProductoDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(productos);
    }

    @Operation(
        summary = "Actualizar producto",
        description = "Actualiza los datos de un producto existente por su ID. Requiere autenticación.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(
            @Parameter(description = "ID del producto", example = "1") @PathVariable Long id,
            @Valid @RequestBody ProductoDTO dto) {
        Optional<Producto> actualizado = service.actualizarProducto(id, dto.toEntity());
        return actualizado
                .map(p -> ResponseEntity.ok(ProductoDTO.fromEntity(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Eliminar producto",
        description = "Elimina un producto del catálogo por su ID. Requiere autenticación.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(
            @Parameter(description = "ID del producto", example = "1") @PathVariable Long id) {
        service.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}