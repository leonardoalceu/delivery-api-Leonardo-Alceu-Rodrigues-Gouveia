package com.deliverytech.delivery_api.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.projection.RelatorioVendas;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    List<Restaurante> findByAtivoTrue();

    List<Restaurante> findByCategoriaContainingIgnoreCase(String categoria);

    List<Restaurante> findByTaxaEntregaLessThanEqual(BigDecimal taxa);

    List<Restaurante> findTop5ByOrderByNomeAsc();

    @Query("""
        SELECT 
            r.id AS restauranteId,
            r.nome AS restauranteNome,
            COUNT(p.id) AS quantidadePedidos,
            COALESCE(SUM(p.valorTotal), 0) AS totalVendas
        FROM Pedido p
        JOIN p.restaurante r
        GROUP BY r.id, r.nome
        ORDER BY totalVendas DESC
    """)
    List<RelatorioVendas> relatorioVendasPorRestaurante();
}
