package com.deliverytech.delivery_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery_api.entity.ItemPedido;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    // Buscar todos os itens de um pedido específico
    List<ItemPedido> findByPedidoId(Long pedidoId);
}
