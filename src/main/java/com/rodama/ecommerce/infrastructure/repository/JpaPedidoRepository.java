package com.rodama.ecommerce.infrastructure.repository;

import com.rodama.ecommerce.infrastructure.entity.PedidoEntity;
import com.rodama.ecommerce.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaPedidoRepository extends JpaRepository<PedidoEntity, Long> {
    List<PedidoEntity> findByUsuarioId(Long idUsuario);
}
