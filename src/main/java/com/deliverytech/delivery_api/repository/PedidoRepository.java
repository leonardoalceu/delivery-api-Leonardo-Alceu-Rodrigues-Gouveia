package com.deliverytech.delivery_api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery_api.entity.Pedido;
import com.deliverytech.delivery_api.entity.StatusPedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Buscar pedidos por cliente, ordenados pela data do pedido descendente
    List<Pedido> findByClienteIdOrderByDataPedidoDesc(Long clienteId);

    // Buscar pedidos por status
    List<Pedido> findByStatus(StatusPedido status);

    // Buscar os 10 pedidos mais recentes
    List<Pedido> findTop10ByOrderByDataPedidoDesc();

    // Buscar pedidos por período
    List<Pedido> findByDataPedidoBetween(LocalDateTime inicio, LocalDateTime fim);
}
//PedidoRepository.java dentro de repository