package com.rodama.ecommerce.infrastructure.repository;

import com.rodama.ecommerce.infrastructure.entity.PagoEntity;
import com.rodama.ecommerce.domain.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaPagoRepository extends JpaRepository<PagoEntity, Long> {
}
