package com.deliverytech.delivery_api.repository;

import com.deliverytech.projection.ClienteMaisAtivo;
import com.deliverytech.projection.ProdutoMaisVendido;
import com.deliverytech.projection.RelatorioVendas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RelatorioRepository extends JpaRepository<Object, Long> {

    // RELATÓRIO 1 — VENDAS POR RESTAURANTE
    @Query("""
        SELECT r.nome AS restaurante,
               COUNT(p.id) AS totalPedidos,
               SUM(p.valorTotal) AS totalVendas
        FROM Pedido p
        JOIN p.restaurante r
        GROUP BY r.nome
        """)
    List<RelatorioVendas> vendasPorRestaurante();

    // RELATÓRIO 2 — PRODUTOS MAIS VENDIDOS
    @Query("""
        SELECT pr.nome AS produto,
               SUM(ip.quantidade) AS quantidadeVendida
        FROM ItemPedido ip
        JOIN ip.produto pr
        GROUP BY pr.nome
        ORDER BY quantidadeVendida DESC
        """)
    List<ProdutoMaisVendido> produtosMaisVendidos();

    // RELATÓRIO 3 — CLIENTES MAIS ATIVOS
    @Query("""
        SELECT c.nome AS cliente,
               COUNT(p.id) AS totalPedidos
        FROM Pedido p
        JOIN p.cliente c
        GROUP BY c.nome
        ORDER BY totalPedidos DESC
        """)
    List<ClienteMaisAtivo> clientesMaisAtivos();

    // RELATÓRIO 4 — TOTAL PEDIDOS POR PERÍODO
    @Query("""
        SELECT COUNT(p.id)
        FROM Pedido p
        WHERE p.data BETWEEN :inicio AND :fim
        """)
    Long totalPedidosPorPeriodo(LocalDate inicio, LocalDate fim);

    // RELATÓRIO 5 — TOTAL DE VENDAS POR PERÍODO
    @Query("""
        SELECT SUM(p.valorTotal)
        FROM Pedido p
        WHERE p.data BETWEEN :inicio AND :fim
        """)
    Double totalVendasPorPeriodo(LocalDate inicio, LocalDate fim);
}
