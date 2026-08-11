package com.rodama.ecommerce.application.service.implementation;

import com.rodama.ecommerce.application.service.ProductoService;
import com.rodama.ecommerce.domain.model.Producto;
import com.rodama.ecommerce.domain.model.enums.Categoria;
import com.rodama.ecommerce.infrastructure.entity.ProductoEntity;
import com.rodama.ecommerce.infrastructure.repository.JpaProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final JpaProductoRepository repository;

    public ProductoServiceImpl(JpaProductoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Producto crearProducto(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            producto.setNombre("Producto Nuevo");
        }
        if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
            producto.setPrecio(1000.0);
        }
        if (producto.getCategoria() == null) {
            producto.setCategoria(Categoria.ACCESORIOS);
        }
        if (producto.getStock() == null) {
            producto.setStock(1);
        }

        ProductoEntity saved = repository.save(toEntity(producto));
        return toModel(saved);
    }

    @Override
    public List<Producto> obtenerTodos() {
        return repository.findAll()
                .stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public Optional<Producto> encontrarPorId(Long id) {
        return repository.findById(id)
                .map(this::toModel);
    }

    @Override
    public List<Producto> encontrarPorCategoria(Categoria categoria) {
        return repository.findByCategoriaIgnoreCase(categoria.name())
                .stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public Optional<Producto> actualizarProducto(Long id, Producto producto) {
        return repository.findById(id).map(entity -> {
            if (producto.getNombre() != null && !producto.getNombre().trim().isEmpty()) {
                entity.setNombre(producto.getNombre());
            }
            if (producto.getPrecio() != null && producto.getPrecio() > 0) {
                entity.setPrecio(producto.getPrecio());
            }
            if (producto.getCategoria() != null) {
                entity.setCategoria(producto.getCategoria());
            }
            if (producto.getDescripcion() != null) {
                entity.setDescripcion(producto.getDescripcion());
            }
            if (producto.getStock() != null) {
                entity.setStock(producto.getStock());
            }
            if (producto.getTalla() != null) {
                entity.setTalla(producto.getTalla());
            }
            if (producto.getImageUrl() != null) {
                entity.setImageUrl(producto.getImageUrl());
            }

            ProductoEntity actualizado = repository.save(entity);
            return toModel(actualizado);
        });
    }

    @Override
    public boolean eliminarPorId(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    private ProductoEntity toEntity(Producto producto) {
        ProductoEntity entity = new ProductoEntity();
        entity.setId(producto.getId());
        entity.setNombre(producto.getNombre());
        entity.setPrecio(producto.getPrecio());
        entity.setCategoria(producto.getCategoria());
        entity.setDescripcion(producto.getDescripcion());
        entity.setStock(producto.getStock());
        entity.setTalla(producto.getTalla());
        entity.setImageUrl(producto.getImageUrl());
        return entity;
    }

    private Producto toModel(ProductoEntity entity) {
        Producto producto = new Producto();
        producto.setId(entity.getId());
        producto.setNombre(entity.getNombre());
        producto.setPrecio(entity.getPrecio());
        producto.setCategoria(entity.getCategoria());
        producto.setDescripcion(entity.getDescripcion());
        producto.setStock(entity.getStock());
        producto.setTalla(entity.getTalla());
        producto.setImageUrl(entity.getImageUrl());
        return producto;
    }
}
