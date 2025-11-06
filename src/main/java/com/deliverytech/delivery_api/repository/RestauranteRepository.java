package com.deliverytech.delivery_api.repository;

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

    // Buscar por nome (caso queira usar depois)
    Optional<Restaurante> findByNome(String nome);
}


