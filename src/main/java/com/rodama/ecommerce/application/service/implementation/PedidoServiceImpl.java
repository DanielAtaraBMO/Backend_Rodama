package com.rodama.ecommerce.application.service.implementation;

import com.rodama.ecommerce.application.service.PedidoService;
import com.rodama.ecommerce.domain.model.Pedido;
import com.rodama.ecommerce.domain.model.Usuario;
import com.rodama.ecommerce.infrastructure.entity.PedidoEntity;
import com.rodama.ecommerce.infrastructure.entity.UsuarioEntity;
import com.rodama.ecommerce.infrastructure.repository.JpaPedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final JpaPedidoRepository repository;

    public PedidoServiceImpl(JpaPedidoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pedido crearPedido(Pedido pedido) {
        if (pedido.getUsuario() == null || pedido.getUsuario().getId() == null) {
            throw new RuntimeException("El usuario es obligatorio");
        }
        if (pedido.getDireccion() == null || pedido.getDireccion().isEmpty()) {
            throw new RuntimeException("La dirección es obligatoria");
        }
        if (pedido.getPrecio() == null || pedido.getPrecio() <= 0) {
            throw new RuntimeException("El precio debe ser mayor a 0");
        }

        if (pedido.getFecha() == null) {
            pedido.setFecha(LocalDateTime.now());
        }

        PedidoEntity saved = repository.save(toEntity(pedido));
        return toModel(saved);
    }

    @Override
    public List<Pedido> obtenerTodos() {
        return repository.findAll()
                .stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public Optional<Pedido> encontrarPorId(Long id) {
        return repository.findById(id)
                .map(this::toModel);
    }

    @Override
    public boolean eliminarPorId(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Pedido no encontrado");
        }
        repository.deleteById(id);
        return true;
    }

    private PedidoEntity toEntity(Pedido pedido) {
        PedidoEntity entity = new PedidoEntity();

        entity.setIdPedido(pedido.getIdPedido());
        entity.setDireccion(pedido.getDireccion());
        entity.setFecha(pedido.getFecha());
        entity.setPrecio(pedido.getPrecio());

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(pedido.getUsuario().getId());
        entity.setUsuario(usuario);

        return entity;
    }

    private Pedido toModel(PedidoEntity entity) {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(entity.getIdPedido());
        pedido.setDireccion(entity.getDireccion());
        pedido.setFecha(entity.getFecha());
        pedido.setPrecio(entity.getPrecio());

        Usuario usuario = new Usuario();
        usuario.setId(entity.getUsuario().getId());
        pedido.setUsuario(usuario);

        return pedido;
    }
}