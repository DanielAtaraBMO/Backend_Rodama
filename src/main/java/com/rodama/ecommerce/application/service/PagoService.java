package com.rodama.ecommerce.application.service;

import com.rodama.ecommerce.domain.model.Pago;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PagoService {
    Pago crearPago(Pago pago);
    List<Pago> obtenerTodos();
    Optional<Pago> encontrarPorId(Long id);
}
