package com.rodama.ecommerce.application.service;

import com.rodama.ecommerce.domain.model.Pedido;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PedidoService {
    Pedido crearPedido(Pedido pedido);
    List<Pedido> obtenerTodos();
    Optional<Pedido> encontrarPorId(Long id);
    boolean eliminarPorId(Long id);
}
