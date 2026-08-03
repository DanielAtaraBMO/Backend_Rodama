package com.rodama.ecommerce.infrastructure.repository;

import com.rodama.ecommerce.domain.model.enums.Categoria;
import com.rodama.ecommerce.infrastructure.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaProductoRepository extends JpaRepository<ProductoEntity, Long> {
    Optional<ProductoEntity> findByNombre(String nombre);
    List<ProductoEntity> findByCategoria(Categoria categoria);
}
