package com.deliverytech.delivery_api.loader;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.deliverytech.delivery_api.entity.Cliente;
import com.deliverytech.delivery_api.entity.ItemPedido;
import com.deliverytech.delivery_api.entity.Pedido;
import com.deliverytech.delivery_api.entity.Produto;
import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.entity.StatusPedido;
import com.deliverytech.delivery_api.repository.ClienteRepository;
import com.deliverytech.delivery_api.repository.ItemPedidoRepository;
import com.deliverytech.delivery_api.repository.PedidoRepository;
import com.deliverytech.delivery_api.repository.ProdutoRepository;
import com.deliverytech.delivery_api.repository.RestauranteRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final RestauranteRepository restauranteRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public DataLoader(
            ClienteRepository clienteRepository,
            RestauranteRepository restauranteRepository,
            ProdutoRepository produtoRepository,
            PedidoRepository pedidoRepository,
            ItemPedidoRepository itemPedidoRepository) {
        this.clienteRepository = clienteRepository;
        this.restauranteRepository = restauranteRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println(" Iniciando carga de dados...");

        // ======================
        // 1 Criar Clientes
        // ======================
        Cliente c1 = new Cliente("João Silva", "joao@email.com", "9999-1111", true);
        Cliente c2 = new Cliente("Maria Souza", "maria@email.com", "9999-2222", true);
        Cliente c3 = new Cliente("Pedro Santos", "pedro@email.com", "9999-3333", true);
        clienteRepository.saveAll(Arrays.asList(c1, c2, c3));

        // ======================
        // 2 Criar Restaurantes
        // ======================
        Restaurante r1 = new Restaurante("Restaurante A", "Italiana", BigDecimal.valueOf(10.0), true, "Rua A, 123", "123456789", 4.5);
        Restaurante r2 = new Restaurante("Restaurante B", "Japonesa", BigDecimal.valueOf(8.5), true, "Rua B, 456", "987654321", 4.8);
        restauranteRepository.saveAll(Arrays.asList(r1, r2));

        // ======================
        // 3 Criar Produtos
        // ======================
        Produto p1 = new Produto("Pizza Margherita", "Pizza tradicional italiana", BigDecimal.valueOf(45.0), true, "Italiana", r1);
        Produto p2 = new Produto("Lasanha", "Lasanha à bolonhesa", BigDecimal.valueOf(35.0), true, "Italiana", r1);
        Produto p3 = new Produto("Sushi Combo", "10 peças variadas", BigDecimal.valueOf(55.0), true, "Japonesa", r2);
        Produto p4 = new Produto("Yakissoba", "Macarrão oriental com legumes", BigDecimal.valueOf(40.0), true, "Japonesa", r2);
        Produto p5 = new Produto("Temaki", "Temaki de salmão", BigDecimal.valueOf(25.0), true, "Japonesa", r2);
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));


       
        // 4 Criar Pedidos + Itens
         Pedido pedido1 = new Pedido(LocalDateTime.now().minusDays(1), StatusPedido.CONFIRMADO, BigDecimal.valueOf(80.0), c1, r1);
        Pedido pedido2 = new Pedido(LocalDateTime.now(), StatusPedido.PENDENTE, BigDecimal.valueOf(65.0), c2, r2);
        pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));

        ItemPedido item1 = new ItemPedido(pedido1, p1, 1, p1.getPreco());
        ItemPedido item2 = new ItemPedido(pedido1, p2, 1, p2.getPreco());
        ItemPedido item3 = new ItemPedido(pedido2, p3, 1, p3.getPreco());
        ItemPedido item4 = new ItemPedido(pedido2, p5, 2, p5.getPreco());
        itemPedidoRepository.saveAll(Arrays.asList(item1, item2, item3, item4));

        System.out.println(" Dados de teste inseridos com sucesso!");

        // ======================
// 5 Testes de Consultas Derivadas
// ======================
System.out.println("\n Consultas de validação:");

// Buscar cliente pelo e-mail
clienteRepository.findByEmail("joao@email.com").ifPresent(cliente ->
        System.out.println("Cliente encontrado: " + cliente.getNome()));

// Restaurantes ativos
List<Restaurante> ativos = restauranteRepository.findByAtivoTrue();
System.out.println("Restaurantes ativos: " + ativos.size());

// Produtos de um restaurante específico
List<Produto> produtosRestauranteA = produtoRepository.findByRestauranteId(r1.getId());
System.out.println("Produtos do Restaurante A: " + produtosRestauranteA.size());

// Top 10 pedidos mais recentes
List<Pedido> pedidosRecentes = pedidoRepository.findTop10ByOrderByDataPedidoDesc();
System.out.println("Pedidos mais recentes: " + pedidosRecentes.size());

// Pedidos de um cliente específico
List<Pedido> pedidosCliente1 = pedidoRepository.findByClienteIdOrderByDataPedidoDesc(c1.getId());
System.out.println("Pedidos do cliente " + c1.getNome() + ": " + pedidosCliente1.size());

// ======================
// 6 Consultas Customizadas
// ======================
System.out.println("\n Consultas customizadas:");

// Total de vendas por restaurante
for (Restaurante r : restauranteRepository.findAll()) {
    Double total = pedidoRepository.totalVendasPorRestaurante(r.getId());
    System.out.println("Restaurante " + r.getNome() + " total vendas: " + (total != null ? total : 0));
}

// Pedidos com valor acima de 70
pedidoRepository.pedidosAcimaDe(BigDecimal.valueOf(70))
        .forEach(p -> System.out.println("Pedido acima de 70: " + p.getId() + ", Valor: " + p.getTotal()));

// Pedidos por período e status
List<Pedido> pedidosPeriodoStatus = pedidoRepository.findByDataPedidoBetweenAndStatus(
        LocalDateTime.now().minusDays(7),
        LocalDateTime.now(),
        StatusPedido.CONFIRMADO
);
pedidosPeriodoStatus.forEach(p ->
        System.out.println("Pedido confirmado na semana: " + p.getId())
);

System.out.println("\n Carga de dados finalizada!"); 
    }
}