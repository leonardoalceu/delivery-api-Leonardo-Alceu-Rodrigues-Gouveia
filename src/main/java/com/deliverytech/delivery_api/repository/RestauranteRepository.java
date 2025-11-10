package com.deliverytech.delivery_api.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery_api.entity.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    // Buscar apenas restaurantes ativos
    List<Restaurante> findByAtivoTrue();

    // Buscar por categoria (ignora maiúsculas/minúsculas)
    List<Restaurante> findByCategoriaContainingIgnoreCase(String categoria);

    // Buscar por categoria exata (requisito do roteiro)
    List<Restaurante> findByCategoria(String categoria);

    // Buscar por taxa de entrega menor ou igual
    List<Restaurante> findByTaxaEntregaLessThanEqual(BigDecimal taxa);

    // Retornar os 5 primeiros restaurantes ordenados por nome (A-Z)
    List<Restaurante> findTop5ByOrderByNomeAsc();

    // Buscar restaurante por nome
    Optional<Restaurante> findByNome(String nome);
}
