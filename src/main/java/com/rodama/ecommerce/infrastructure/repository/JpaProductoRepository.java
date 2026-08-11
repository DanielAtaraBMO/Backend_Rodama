package com.rodama.ecommerce.infrastructure.repository;

import com.rodama.ecommerce.infrastructure.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaProductoRepository extends JpaRepository<ProductoEntity, Long> {
    Optional<ProductoEntity> findByNombre(String nombre);
    List<ProductoEntity> findByCategoriaRaw(String categoriaRaw);

    @Query("SELECT p FROM ProductoEntity p WHERE LOWER(p.categoriaRaw) = LOWER(:cat)")
    List<ProductoEntity> findByCategoriaIgnoreCase(@Param("cat") String cat);
}
