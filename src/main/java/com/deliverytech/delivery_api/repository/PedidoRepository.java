package com.deliverytech.delivery_api.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery_api.entity.Pedido;
import com.deliverytech.delivery_api.entity.StatusPedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Buscar pedidos acima de um valor
    @Query("SELECT p FROM Pedido p WHERE p.total > :valor")
    List<Pedido> pedidosAcimaDe(@Param("valor") BigDecimal valor);

    // Buscar pedidos por status
    List<Pedido> findByStatus(String status);

    // Buscar pedidos por cliente, ordenando pela data do pedido (mais recente primeiro)
    List<Pedido> findByClienteIdOrderByDataPedidoDesc(Long clienteId);

    // Buscar pedidos por restaurante
    List<Pedido> findByRestauranteId(Long restauranteId);

    // Buscar pedidos por data
    List<Pedido> findByDataPedidoAfter(LocalDateTime data);

    // -----------------------------
    // NOVOS MÉTODOS NECESSÁRIOS PARA O DATALOADER
    // -----------------------------

    // Top 10 pedidos mais recentes
    List<Pedido> findTop10ByOrderByDataPedidoDesc();

    // Total de vendas por restaurante
    @Query("SELECT SUM(p.total) FROM Pedido p WHERE p.restaurante.id = :restauranteId")
    Double totalVendasPorRestaurante(@Param("restauranteId") Long restauranteId);

    // Pedidos por período e status
    List<Pedido> findByDataPedidoBetweenAndStatus(LocalDateTime inicio, LocalDateTime fim, StatusPedido status);
}



