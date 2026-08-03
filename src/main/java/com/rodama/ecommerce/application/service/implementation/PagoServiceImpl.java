package com.rodama.ecommerce.application.service.implementation;

import com.rodama.ecommerce.application.service.PagoService;
import com.rodama.ecommerce.domain.model.Pago;
import com.rodama.ecommerce.infrastructure.entity.PagoEntity;
import com.rodama.ecommerce.infrastructure.entity.PedidoEntity;
import com.rodama.ecommerce.infrastructure.repository.JpaPagoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PagoServiceImpl implements PagoService {

    private final JpaPagoRepository repository;

    public PagoServiceImpl(JpaPagoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pago crearPago(Pago pago) {

        if (pago.getPedidoId() == null) {
            throw new RuntimeException("El pedido es obligatorio");
        }
        if (pago.getMonto() == null || pago.getMonto() <= 0) {
            throw new RuntimeException("El monto debe ser mayor a 0");
        }
        if (pago.getMetodo() == null || pago.getMetodo().isEmpty()) {
            throw new RuntimeException("El método de pago es obligatorio");
        }

        if (pago.getFecha() == null) {
            pago.setFecha(LocalDateTime.now());
        }

        PagoEntity saved = repository.save(toEntity(pago));
        return toModel(saved);
    }

    @Override
    public List<Pago> obtenerTodos() {
        return repository.findAll()
                .stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public Optional<Pago> encontrarPorId(Long id) {
        return repository.findById(id)
                .map(this::toModel);
    }

    private PagoEntity toEntity(Pago pago) {
        PagoEntity entity = new PagoEntity();

        entity.setId(pago.getId());
        entity.setMonto(pago.getMonto());
        entity.setMetodo(pago.getMetodo());
        entity.setFecha(pago.getFecha());

        PedidoEntity pedido = new PedidoEntity();
        pedido.setIdPedido(pago.getPedidoId());
        entity.setPedido(pedido);

        return entity;
    }

    private Pago toModel(PagoEntity entity) {
        Pago pago = new Pago();

        pago.setId(entity.getId());
        pago.setMonto(entity.getMonto());
        pago.setMetodo(entity.getMetodo());
        pago.setFecha(entity.getFecha());
        pago.setPedidoId(entity.getPedido().getIdPedido());

        return pago;
    }
}