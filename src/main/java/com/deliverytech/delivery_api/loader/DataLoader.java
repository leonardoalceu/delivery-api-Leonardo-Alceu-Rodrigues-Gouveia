package com.deliverytech.delivery_api.loader;

import com.deliverytech.delivery_api.entity.*;
import com.deliverytech.delivery_api.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final RestauranteRepository restauranteRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;

    public DataLoader(ClienteRepository clienteRepository, RestauranteRepository restauranteRepository,
                      ProdutoRepository produtoRepository, PedidoRepository pedidoRepository) {
        this.clienteRepository = clienteRepository;
        this.restauranteRepository = restauranteRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void run(String... args) {

        // ==== CLIENTES ====
        Cliente c1 = new Cliente("João", "joao@email.com", true);
        Cliente c2 = new Cliente("Maria", "maria@email.com", true);
        Cliente c3 = new Cliente("Pedro", "pedro@email.com", false);
        clienteRepository.saveAll(List.of(c1, c2, c3));

        // ==== RESTAURANTES ====
        Restaurante r1 = new Restaurante("Burger House", "Lanches", new BigDecimal("5.00"), true);
        Restaurante r2 = new Restaurante("Pizzaria Roma", "Italiana", new BigDecimal("7.50"), true);
        restauranteRepository.saveAll(List.of(r1, r2));

        // ==== PRODUTOS ====
        Produto p1 = new Produto("X-Burger", "Lanche artesanal", new BigDecimal("20.00"), true, "Lanches", r1);
        Produto p2 = new Produto("X-Bacon", "Lanche com bacon", new BigDecimal("25.00"), true, "Lanches", r1);
        Produto p3 = new Produto("Pizza Margherita", "Clássica italiana", new BigDecimal("45.00"), true, "Italiana", r2);
        Produto p4 = new Produto("Pizza Calabresa", "Com cebola e queijo", new BigDecimal("48.00"), true, "Italiana", r2);
        Produto p5 = new Produto("Refrigerante", "Lata 350ml", new BigDecimal("6.00"), true, "Bebida", r1);
        produtoRepository.saveAll(List.of(p1, p2, p3, p4, p5));

        // ==== PEDIDOS ====
        Pedido pedido1 = new Pedido(c1, LocalDateTime.now().minusDays(1), StatusPedido.CONFIRMADO);
        Pedido pedido2 = new Pedido(c2, LocalDateTime.now(), StatusPedido.PENDENTE);
        pedidoRepository.saveAll(List.of(pedido1, pedido2));

        System.out.println("=== Dados carregados com sucesso! ===");

        // ==== TESTES ====
        System.out.println("\n>> Clientes Ativos: " + clienteRepository.findByAtivoTrue());
        System.out.println(">> Restaurantes Taxa <= 5: " + restauranteRepository.findByTaxaEntregaLessThanEqual(new BigDecimal("5.00")));
        System.out.println(">> Produtos por Restaurante 1: " + produtoRepository.findByRestauranteId(r1.getId()));
        System.out.println(">> Pedidos Recentes: " + pedidoRepository.findTop10ByOrderByDataPedidoDesc());
    }
}
