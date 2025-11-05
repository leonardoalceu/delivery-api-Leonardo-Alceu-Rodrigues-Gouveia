package com.deliverytech.delivery_api.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery_api.entity.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    // Buscar por nome
    Optional<Restaurante> findByNome(String nome);

    // Buscar restaurantes ativos
    List<Restaurante> findByAtivoTrue();

    // Buscar por categoria
    List<Restaurante> findByCategoria(String categoria);

    // Buscar restaurantes por taxa de entrega até X
    List<Restaurante> findByTaxaEntregaLessThanEqual(BigDecimal taxa);

    // Buscar os 5 primeiros restaurantes por ordem alfabética
    List<Restaurante> findTop5ByOrderByNomeAsc();

}
// RestauranteRepository.java dentro de repository
