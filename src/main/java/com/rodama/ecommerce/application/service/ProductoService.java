package com.rodama.ecommerce.application.service;

import com.rodama.ecommerce.domain.model.Producto;
import com.rodama.ecommerce.domain.model.Usuario;
import com.rodama.ecommerce.domain.model.enums.Categoria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface ProductoService {
    Producto crearProducto(Producto producto);

    List<Producto> obtenerTodos();

    Optional<Producto> encontrarPorId(Long id);

    List<Producto> encontrarPorCategoria(Categoria categoria);

    Optional<Producto> actualizarProducto(Long id, Producto producto);

    boolean eliminarPorId(Long id);
}
